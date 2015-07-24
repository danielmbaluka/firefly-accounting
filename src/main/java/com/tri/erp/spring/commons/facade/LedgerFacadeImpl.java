package com.tri.erp.spring.commons.facade;

import com.tri.erp.spring.commons.helpers.Checker;
import com.tri.erp.spring.model.*;
import com.tri.erp.spring.repo.AllocationFactorRepo;
import com.tri.erp.spring.repo.CheckVoucherIncomePaymentRepo;
import com.tri.erp.spring.repo.GeneralLedgerRepo;
import com.tri.erp.spring.repo.SubLedgerRepo;
import com.tri.erp.spring.response.GeneralLedgerLineDto2;
import com.tri.erp.spring.response.SubLedgerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by TSI Admin on 5/1/2015.
 */

@Component
public class LedgerFacadeImpl implements LedgerFacade {

    @Autowired
    GeneralLedgerRepo generalLedgerRepo;

    @Autowired
    AllocationFactorRepo allocationFactorRepo;

    @Autowired
    SubLedgerRepo subLedgerRepo;

    @Autowired
    CheckVoucherIncomePaymentRepo incomePaymentRepo;

    @Override
    public Long postGeneralLedger(Transaction transaction, List<GeneralLedgerLineDto2> generalLedgerLines, List<SubLedgerDto> subLedgerLines) {
        Long count = 0L;

        if (transaction != null && transaction.getId() > 0) {
            generalLedgerRepo.deleteByTransactionId(transaction.getId());
            subLedgerRepo.deleteByTransactionId(transaction.getId());
            incomePaymentRepo.deleteByTransactionId(transaction.getId());
        }

        for(GeneralLedgerLineDto2 ledgerLine: generalLedgerLines) {
            boolean isDebit = true;
            BigDecimal glAmount = ledgerLine.getDebit();
            if (glAmount == null || glAmount.compareTo(BigDecimal.ZERO) == 0) {
                isDebit = false;
                glAmount = ledgerLine.getCredit();
            }

            List<Map> distribution = ledgerLine.getDistribution();
            List<Object[]> percentages = allocationFactorRepo.findByAccountId(ledgerLine.getAccountId());

            if (Checker.collectionIsEmpty(distribution)) { // automatic sl distribution

                for(Object[] obj:percentages) {
                    Integer segmentId = (Integer)obj[1];
                    BigDecimal percentage = (BigDecimal)obj[2];

                    BigDecimal glShare  = (percentage.divide(new BigDecimal(100))).multiply(glAmount);

                    GeneralLedger generalLedger = new GeneralLedger();
                    generalLedger.setTransaction(transaction);
                    if (isDebit) {
                        generalLedger.setDebit(glShare);
                    } else {
                        generalLedger.setCredit(glShare);
                    }

                    SegmentAccount segmentAccount = new SegmentAccount();
                    segmentAccount.setId(segmentId);
                    generalLedger.setSegmentAccount(segmentAccount);

                    GeneralLedger newGl = generalLedgerRepo.save(generalLedger);

                    count += newGl == null ? 0 : 1;

                    if (Checker.collectionIsEmpty(subLedgerLines)) continue;
                    for(SubLedgerDto subLedgerLine: subLedgerLines) {

                        if (subLedgerLine.getAccountId() == ledgerLine.getAccountId()) {
                            SubLedger subLedger = new SubLedger();
                            subLedger.setTransaction(transaction);
                            subLedger.setSegmentAccount(segmentAccount);
                            subLedger.setTransaction(newGl.getTransaction());
                            subLedger.setGeneralLedger(newGl);

                            SlEntity slEntity = new SlEntity();
                            slEntity.setAccountNo(subLedgerLine.getAccountNo());
                            subLedger.setSlEntity(slEntity);

                            BigDecimal slAmount = subLedgerLine.getAmount();
                            BigDecimal slShare = ((percentage.divide(new BigDecimal(100))).multiply(slAmount)).setScale(2, BigDecimal.ROUND_HALF_UP);;

                            if (isDebit)
                                subLedger.setDebit(slShare);
                            else
                                subLedger.setCredit(slShare);

                            subLedgerRepo.save(subLedger);
                        }
                    }
                }
            } else {
                for(Map obj:distribution) {
                    BigDecimal glShare = new BigDecimal(obj.get("amount").toString());
                    Integer segmentAccountId = (Integer)obj.get("segmentAccountId");

                    GeneralLedger generalLedger = new GeneralLedger();
                    generalLedger.setTransaction(transaction);
                    if (isDebit) {
                        generalLedger.setDebit(glShare);
                    } else {
                        generalLedger.setCredit(glShare);
                    }

                    SegmentAccount segmentAccount = new SegmentAccount();
                    segmentAccount.setId(segmentAccountId);
                    generalLedger.setSegmentAccount(segmentAccount);

                    GeneralLedger newGl = generalLedgerRepo.save(generalLedger);

                    count += newGl == null ? 0 : 1;

                    if (Checker.collectionIsEmpty(subLedgerLines)) continue;
                    for(SubLedgerDto subLedgerLine: subLedgerLines) {

                        if (subLedgerLine.getAccountId() == ledgerLine.getAccountId()) {
                            SubLedger subLedger = new SubLedger();
                            subLedger.setTransaction(transaction);
                            subLedger.setSegmentAccount(segmentAccount);
                            subLedger.setTransaction(newGl.getTransaction());
                            subLedger.setGeneralLedger(newGl);

                            SlEntity slEntity = new SlEntity();
                            slEntity.setAccountNo(subLedgerLine.getAccountNo());
                            subLedger.setSlEntity(slEntity);

                            BigDecimal slAmount = subLedgerLine.getAmount();
                            // conversion
                            BigDecimal percent = (glShare.multiply(new BigDecimal(100))).divide(glAmount);
                            BigDecimal slShare = ((percent.divide(new BigDecimal(100))).multiply(slAmount)).setScale(2, BigDecimal.ROUND_HALF_UP);

                            if (isDebit)
                                subLedger.setDebit(slShare);
                            else
                                subLedger.setCredit(slShare);

                            subLedgerRepo.save(subLedger);
                        }
                    }
                }
            }

            if (ledgerLine.getwTaxEntry() != null) {

                CheckVoucherIncomePayment incomePayment = incomePaymentRepo.findOneByTransactionId(transaction.getId());
                if (incomePayment == null) incomePayment = new CheckVoucherIncomePayment();
                LinkedHashMap atc = (LinkedHashMap)ledgerLine.getwTaxEntry().get("atc");

                TaxCode taxCode = new TaxCode();
                taxCode.setId(Integer.parseInt(atc.get("id").toString()));

                incomePayment.setTransaction(transaction);
                incomePayment.setAmount(new BigDecimal(ledgerLine.getwTaxEntry().get("amount").toString()));
                incomePayment.setPercentage(new BigDecimal(ledgerLine.getwTaxEntry().get("percentage").toString()));
                incomePayment.setTaxCode(taxCode);

                incomePaymentRepo.save(incomePayment);
            }
        }
        return count;
    }
}
