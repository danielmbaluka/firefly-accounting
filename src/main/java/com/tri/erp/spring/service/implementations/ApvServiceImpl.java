package com.tri.erp.spring.service.implementations;

import com.tri.erp.spring.commons.*;
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
import com.tri.erp.spring.repo.*;
import com.tri.erp.spring.response.*;
import com.tri.erp.spring.response.reports.APDetail;
import com.tri.erp.spring.service.interfaces.ApvService;
import com.tri.erp.spring.service.interfaces.PrintableVoucher;
import com.tri.erp.spring.validator.ApvValidator;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by TSI Admin on 10/9/2014.
 */

@Service(value = "apvServiceImpl")
public class ApvServiceImpl implements ApvService, PrintableVoucher {

    @Autowired
    GeneratorFacade generatorFacade;

    @Autowired
    private AuthenticationFacade authenticationFacade;

    @Autowired
    AccountsPayableVoucherRepo apvRepo;

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
        AccountsPayableVoucher apv = (AccountsPayableVoucher) v;
        return this.processCreate(apv, bindingResult, messageSource);
    }

    @Override
    @Transactional
    public PostResponse processCreate(Document v, BindingResult bindingResult, MessageSource messageSource) {
        AccountsPayableVoucher apv = (AccountsPayableVoucher) v;
        PostResponse response = new PostResponse();
        MessageFormatter messageFormatter = new MessageFormatter(bindingResult, messageSource, response);

        ApvValidator validator = new ApvValidator();
        validator.setLedgerDtoer(this.ledgerDtoers);
        validator.validate(apv, bindingResult);

        if (bindingResult.hasErrors()) {
            messageFormatter.buildErrorMessages();
            response = messageFormatter.getResponse();
        } else {
            User createdBy = authenticationFacade.getLoggedIn();
            AccountsPayableVoucher existingApv = null;

            Integer voucherYear = Integer.parseInt(GlobalConstant.YYYY_DATE_FORMAT.format(apv.getVoucherDate()));
            User approvingOfficer = userRepo.findOneByAccountNo(apv.getApprovingOfficer().getAccountNo());
            User checker = userRepo.findOneByAccountNo(apv.getChecker().getAccountNo());

            Boolean insertMode = apv.getId() == null;
            if (insertMode) { // insert mode
                Object latestApvCode = apvRepo.findLatestApvCodeByYear(voucherYear);
                apv.setCode(generatorFacade.voucherCode("APV", (latestApvCode == null ? "" : String.valueOf(latestApvCode)), apv.getVoucherDate()));

                DocumentStatus documentStatus = new DocumentStatus();
                documentStatus.setId(com.tri.erp.spring.model.enums.DocumentStatus.DOCUMENT_CREATED.getId());
                Workflow wf = new Workflow();
                wf.setId(com.tri.erp.spring.model.enums.Workflow.APV.getId());

                apv.setDocumentStatus(documentStatus);
                apv.setCreatedBy(createdBy);
                apv.setTransaction(generatorFacade.transaction());
                apv.setWorkflow(wf);

                existingApv = apv;

            } else {
                existingApv = apvRepo.findOne(apv.getId());

                List<Integer> statusAllowed = new ArrayList();
                statusAllowed.add(com.tri.erp.spring.model.enums.DocumentStatus.DOCUMENT_CREATED.getId());
                statusAllowed.add(com.tri.erp.spring.model.enums.DocumentStatus.RETURNED_TO_CREATOR.getId());

                if (statusAllowed.indexOf(existingApv.getDocumentStatus().getId()) < 0) {

                    ArrayList<String> messages = new ArrayList();
                    messages.add("Action is not allowed");

                    response.isNotAuthorized(true);
                    response.setMessages(messages);
                    response.setSuccess(false);

                    return response;
                }
            }

            // editable fields
            existingApv.setVendor(apv.getVendor());
            existingApv.setParticulars(apv.getParticulars());
            existingApv.setVoucherDate(apv.getVoucherDate());
            existingApv.setDueDate(apv.getDueDate());
            existingApv.setApprovingOfficer(approvingOfficer);
            existingApv.setChecker(checker);
            existingApv.setYear(voucherYear);
            existingApv.setAmount(apv.getAmount());

            AccountsPayableVoucher newApv = apvRepo.save(existingApv);

            if (newApv != null) {

                ledgerFacade.postGeneralLedger(newApv.getTransaction(), apv.getGeneralLedgerLines(),  apv.getSubLedgerLines());

                if (insertMode) { // log action only when adding document
                    documentProcessingFacade.processAction(newApv.getTransaction(), null, newApv.getWorkflow(), createdBy);
                }

                response.setModelId(newApv.getId());
                response.setSuccessMessage("APV successfully saved!");
                response.setSuccess(true);
            }
        }
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ApvListDto> findAll() {
        List<AccountsPayableVoucher> vouchers = apvRepo.findAll();

        List<ApvListDto> returnVouchers = new ArrayList<>();
        if (!Checker.collectionIsEmpty(vouchers)) {
            for(AccountsPayableVoucher apv : vouchers) {
                ApvListDto apvListDto = new ApvListDto();
                apvListDto.setId(apv.getId());
                apvListDto.setTransId(apv.getTransaction().getId());
                apvListDto.setSupplierAccountNo(apv.getVendor().getAccountNo());
                apvListDto.setAmount(apv.getAmount());
                apvListDto.setDate(apv.getVoucherDate());
                apvListDto.setLocalCode(apv.getCode());
                apvListDto.setParticulars(apv.getParticulars());
                apvListDto.setStatus(apv.getDocumentStatus().getStatus());
                apvListDto.setSupplier(apv.getVendor().getName());

                returnVouchers.add(apvListDto);
            }

            return returnVouchers;
        }
        return null;
    }

    @Override
    public ApvDto findById(Integer id) {

        AccountsPayableVoucher accountsPayableVoucher =  apvRepo.findOne(id);
        ApvDto apvDto = new ApvDto();

        if (accountsPayableVoucher != null) {
            apvDto.setId(accountsPayableVoucher.getId());
            apvDto.setParticulars(accountsPayableVoucher.getParticulars());
            apvDto.setLocalCode(accountsPayableVoucher.getCode());
            apvDto.setTransId(accountsPayableVoucher.getTransaction().getId());

            SlEntity approvingOfficer = slEntityRepo.findOne(accountsPayableVoucher.getApprovingOfficer().getAccountNo());
            SlEntity checker = slEntityRepo.findOne(accountsPayableVoucher.getChecker().getAccountNo());

            apvDto.setApprovingOfficer(approvingOfficer);
            apvDto.setChecker(checker);
            apvDto.setDueDate(accountsPayableVoucher.getDueDate());
            apvDto.setVendor(accountsPayableVoucher.getVendor());
            apvDto.setVoucherDate(accountsPayableVoucher.getVoucherDate());
            apvDto.setAmount(accountsPayableVoucher.getAmount());
            apvDto.setDocumentStatus(accountsPayableVoucher.getDocumentStatus());
            apvDto.setCreated(accountsPayableVoucher.getCreatedAt());
            apvDto.setLastUpdated(accountsPayableVoucher.getUpdatedAt());
        }

        return  apvDto;
    }

    @Override
    public  List<ApvListDto> findByStatusId(Integer id) {
        List<AccountsPayableVoucher> vouchers = apvRepo.findByDocumentStatusId(id);

        List<ApvListDto> returnVouchers = new ArrayList<>();
        if (!Checker.collectionIsEmpty(vouchers)) {
            for(AccountsPayableVoucher apv : vouchers) {
                ApvListDto apvListDto = new ApvListDto();
                apvListDto.setId(apv.getId());
                apvListDto.setTransId(apv.getTransaction().getId());
                apvListDto.setSupplierAccountNo(apv.getVendor().getAccountNo());
                apvListDto.setAmount(apv.getAmount());
                apvListDto.setDate(apv.getVoucherDate());
                apvListDto.setLocalCode(apv.getCode());
                apvListDto.setParticulars(apv.getParticulars());
                apvListDto.setStatus(apv.getDocumentStatus().getStatus());
                apvListDto.setSupplier(apv.getVendor().getName());

                returnVouchers.add(apvListDto);
            }

            return returnVouchers;
        }
        return null;
    }

    @Override
    public HashMap reportParameters(Integer vid, HttpServletRequest request) {
        HashMap<String, Object> params = ReportUtil.setupSharedReportHeaders(request);

        AccountsPayableVoucher payableVoucher = apvRepo.findOne(vid);

        if (payableVoucher != null) {
            params.put("VOUCHER_NO", payableVoucher.getCode());
            params.put("V_DATE", payableVoucher.getVoucherDate());
//            params.put("AMOUNT_IN_WORDS", CurrencyIntoWords.convert(payableVoucher.getAmount()));
            params.put("TOTAL", payableVoucher.getAmount());
            params.put("APPROVAR", payableVoucher.getApprovingOfficer().getFullName());
            params.put("CHECKER", payableVoucher.getChecker().getFullName());
            params.put("PREPARAR", payableVoucher.getCreatedBy().getFullName());
            params.put("SUPPLIER_NAME", payableVoucher.getVendor().getName());
            params.put("SUPPLIER_ADDR", payableVoucher.getVendor().getAddress());
            params.put("DUE_DATE", payableVoucher.getDueDate());
            params.put("PARTICULARS", payableVoucher.getParticulars());
            params.put("APPROVAR_POS", "");
            params.put("CHECKER_POS", "");
            params.put("PREPARAR_POS", "");
        }

        return params;
    }

    @Override
    public JRDataSource datasource(Integer vid) {
        List<APDetail> details = new ArrayList<>();

        AccountsPayableVoucher voucher = apvRepo.findOne(vid);
        if (voucher != null) {
            List<GeneralLedgerLineDto2> ledgerLineDto2s = ledgerDtoers.getGLAccountEntriesDtoByTrans(voucher.getTransaction().getId());

            if (!Checker.collectionIsEmpty(ledgerLineDto2s)) {
                for(GeneralLedgerLineDto2 dto : ledgerLineDto2s) {
                    APDetail d = new APDetail();

                    d.setInvoiceNumber("");
                    d.setParticulars(dto.getDescription());
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
        AccountsPayableVoucher accountsPayableVoucher =  apvRepo.findOne(postData.getDocumentId());

        if (accountsPayableVoucher != null && accountsPayableVoucher.getDocumentStatus().getId() != com.tri.erp.spring.model.enums.DocumentStatus.APPROVED.getId()) {
            DocumentWorkflowActionMap actionMap = workflowActionMapRepo.findOne(postData.getWorkflowActionsDto().getActionMapId());
            DocumentStatus afterActionDocumentStatus = actionMap.getAfterActionDocumentStatus();

            accountsPayableVoucher.setDocumentStatus(afterActionDocumentStatus);
            accountsPayableVoucher.setUpdatedAt(null);
            accountsPayableVoucher = apvRepo.save(accountsPayableVoucher);

            if (accountsPayableVoucher != null) {
                documentProcessingFacade.processAction(accountsPayableVoucher.getTransaction(), actionMap, null, processedBy);

                response.setSuccessMessage("Document successfully processed");
                response.setSuccess(true);
            }

        }
        return response;
    }
}
