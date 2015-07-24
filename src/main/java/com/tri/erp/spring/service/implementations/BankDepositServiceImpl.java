package com.tri.erp.spring.service.implementations;

import com.tri.erp.spring.commons.GlobalConstant;
import com.tri.erp.spring.commons.facade.AuthenticationFacade;
import com.tri.erp.spring.commons.facade.DocumentProcessingFacade;
import com.tri.erp.spring.commons.facade.GeneratorFacade;
import com.tri.erp.spring.commons.facade.LedgerFacadeImpl;
import com.tri.erp.spring.commons.helpers.Checker;
import com.tri.erp.spring.commons.helpers.MessageFormatter;
import com.tri.erp.spring.commons.helpers.ReportUtil;
import com.tri.erp.spring.dtoers.LedgerDtoerImpl;
import com.tri.erp.spring.model.*;
import com.tri.erp.spring.model.DocumentStatus;
import com.tri.erp.spring.model.Workflow;
import com.tri.erp.spring.model.enums.*;
import com.tri.erp.spring.repo.*;
import com.tri.erp.spring.response.*;
import com.tri.erp.spring.response.reports.BankDepositDetail;
import com.tri.erp.spring.response.reports.SalesVoucherDetail;
import com.tri.erp.spring.service.interfaces.BankDepositService;
import com.tri.erp.spring.service.interfaces.PrintableVoucher;
import com.tri.erp.spring.validator.BankDepositValidator;
import com.tri.erp.spring.validator.SalesVoucherValidator;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import wordnumber.CurrencyIntoWords;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by TSI Admin on 10/9/2014.
 */

@Service(value = "bankDepositServiceImpl")
public class BankDepositServiceImpl implements BankDepositService, PrintableVoucher {

    @Autowired
    GeneratorFacade generatorFacade;

    @Autowired
    private AuthenticationFacade authenticationFacade;

    @Autowired
    BankDepositRepo bankDepositRepo;

    @Autowired
    UserRepo userRepo;

    @Autowired
    SlEntityRepo slEntityRepo;

    @Autowired
    LedgerFacadeImpl ledgerFacade;

    @Autowired
    DocumentWorkflowActionMapRepo workflowActionMapRepo;

    @Autowired
    DocumentProcessingFacade documentProcessingFacade;

    @Autowired
    LedgerDtoerImpl ledgerDtoers;

    @Override
    @Transactional
    public PostResponse processUpdate(Document v, BindingResult bindingResult, MessageSource messageSource) {
        BankDeposit bankDeposit = (BankDeposit) v;
        return this.processCreate(bankDeposit, bindingResult, messageSource);
    }

