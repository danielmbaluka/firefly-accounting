package com.tri.erp.spring.dtoers;

import com.tri.erp.spring.commons.facade.SettingFacade;
import com.tri.erp.spring.commons.helpers.Checker;
import com.tri.erp.spring.model.*;
import com.tri.erp.spring.model.enums.SettingCode;
import com.tri.erp.spring.repo.*;
import com.tri.erp.spring.response.GeneralLedgerLineDto;
import com.tri.erp.spring.response.GeneralLedgerLineDto2;
import com.tri.erp.spring.response.SubLedgerDto;
import com.tri.erp.spring.response.reports.CommonLedgerDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by TSI Admin on 5/3/2015.
 */

@Component
public class LedgerDtoerImpl implements LedgerDtoer {

    @Autowired
    GeneralLedgerRepo generalLedgerRepo;

    @Autowired
    SubLedgerRepo subLedgerRepo;

    @Autowired
    CheckVoucherChequeRepo chequeRepo;

    @Autowired
    CheckVoucherIncomePaymentRepo incomePaymentRepo;

    @Autowired
    SettingFacade settingFacade;

    @Autowired
    TemporaryGeneralLedgerRepo temporaryGeneralLedgerRepo;

    @Autowired
    TemporarySubLedgerRepo temporarySubLedgerRepo;

    @Autowired
    TemporaryBatchRepo temporaryBatchRepo;

    @Override
    public List<SubLedgerDto> getSLEntriesDtoByGl(Integer glId) {
        List<SubLedger> sls = subLedgerRepo.findByGeneralLedgerId(glId);
        return composeSubLedgerDto(sls);
    }

    @Override
    public List<SubLedgerDto> getSLEntriesDtoByTrans(Integer transId) {
        List<SubLedger> sls = subLedgerRepo.findByTransactionId(transId);
        return composeSubLedgerDto(sls);
    }

    @Override
    public List<SubLedgerDto> getSLEntityDistributionByTrans(Integer transId) {
        List<SubLedgerDto> subLedgerDtos = new ArrayList<>();

        List<Object[]> list = subLedgerRepo.findByTransactionIdNative(transId);
        if (!Checker.collectionIsEmpty(list)) {
            for (Object[] l:list) {
                SubLedgerDto dto = new SubLedgerDto();

                dto.setId((Integer) l[0]);
                dto.setAccountNo((Integer) l[1]);
                dto.setName((String) l[2]);
                dto.setAccountId((Integer) l[3]);
                dto.setAmount((BigDecimal) l[4]);

                subLedgerDtos.add(dto);
            }
        }
        return subLedgerDtos;
    }

    @Override
    public List<GeneralLedgerLineDto> getGLEntriesDtoByTrans(Integer transId) {
        List<GeneralLedger> generalLedgerLines = generalLedgerRepo.findByTransactionId(transId);

        List<GeneralLedgerLineDto> ledgerLineDtos = new ArrayList<>();

        if (generalLedgerLines != null) {
            for (GeneralLedger line : generalLedgerLines) {
                GeneralLedgerLineDto lineDto = new GeneralLedgerLineDto();
                lineDto.setCredit(line.getCredit());
                lineDto.setDebit(line.getDebit());
                lineDto.setDescription(line.getSegmentAccount().getAccount().getTitle());
                lineDto.setId(line.getId());
                lineDto.setSegmentAccountCode(line.getSegmentAccount().getAccountCode());
                lineDto.setSegmentAccountId(line.getSegmentAccount().getId());

                List<SubLedger> subLedgers = subLedgerRepo.findByGeneralLedgerId(line.getId());
                lineDto.setHasSL((subLedgers != null && subLedgers.size() > 0));

                CheckVoucherCheque cheque = chequeRepo.findOneByBankAccountIdAndTransactionId(line.getSegmentAccount().getId(), transId);
                if (cheque != null) {
                    lineDto.setCheckNumber(cheque.getCheckNumber());
                }

                ledgerLineDtos.add(lineDto);
            }
        }

        return ledgerLineDtos;
    }

