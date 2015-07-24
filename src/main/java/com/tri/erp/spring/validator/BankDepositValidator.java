package com.tri.erp.spring.validator;

import com.tri.erp.spring.commons.helpers.Checker;
import com.tri.erp.spring.dtoers.LedgerDtoerImpl;
import com.tri.erp.spring.model.BankDeposit;
import com.tri.erp.spring.model.SalesVoucher;
import com.tri.erp.spring.response.SubLedgerDto;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.List;

/**
 * Created by TSI Admin on 9/11/2014.
 */

@Component
public class BankDepositValidator implements Validator {

    LedgerDtoerImpl ledgerDtoer;

    @Override
    public boolean supports(Class<?> aClass) {
        return BankDeposit.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        BankDeposit bankDeposit = (BankDeposit) o;
        // when update mode and SL entries are unchanged
        if (Checker.collectionIsEmpty(bankDeposit.getSubLedgerLines()) && Checker.validTransaction(bankDeposit.getTransaction())) {
            List<SubLedgerDto> slEntriesDtoByTrans = ledgerDtoer.getSLEntriesDtoByTrans(bankDeposit.getTransaction().getId());
            bankDeposit.setSubLedgerLines(slEntriesDtoByTrans);
        }
        CommonValidator.validateJournal(errors, bankDeposit.getTransaction(), bankDeposit.getGeneralLedgerLines(), bankDeposit.getSubLedgerLines());
    }

    // this how we add dependency, @Autowired is not working here!
    public void setLedgerDtoer(LedgerDtoerImpl l) {
        this.ledgerDtoer = l;
    }
}
