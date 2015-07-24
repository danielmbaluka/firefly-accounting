package com.tri.erp.spring.dtoers;

import com.tri.erp.spring.commons.helpers.Checker;
import com.tri.erp.spring.model.enums.DocumentStatus;
import com.tri.erp.spring.repo.AccountRepo;
import com.tri.erp.spring.repo.CheckVoucherRepo;
import com.tri.erp.spring.repo.GeneralLedgerRepo;
import com.tri.erp.spring.repo.JournalVoucherRepo;
import com.tri.erp.spring.response.reports.CommonRegisterDetail;
import com.tri.erp.spring.response.reports.RegisterRecapDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by TSI Admin on 5/19/2015.
 */

@Component
public class AccountingReportDtoerImpl implements AccountingReportDtoer {

    @Autowired
    AccountRepo accountRepo;

    @Autowired
    JournalVoucherRepo journalVoucherRepo;

    @Autowired
    CheckVoucherRepo checkVoucherRepo;

    @Autowired
    GeneralLedgerRepo generalLedgerRepo;

    @Override
    public List<CommonRegisterDetail> getForJVRegister(String from, String to) {
        List<Object[]> journalList = journalVoucherRepo.findForRegisterByDateRange(from, to, DocumentStatus.APPROVED.getId());
        return this.makeCommonRegisterDetail(journalList);
    }

    public List<RegisterRecapDetail> getForJVRegisterRecap(String from, String to) {
        List<Object[]> rows = journalVoucherRepo.findForRegisterRecapByDateRange(from, to, DocumentStatus.APPROVED.getId());
        return this.makeRegisterRecapDetail(rows);
    }

    @Override
    public List<CommonRegisterDetail> getForCVRegister(String from, String to) {
        List<Object[]> journalList = checkVoucherRepo.findForRegisterByDateRange(from, to, DocumentStatus.APPROVED.getId());
        return this.makeCommonRegisterDetail(journalList);
    }

    public List<RegisterRecapDetail> getForCVRegisterRecap(String from, String to) {
        List<Object[]> rows = checkVoucherRepo.findForRegisterRecapByDateRange(from, to, DocumentStatus.APPROVED.getId());
        return this.makeRegisterRecapDetail(rows);
    }

    // private methods here
    private List<CommonRegisterDetail> makeCommonRegisterDetail(List<Object[]> rows) {
        List<CommonRegisterDetail> data = new ArrayList<>();

        for (Object[] row:rows) {
            Integer jvId = Integer.parseInt(row[0].toString());
            String jvNumber = String.valueOf(row[1]);
            String explanation = String.valueOf(row[2]);
            Date voucherDate = (Date)row[3];
            Integer transId = Integer.parseInt(row[4].toString());

            List<Object[]> transactions = generalLedgerRepo.findForRegisterByTransId(transId);
            int counter = 1;
            if (!Checker.collectionIsEmpty(transactions)) {
                for(Object[] detail:transactions) {
                    String segmentAccountCode = String.valueOf(detail[0]);
                    BigDecimal debit = (BigDecimal)detail[1];
                    BigDecimal credit = (BigDecimal)detail[2];

                    CommonRegisterDetail jvDetail = new CommonRegisterDetail();
                    if (counter == 1) {
                        jvDetail.setReference(jvNumber);
                        jvDetail.setExplanation(explanation);
                        jvDetail.setVoucherDate(voucherDate);
                    } else {
                        jvDetail.setReference(null);
                        jvDetail.setExplanation(null);
                        jvDetail.setVoucherDate(null);
                    }

                    jvDetail.setCode(segmentAccountCode);
                    jvDetail.setDebit(debit);
                    jvDetail.setCredit(credit);

                    data.add(jvDetail);

                    counter ++;
                }
            }
        }

        return data;
    }

    private List<RegisterRecapDetail> makeRegisterRecapDetail(List<Object[]> rows) {
        List<RegisterRecapDetail> details = new ArrayList<>();

        if (!Checker.collectionIsEmpty(rows)) {
            for(Object[] row:rows ){
                BigDecimal glDebit = (BigDecimal)row[0];
                BigDecimal glCredit = (BigDecimal)row[1];
                Integer glId = (Integer)row[2];
                BigDecimal slDebit = (BigDecimal)row[3];
                BigDecimal slCredit = (BigDecimal)row[4];
                String accountNo = String.valueOf(row[5]);
                String glAccountCode = (String)row[6];
                String glAccountTitle = (String)row[7];
                String slEntityName = (String)row[8];

                RegisterRecapDetail detail = new RegisterRecapDetail();
                detail.setSlAccountTitle(slEntityName);
                detail.setSlAccountCode(accountNo == "null" ? "" : accountNo);
                detail.setSlDebit(slDebit);
                detail.setSlCredit(slCredit);

                detail.setGlAccountTitle(glAccountTitle);
                detail.setGlAccountCode(glAccountCode);
                detail.setGlDebit(glDebit);
                detail.setGlCredit(glCredit);

                details.add(detail);
            }
        }

        return details;
    }
}