    @Override
    public List<GeneralLedgerLineDto2> getGLAccountEntriesDtoByTrans(Integer transId) {
        List<Object[]> generalLedgerLines = generalLedgerRepo.findByTransactionIdGroupByAccount(transId);

        List<GeneralLedgerLineDto2> ledgerLineDtos = new ArrayList<>();

        if (generalLedgerLines != null) {
            for (Object[] line : generalLedgerLines) {
                GeneralLedgerLineDto2 lineDto = new GeneralLedgerLineDto2();

                BigDecimal debit = new BigDecimal(line[0].toString());
                BigDecimal credit = new BigDecimal(line[1].toString());
                Integer accountId = (Integer)line[2];
                String code = (String)line[3];
                String title = (String)line[4];

                lineDto.setDebit(debit);
                lineDto.setCredit(credit);
                lineDto.setDescription(title);
                lineDto.setAccountId(accountId);
                lineDto.setCode(code);

                List<Object[]> subLedgerLines = subLedgerRepo.findByTransactionIdAndAccountId(transId, accountId);
                lineDto.setHasSL((subLedgerLines != null && subLedgerLines.size() > 0));

                // withholding tax: atc
                Integer wtaxAccountId = 0;
                try {
                    Map wtaxAccount = settingFacade.getByCode(SettingCode.WTAX_ACCOUNT.toString());
                    wtaxAccountId = Integer.parseInt(wtaxAccount.get("id").toString());
                } finally {}

                if (wtaxAccountId.equals(accountId)) {
                    CheckVoucherIncomePayment incomePayment = incomePaymentRepo.findOneByTransactionId(transId);
                    if (incomePayment != null) {
                        Map account = new HashMap();
                        account.put("id", accountId);
                        account.put("title", title);
                        account.put("code", code);
                        account.put("amount", incomePayment.getAmount());

                        Map wTaxEntry = new HashMap();
                        wTaxEntry.put("account", account);
                        wTaxEntry.put("amount", incomePayment.getAmount());
                        wTaxEntry.put("percentage", incomePayment.getPercentage());
                        wTaxEntry.put("atc", incomePayment.getTaxCode());

                        lineDto.setwTaxEntry(wTaxEntry);
                    }
                }
                ledgerLineDtos.add(lineDto);
            }
        }

        return ledgerLineDtos;
    }

    @Override
    public List<SubLedgerDto> getSLEntriesDtoByTransAndAccount(Integer transId, Integer accountId) {
        List<SubLedgerDto> subLedgerDtos = new ArrayList<>();

        List<Object[]> list = subLedgerRepo.findByTransactionIdAndAccountId(transId, accountId);
        if (!Checker.collectionIsEmpty(list)) {
            for (Object[] l:list) {
                SubLedgerDto dto = new SubLedgerDto();

                dto.setId((Integer) l[0]);
                dto.setAccountNo((Integer) l[1]);
                dto.setName((String) l[2]);
                dto.setAccountId((Integer) l[3]);
                dto.setAmount((BigDecimal) l[4]);

                subLedgerDtos.add(dto);
            }
        }
        return subLedgerDtos;
    }

    private List<SubLedgerDto> composeSubLedgerDto( List<SubLedger> ledgerLines) {
        List<SubLedgerDto> subLedgerDtos = new ArrayList<>();

        for (SubLedger subLedger : ledgerLines) {
            SubLedgerDto dto = new SubLedgerDto();
            dto.setSegmentAccountId(subLedger.getSegmentAccount().getId());
            dto.setName(subLedger.getSlEntity().getName());
            dto.setAccountNo(subLedger.getSlEntity().getAccountNo());
            dto.setAmount(subLedger.getDebit().add(subLedger.getCredit()));
            dto.setGeneralLedgerId(subLedger.getGeneralLedger().getId());
            dto.setId(subLedger.getId());

            subLedgerDtos.add(dto);
        }

        return subLedgerDtos;
    }

    @Override
    public List<Map> getGLEntriesDtoByTransAndAccount(Integer transId, Integer accountId) {
        List<Map> result = new ArrayList<>();
        List<Object[]> rows = generalLedgerRepo.findByTransactionIdAndAccount(transId, accountId);

        if (!Checker.collectionIsEmpty(rows)) {
            for (Object[] l:rows) {
                Map record = new HashMap();

                record.put("ledgerId",  l[0]);
                record.put("segmentAccountId",  l[2]);
                record.put("debit",  l[3]);
                record.put("credit",  l[4]);
                record.put("segmentDescription",  l[5]);

                result.add(record);
            }
        }
        return result;
    }