    @Override
    @Transactional
    public PostResponse processCreate(Document v, BindingResult bindingResult, MessageSource messageSource) {
        BankDeposit bankDeposit = (BankDeposit) v;
        PostResponse response = new PostResponse();
        MessageFormatter messageFormatter = new MessageFormatter(bindingResult, messageSource, response);

        BankDepositValidator validator = new BankDepositValidator();
        validator.setLedgerDtoer(this.ledgerDtoers);
        validator.validate(bankDeposit, bindingResult);

        if (bindingResult.hasErrors()) {
            messageFormatter.buildErrorMessages();
            response = messageFormatter.getResponse();
        } else {
            User createdBy = authenticationFacade.getLoggedIn();
            BankDeposit existingBankDeposit = null;

            Integer voucherYear = Integer.parseInt(GlobalConstant.YYYY_DATE_FORMAT.format(bankDeposit.getVoucherDate()));
            User approvingOfficer = userRepo.findOneByAccountNo(bankDeposit.getApprovingOfficer().getAccountNo());
            User checker = userRepo.findOneByAccountNo(bankDeposit.getChecker().getAccountNo());

            Boolean insertMode = bankDeposit.getId() == null;
            if (insertMode) { // insert mode
                Object latestBankDepositCode = bankDepositRepo.findLatestBankDepositCodeByYear(voucherYear);
                bankDeposit.setCode(generatorFacade.voucherCode("BD", (latestBankDepositCode == null ? "" : String.valueOf(latestBankDepositCode)), bankDeposit.getVoucherDate()));

                DocumentStatus documentStatus = new DocumentStatus();
                documentStatus.setId(com.tri.erp.spring.model.enums.DocumentStatus.DOCUMENT_CREATED.getId());
                Workflow wf = new Workflow();
                wf.setId(com.tri.erp.spring.model.enums.Workflow.BANK_DEPOSIT.getId());

                bankDeposit.setDocumentStatus(documentStatus);
                bankDeposit.setCreatedBy(createdBy);
                bankDeposit.setTransaction(generatorFacade.transaction());
                bankDeposit.setWorkflow(wf);

                existingBankDeposit = bankDeposit;

            } else {
                existingBankDeposit = bankDepositRepo.findOne(bankDeposit.getId());
            }

            // editable fields
            existingBankDeposit.setDepositNumber(bankDeposit.getDepositNumber());
//            existingBankDeposit.setDepositDate(bankDeposit.getDepositDate());
            existingBankDeposit.setVoucherDate(bankDeposit.getVoucherDate());
            existingBankDeposit.setApprovingOfficer(approvingOfficer);
            existingBankDeposit.setChecker(checker);
            existingBankDeposit.setYear(voucherYear);
            existingBankDeposit.setAmount(bankDeposit.getAmount());

            BankDeposit newBankDeposit = bankDepositRepo.save(existingBankDeposit);

            if (newBankDeposit != null) {

//                for(GeneralLedgerLineDto2 gl : sv.getGeneralLedgerLines()){
//                    gl.setTransactionId(newSv.getTransaction().getId());
//                }

                ledgerFacade.postGeneralLedger(newBankDeposit.getTransaction(), bankDeposit.getGeneralLedgerLines(), bankDeposit.getSubLedgerLines());

                if (insertMode) { // log action only when adding document
                    documentProcessingFacade.processAction(newBankDeposit.getTransaction(), null, newBankDeposit.getWorkflow(), createdBy);
                }

                response.setModelId(newBankDeposit.getId());
                response.setSuccessMessage("Bank deposit successfully saved!");
                response.setSuccess(true);
            }
        }
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public List<BankDepositListDto> findAll() {
        List<BankDeposit> vouchers = bankDepositRepo.findAll();

        List<BankDepositListDto> returnVouchers = new ArrayList<>();
        if (!Checker.collectionIsEmpty(vouchers)) {
            for(BankDeposit deposit : vouchers) {
                BankDepositListDto bankDepositListDto = new BankDepositListDto();
                bankDepositListDto.setId(deposit.getId());
                bankDepositListDto.setTransId(deposit.getTransaction().getId());
                bankDepositListDto.setAmount(deposit.getAmount());
                bankDepositListDto.setDate(deposit.getVoucherDate());
                bankDepositListDto.setDepositDate(deposit.getVoucherDate());
                bankDepositListDto.setLocalCode(deposit.getCode());
                bankDepositListDto.setDepositNumber(deposit.getDepositNumber());
                bankDepositListDto.setStatus(deposit.getDocumentStatus().getStatus());

                returnVouchers.add(bankDepositListDto);
            }

            return returnVouchers;
        }
        return null;
    }

    @Override
    public BankDepositDto findById(Integer id) {

        BankDeposit bankDeposit =  bankDepositRepo.findOne(id);
        BankDepositDto bankDepositDto = new BankDepositDto();

        if (bankDeposit != null) {
            bankDepositDto.setId(bankDeposit.getId());
            bankDepositDto.setDepositNumber(bankDeposit.getDepositNumber());
            bankDepositDto.setLocalCode(bankDeposit.getCode());
            bankDepositDto.setTransId(bankDeposit.getTransaction().getId());

            SlEntity approvingOfficer = slEntityRepo.findOne(bankDeposit.getApprovingOfficer().getAccountNo());
            SlEntity checker = slEntityRepo.findOne(bankDeposit.getChecker().getAccountNo());

            bankDepositDto.setApprovingOfficer(approvingOfficer);
            bankDepositDto.setChecker(checker);
            bankDepositDto.setDepositDate(bankDeposit.getVoucherDate());
            bankDepositDto.setAmount(bankDeposit.getAmount());
            bankDepositDto.setDocumentStatus(bankDeposit.getDocumentStatus());
            bankDepositDto.setCreated(bankDeposit.getCreatedAt());
            bankDepositDto.setLastUpdated(bankDeposit.getUpdatedAt());
        }

        return  bankDepositDto;
    }

    @Override
    public  List<BankDepositListDto> findByStatusId(Integer id) {
        List<BankDeposit> vouchers = bankDepositRepo.findByDocumentStatusId(id);

        List<BankDepositListDto> returnVouchers = new ArrayList<>();
        if (!Checker.collectionIsEmpty(vouchers)) {
            for(BankDeposit sv : vouchers) {
                BankDepositListDto bankDepositListDto = new BankDepositListDto();
                bankDepositListDto.setId(sv.getId());
                bankDepositListDto.setTransId(sv.getTransaction().getId());
                bankDepositListDto.setAmount(sv.getAmount());
                bankDepositListDto.setDate(sv.getVoucherDate());
                bankDepositListDto.setDepositDate(sv.getVoucherDate());
                bankDepositListDto.setLocalCode(sv.getCode());
                bankDepositListDto.setDepositNumber(sv.getDepositNumber());
                bankDepositListDto.setStatus(sv.getDocumentStatus().getStatus());

                returnVouchers.add(bankDepositListDto);
            }

            return returnVouchers;
        }
        return null;
    }

    @Override
    public HashMap reportParameters(Integer vid, HttpServletRequest request) {
        HashMap<String, Object> params = ReportUtil.setupSharedReportHeaders(request);

        BankDeposit bankDeposit = bankDepositRepo.findOne(vid);

        if (bankDeposit != null) {
            params.put("VOUCHER_NO", bankDeposit.getCode());
            params.put("DEPOSIT_NO", bankDeposit.getDepositNumber());
            params.put("V_DATE", bankDeposit.getVoucherDate());
            params.put("DEPOSIT_DATE", bankDeposit.getVoucherDate());
            params.put("AMOUNT_IN_WORDS", CurrencyIntoWords.convert(bankDeposit.getAmount()));
            params.put("TOTAL", bankDeposit.getAmount());
            params.put("APPROVAR", bankDeposit.getApprovingOfficer().getFullName());
            params.put("CHECKER", bankDeposit.getChecker().getFullName());
            params.put("PREPARAR", bankDeposit.getCreatedBy().getFullName());
            params.put("APPROVAR_POS", "");
            params.put("CHECKER_POS", "");
            params.put("PREPARAR_POS", "");
            params.put("REMARKS","");
        }

        return params;
    }

    @Override
    public JRDataSource datasource(Integer vid) {
        List<BankDepositDetail> details = new ArrayList<>();

        BankDeposit voucher = bankDepositRepo.findOne(vid);
        if (voucher != null) {
            List<GeneralLedgerLineDto2> generalLedgerLineDtos = ledgerDtoers.getGLAccountEntriesDtoByTrans(voucher.getTransaction().getId());

            if (!Checker.collectionIsEmpty(generalLedgerLineDtos)) {
                for(GeneralLedgerLineDto2 dto : generalLedgerLineDtos) {
                    BankDepositDetail d = new BankDepositDetail();

                    d.setAccount(dto.getCode());

                    d.setDebit(dto.getDebit());
                    d.setCredit(dto.getCredit());

                    details.add(d);
                }
            }
        }
        return new JRBeanCollectionDataSource(details);
    }

    @Override
    public PostResponse process(ProcessDocumentDto postData, BindingResult bindingResult, MessageSource messageSource) {
        PostResponse response = new PostResponse();

        User processedBy = authenticationFacade.getLoggedIn();
        BankDeposit bankDeposit =  bankDepositRepo.findOne(postData.getDocumentId());

        if (bankDeposit != null) {
            DocumentWorkflowActionMap actionMap = workflowActionMapRepo.findOne(postData.getWorkflowActionsDto().getActionMapId());
            DocumentStatus afterActionDocumentStatus = actionMap.getAfterActionDocumentStatus();

            bankDeposit.setDocumentStatus(afterActionDocumentStatus);
            bankDeposit.setUpdatedAt(null);
            bankDeposit = bankDepositRepo.save(bankDeposit);

            if (bankDeposit != null) {
                documentProcessingFacade.processAction(bankDeposit.getTransaction(), actionMap, null, processedBy);

                response.setSuccessMessage("Document successfully processed");
                response.setSuccess(true);
            }

        }
        return response;
    }
}
