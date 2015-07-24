package com.tri.erp.spring.validator;

import com.tri.erp.spring.commons.Debug;
import com.tri.erp.spring.commons.helpers.Checker;
import com.tri.erp.spring.model.Transaction;
import com.tri.erp.spring.response.GeneralLedgerLineDto;
import com.tri.erp.spring.response.GeneralLedgerLineDto2;
import com.tri.erp.spring.response.SubLedgerDto;
import org.springframework.validation.Errors;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by TSI Admin on 4/27/2015.
 */

public class CommonValidator {

    public static Errors validateJournal(Errors errors, Transaction transaction, List<GeneralLedgerLineDto2> ledgerLineDtos, List<SubLedgerDto> subLedgerLineDtos) {

        Map glAccountAmountMap = new HashMap<Integer, BigDecimal>();
        Map slAccountAmountMap = new HashMap<Integer, BigDecimal>();

        if(Checker.collectionIsEmpty(ledgerLineDtos)) {
            errors.rejectValue("generalLedgerLines", "journal.empty");
        } else {
            BigDecimal dr = BigDecimal.ZERO;
            BigDecimal cr = BigDecimal.ZERO;
            Boolean hasMissingGL = false;

            for(GeneralLedgerLineDto2 lineDto : ledgerLineDtos) {
                if (lineDto.getCredit() != null) {
                    cr = cr.add(lineDto.getCredit());
                }
                if (lineDto.getDebit() != null) {
                    dr = dr.add(lineDto.getDebit());
                }

                if ((lineDto.getAccountId() == null || lineDto.getAccountId() == 0) && (dr.compareTo(BigDecimal.ZERO) > 0 || cr.compareTo(BigDecimal.ZERO) > 0)) {
                    hasMissingGL = true;
                }
                BigDecimal prevAmount = (BigDecimal) glAccountAmountMap.get(lineDto.getAccountId());
                BigDecimal newAmount = (cr.compareTo(BigDecimal.ZERO) == 0) ? dr : cr;
                glAccountAmountMap.put(lineDto.getAccountId(), prevAmount != null ? newAmount.add(prevAmount) : newAmount);
            }

            if(dr.compareTo(cr) != 0) {
                errors.rejectValue("generalLedgerLines", "journal.imbalance");
            }
            if (hasMissingGL) {
                errors.rejectValue("generalLedgerLines", "journal.no.account.selected");
            }
        }

        if(!Checker.collectionIsEmpty(subLedgerLineDtos)) {

            Boolean hasMissingSL = false;
            BigDecimal totalSlAmount = BigDecimal.ZERO;

            for(SubLedgerDto lineDto : subLedgerLineDtos) {
                BigDecimal perEntityAmount = BigDecimal.ZERO;

                if (lineDto.getAmount() != null) {
                    perEntityAmount = lineDto.getAmount();
                    totalSlAmount = totalSlAmount.add(perEntityAmount);
                }

                if (lineDto.getAccountId() == null || lineDto.getAccountId() == 0) {
                    hasMissingSL = true;
                } else {
                    BigDecimal prevAmount = (BigDecimal) slAccountAmountMap.get(lineDto.getSegmentAccountId());
                    slAccountAmountMap.put(lineDto.getSegmentAccountId(), prevAmount != null ? perEntityAmount.add(prevAmount) : perEntityAmount);
                }
            }

            if (totalSlAmount.compareTo(BigDecimal.ZERO) == 0) {
                errors.rejectValue("generalLedgerLines", "journal.sl.amount.zero");
            }

            if (hasMissingSL) {
                errors.rejectValue("generalLedgerLines", "journal.sl.missing.entity");
            }

            Iterator<Map.Entry<Integer, BigDecimal>> entries = glAccountAmountMap.entrySet().iterator();
            while (entries.hasNext()) {
                Map.Entry<Integer, BigDecimal> entry = entries.next();

                Integer glSegmentAccountId = entry.getKey();
                BigDecimal glAmount = entry.getValue();

                BigDecimal slAmount = (BigDecimal) slAccountAmountMap.get(glSegmentAccountId);

                if (slAmount != null && glAmount.compareTo(slAmount) != 0) {
                    errors.rejectValue("generalLedgerLines", "journal.sl.amount.unmatched");
                    break;
                }
            }

        }
        return errors;
    }
}