    @Override
    public List<CommonLedgerDetail> getVoucherLedgerLines(Integer transId) {
        List<CommonLedgerDetail> details = new ArrayList<>();

        List<GeneralLedgerLineDto2> ledgerLineDto2s = this.getGLAccountEntriesDtoByTrans(transId);

        if (!Checker.collectionIsEmpty(ledgerLineDto2s)) {
            for(GeneralLedgerLineDto2 gl : ledgerLineDto2s) {
                CommonLedgerDetail d = new CommonLedgerDetail();

                d.setAccountCode(gl.getCode());
                d.setAccountTitle(gl.getDescription());
                d.setGlCreditAmount(gl.getCredit());
                d.setGlDebitAmount(gl.getDebit());

                details.add(d);

                List<SubLedgerDto> subLedgerLineDtos = this.getSLEntriesDtoByTransAndAccount(transId, gl.getAccountId());
                if (!Checker.collectionIsEmpty(subLedgerLineDtos)) {

                    for(SubLedgerDto sl : subLedgerLineDtos) {
                        CommonLedgerDetail sld = new CommonLedgerDetail();

                        sld.setAccountCode("&nbsp;&nbsp;&nbsp;" + String.valueOf(sl.getAccountNo()));
                        sld.setAccountTitle("&nbsp;&nbsp;&nbsp;" + sl.getName());

                        BigDecimal drAmount = sl.getAmount();
                        BigDecimal crAmount = sl.getAmount();
                        if (gl.getDebit() == null || gl.getDebit().compareTo(BigDecimal.ZERO) == 0) {
                            drAmount = BigDecimal.ZERO;
                        } else {
                            crAmount = BigDecimal.ZERO;
                        }

                        sld.setSlCreditAmount(crAmount);
                        sld.setSlDebitAmount(drAmount);

                        details.add(sld);
                    }
                }

            }
        }
        return details;
    }

    @Override
    public List<Map> getAllTempBatches() {
        List<Map> result = new ArrayList<>();
        List<Object[]> rows = temporaryBatchRepo.findAllWithAmount();

        if (!Checker.collectionIsEmpty(rows)) {
            for (Object[] l:rows) {
                Map record = new HashMap();

                record.put("tempBatchId",  l[0]);
                record.put("tempBatchDate",  l[1]);
                record.put("remarks",  l[2]);
                record.put("docTypeId",  l[3]);
                record.put("docTypeDesc",  l[4]);
                record.put("tempGLId",  l[5]);
                record.put("amount",  l[6]);

                result.add(record);
            }
        }
        return result;
    }

    @Override
    public List<GeneralLedgerLineDto2> getGLAccountEntriesDtoTempBatchId(Integer tempBatchId) {
        List<Object[]> generalLedgerLines = temporaryGeneralLedgerRepo.findByBatchId(tempBatchId);

        List<GeneralLedgerLineDto2> ledgerLineDtos = new ArrayList<>();

        if (generalLedgerLines != null) {
            for (Object[] line : generalLedgerLines) {
                GeneralLedgerLineDto2 lineDto = new GeneralLedgerLineDto2();

                BigDecimal debit = new BigDecimal(line[0].toString());
                BigDecimal credit = new BigDecimal(line[1].toString());
                Integer accountId = (Integer)line[2];
                String code = (String)line[3];
                String title = (String)line[4];

                lineDto.setDebit(debit);
                lineDto.setCredit(credit);
                lineDto.setDescription(title);
                lineDto.setAccountId(accountId);
                lineDto.setCode(code);

                ledgerLineDtos.add(lineDto);
            }
        }

        return ledgerLineDtos;
    }

    @Override
    public List<SubLedgerDto> getSLAccountEntriesDtoTempBatchId(Integer tempBatchId, Integer accountId) {
        List<SubLedgerDto> subLedgerDtos = new ArrayList<>();

        List<Object[]> list = temporarySubLedgerRepo.findByTempBatcIdAndAccountId(tempBatchId, accountId);
        if (!Checker.collectionIsEmpty(list)) {
            for (Object[] l:list) {
                SubLedgerDto dto = new SubLedgerDto();

                dto.setId((Integer) l[0]);
                dto.setAccountNo((Integer) l[1]);
                dto.setName((String) l[2]);
                dto.setAccountId((Integer) l[3]);
                dto.setAmount((BigDecimal) l[4]);

                subLedgerDtos.add(dto);
            }
        }
        return subLedgerDtos;
    }
}
