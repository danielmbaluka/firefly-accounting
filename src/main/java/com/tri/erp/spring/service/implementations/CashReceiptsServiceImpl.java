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
import com.tri.erp.spring.response.reports.CashReceiptsDetail;
import com.tri.erp.spring.response.reports.CommonLedgerDetail;
import com.tri.erp.spring.service.interfaces.CashReceiptsService;
import com.tri.erp.spring.service.interfaces.PrintableVoucher;
import com.tri.erp.spring.validator.CashReceiptsValidator;
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

@Service(value = "cashReceiptsServiceImpl")
public class CashReceiptsServiceImpl implements CashReceiptsService, PrintableVoucher {

    @Autowired
    GeneratorFacade generatorFacade;

    @Autowired
    private AuthenticationFacade authenticationFacade;

    @Autowired
    CashReceiptsRepo crRepo;

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
        CashReceipts cashReceipts = (CashReceipts) v;
        return this.processCreate(cashReceipts, bindingResult, messageSource);
    }

    @Override
    @Transactional
    public PostResponse processCreate(Document v, BindingResult bindingResult, MessageSource messageSource) {
        CashReceipts cashReceipts = (CashReceipts) v;
        PostResponse response = new PostResponse();
        MessageFormatter messageFormatter = new MessageFormatter(bindingResult, messageSource, response);

        CashReceiptsValidator validator = new CashReceiptsValidator();
        validator.setLedgerDtoer(this.ledgerDtoers);
        validator.validate(cashReceipts, bindingResult);

        if (bindingResult.hasErrors()) {
            messageFormatter.buildErrorMessages();
            response = messageFormatter.getResponse();
        } else {
            User createdBy = authenticationFacade.getLoggedIn();
            CashReceipts existingCashReceipts = null;

            Integer voucherYear = Integer.parseInt(GlobalConstant.YYYY_DATE_FORMAT.format(cashReceipts.getVoucherDate()));
            User approvingOfficer = userRepo.findOneByAccountNo(cashReceipts.getApprovingOfficer().getAccountNo());
            User checker = userRepo.findOneByAccountNo(cashReceipts.getChecker().getAccountNo());

            Boolean insertMode = cashReceipts.getId() == null;
            if (insertMode) { // insert mode
                Object latestCrCode = crRepo.findLatestCrCodeByYear(voucherYear);
                cashReceipts.setCode(generatorFacade.voucherCode("CR", (latestCrCode == null ? "" : String.valueOf(latestCrCode)), cashReceipts.getVoucherDate()));

                DocumentStatus documentStatus = new DocumentStatus();
                documentStatus.setId(com.tri.erp.spring.model.enums.DocumentStatus.DOCUMENT_CREATED.getId());
                Workflow wf = new Workflow();
                wf.setId(com.tri.erp.spring.model.enums.Workflow.CASH_RECEIPTS.getId());

                cashReceipts.setDocumentStatus(documentStatus);
                cashReceipts.setCreatedBy(createdBy);
                cashReceipts.setTransaction(generatorFacade.transaction());
                cashReceipts.setWorkflow(wf);

                existingCashReceipts = cashReceipts;

            } else {
                existingCashReceipts = crRepo.findOne(cashReceipts.getId());

                List<Integer> statusAllowed = new ArrayList();
                statusAllowed.add(com.tri.erp.spring.model.enums.DocumentStatus.DOCUMENT_CREATED.getId());
                statusAllowed.add(com.tri.erp.spring.model.enums.DocumentStatus.RETURNED_TO_CREATOR.getId());

                if (statusAllowed.indexOf(existingCashReceipts.getDocumentStatus().getId()) < 0) {

                    ArrayList<String> messages = new ArrayList();
                    messages.add("Action is not allowed");

                    response.isNotAuthorized(true);
                    response.setMessages(messages);
                    response.setSuccess(false);

                    return response;
                }
            }

            // editable fields
            existingCashReceipts.setParticulars(cashReceipts.getParticulars());
            existingCashReceipts.setVoucherDate(cashReceipts.getVoucherDate());
            existingCashReceipts.setApprovingOfficer(approvingOfficer);
            existingCashReceipts.setChecker(checker);
            existingCashReceipts.setYear(voucherYear);
            existingCashReceipts.setAmount(cashReceipts.getAmount());

            CashReceipts newCashReceipts = crRepo.save(existingCashReceipts);

            if (newCashReceipts != null) {
                ledgerFacade.postGeneralLedger(newCashReceipts.getTransaction(), cashReceipts.getGeneralLedgerLines(), cashReceipts.getSubLedgerLines());

                if (insertMode) { // log action only when adding document
                    documentProcessingFacade.processAction(newCashReceipts.getTransaction(), null, newCashReceipts.getWorkflow(), createdBy);
                }

                response.setModelId(newCashReceipts.getId());
                response.setSuccessMessage("Sales Voucher successfully saved!");
                response.setSuccess(true);
            }
        }
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CashReceiptsListDto> findAll() {
        List<CashReceipts> vouchers = crRepo.findAll();

        List<CashReceiptsListDto> returnVouchers = new ArrayList<>();
        if (!Checker.collectionIsEmpty(vouchers)) {
            for(CashReceipts cr : vouchers) {
                CashReceiptsListDto cashReceiptsListDto = new CashReceiptsListDto();
                cashReceiptsListDto.setId(cr.getId());
                cashReceiptsListDto.setTransId(cr.getTransaction().getId());
                cashReceiptsListDto.setAmount(cr.getAmount());
                cashReceiptsListDto.setDate(cr.getVoucherDate());
                cashReceiptsListDto.setLocalCode(cr.getCode());
                cashReceiptsListDto.setParticulars(cr.getParticulars());
                cashReceiptsListDto.setStatus(cr.getDocumentStatus().getStatus());

                returnVouchers.add(cashReceiptsListDto);
            }

            return returnVouchers;
        }
        return null;
    }

    @Override
    public CashReceiptsDto findById(Integer id) {

        CashReceipts cashReceipts =  crRepo.findOne(id);
        CashReceiptsDto cashReceiptsDto = new CashReceiptsDto();

        if (cashReceipts != null) {
            cashReceiptsDto.setId(cashReceipts.getId());
            cashReceiptsDto.setParticulars(cashReceipts.getParticulars());
            cashReceiptsDto.setLocalCode(cashReceipts.getCode());
            cashReceiptsDto.setTransId(cashReceipts.getTransaction().getId());

            SlEntity approvingOfficer = slEntityRepo.findOne(cashReceipts.getApprovingOfficer().getAccountNo());
            SlEntity checker = slEntityRepo.findOne(cashReceipts.getChecker().getAccountNo());

            cashReceiptsDto.setApprovingOfficer(approvingOfficer);
            cashReceiptsDto.setChecker(checker);
            cashReceiptsDto.setVoucherDate(cashReceipts.getVoucherDate());
            cashReceiptsDto.setAmount(cashReceipts.getAmount());
            cashReceiptsDto.setDocumentStatus(cashReceipts.getDocumentStatus());
            cashReceiptsDto.setCreated(cashReceipts.getCreatedAt());
            cashReceiptsDto.setLastUpdated(cashReceipts.getUpdatedAt());
        }

        return  cashReceiptsDto;
    }

    @Override
    public  List<CashReceiptsListDto> findByStatusId(Integer id) {
        List<CashReceipts> vouchers = crRepo.findByDocumentStatusId(id);

        List<CashReceiptsListDto> returnVouchers = new ArrayList<>();
        if (!Checker.collectionIsEmpty(vouchers)) {
            for(CashReceipts sv : vouchers) {
                CashReceiptsListDto cashReceiptsListDto = new CashReceiptsListDto();
                cashReceiptsListDto.setId(sv.getId());
                cashReceiptsListDto.setTransId(sv.getTransaction().getId());
                cashReceiptsListDto.setAmount(sv.getAmount());
                cashReceiptsListDto.setDate(sv.getVoucherDate());
                cashReceiptsListDto.setLocalCode(sv.getCode());
                cashReceiptsListDto.setParticulars(sv.getParticulars());
                cashReceiptsListDto.setStatus(sv.getDocumentStatus().getStatus());

                returnVouchers.add(cashReceiptsListDto);
            }

            return returnVouchers;
        }
        return null;
    }

    @Override
    public HashMap reportParameters(Integer vid, HttpServletRequest request) {
        HashMap<String, Object> params = ReportUtil.setupSharedReportHeaders(request);

        CashReceipts cashReceipts = crRepo.findOne(vid);

        if (cashReceipts != null) {
            params.put("VOUCHER_NO", cashReceipts.getCode());
            params.put("V_DATE", cashReceipts.getVoucherDate());
            params.put("AMOUNT_IN_WORDS", CurrencyIntoWords.convert(cashReceipts.getAmount()));
            params.put("TOTAL", cashReceipts.getAmount());
            params.put("APPROVAR", cashReceipts.getApprovingOfficer().getFullName());
            params.put("CHECKER", cashReceipts.getChecker().getFullName());
            params.put("PREPARAR", cashReceipts.getCreatedBy().getFullName());
            params.put("PARTICULARS", cashReceipts.getParticulars());
            params.put("APPROVAR_POS", "");
            params.put("CHECKER_POS", "");
            params.put("PREPARAR_POS", "");
            params.put("REMARKS","");
        }

        return params;
    }

    @Override
    public JRDataSource datasource(Integer vid) {
        List<CommonLedgerDetail> details = new ArrayList<>();

        CashReceipts voucher = crRepo.findOne(vid);
        if (voucher != null) {
            details = ledgerDtoers.getVoucherLedgerLines(voucher.getTransaction().getId());
        }
        return new JRBeanCollectionDataSource(details);
    }

    @Override
    public PostResponse process(ProcessDocumentDto postData, BindingResult bindingResult, MessageSource messageSource) {
        PostResponse response = new PostResponse();

        User processedBy = authenticationFacade.getLoggedIn();
        CashReceipts cashReceipts =  crRepo.findOne(postData.getDocumentId());

        if (cashReceipts != null) {
            DocumentWorkflowActionMap actionMap = workflowActionMapRepo.findOne(postData.getWorkflowActionsDto().getActionMapId());
            DocumentStatus afterActionDocumentStatus = actionMap.getAfterActionDocumentStatus();

            cashReceipts.setDocumentStatus(afterActionDocumentStatus);
            cashReceipts.setUpdatedAt(null);
            cashReceipts = crRepo.save(cashReceipts);

            if (cashReceipts != null) {
                documentProcessingFacade.processAction(cashReceipts.getTransaction(), actionMap, null, processedBy);

                response.setSuccessMessage("Document successfully processed");
                response.setSuccess(true);
            }

        }
        return response;
    }
}
