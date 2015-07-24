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
import com.tri.erp.spring.response.reports.APDetail;
import com.tri.erp.spring.response.reports.CommonLedgerDetail;
import com.tri.erp.spring.response.reports.SalesVoucherDetail;
import com.tri.erp.spring.service.interfaces.PrintableVoucher;
import com.tri.erp.spring.service.interfaces.SalesVoucherService;
import com.tri.erp.spring.validator.ApvValidator;
import com.tri.erp.spring.validator.SalesVoucherValidator;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by TSI Admin on 10/9/2014.
 */

@Service(value = "salesVoucherServiceImpl")
public class SalesVoucherServiceImpl implements SalesVoucherService, PrintableVoucher {

    @Autowired
    GeneratorFacade generatorFacade;

    @Autowired
    private AuthenticationFacade authenticationFacade;

    @Autowired
    SalesVoucherRepo svRepo;

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
        SalesVoucher sv = (SalesVoucher) v;
        return this.processCreate(sv, bindingResult, messageSource);
    }

    @Override
    @Transactional
    public PostResponse processCreate(Document v, BindingResult bindingResult, MessageSource messageSource) {
        SalesVoucher sv = (SalesVoucher) v;
        PostResponse response = new PostResponse();
        MessageFormatter messageFormatter = new MessageFormatter(bindingResult, messageSource, response);

        SalesVoucherValidator validator = new SalesVoucherValidator();
        validator.setLedgerDtoer(this.ledgerDtoers);
        validator.validate(sv, bindingResult);

        if (bindingResult.hasErrors()) {
            messageFormatter.buildErrorMessages();
            response = messageFormatter.getResponse();
        } else {
            User createdBy = authenticationFacade.getLoggedIn();
            SalesVoucher existingSv = null;

            Integer voucherYear = Integer.parseInt(GlobalConstant.YYYY_DATE_FORMAT.format(sv.getVoucherDate()));
            User approvingOfficer = userRepo.findOneByAccountNo(sv.getApprovingOfficer().getAccountNo());
            User checker = userRepo.findOneByAccountNo(sv.getChecker().getAccountNo());

            Boolean insertMode = sv.getId() == null;
            if (insertMode) { // insert mode
                Object latestSvCode = svRepo.findLatestSvCodeByYear(voucherYear);
                sv.setCode(generatorFacade.voucherCode("SV", (latestSvCode == null ? "" : String.valueOf(latestSvCode)), sv.getVoucherDate()));

                DocumentStatus documentStatus = new DocumentStatus();
                documentStatus.setId(com.tri.erp.spring.model.enums.DocumentStatus.DOCUMENT_CREATED.getId());
                Workflow wf = new Workflow();
                wf.setId(com.tri.erp.spring.model.enums.Workflow.SALES_VOUCHER.getId());

                sv.setDocumentStatus(documentStatus);
                sv.setCreatedBy(createdBy);
                sv.setTransaction(generatorFacade.transaction());
                sv.setWorkflow(wf);

                existingSv = sv;

            } else {
                existingSv = svRepo.findOne(sv.getId());

                List<Integer> statusAllowed = new ArrayList();
                statusAllowed.add(com.tri.erp.spring.model.enums.DocumentStatus.DOCUMENT_CREATED.getId());
                statusAllowed.add(com.tri.erp.spring.model.enums.DocumentStatus.RETURNED_TO_CREATOR.getId());

                if (statusAllowed.indexOf(existingSv.getDocumentStatus().getId()) < 0) {

                    ArrayList<String> messages = new ArrayList();
                    messages.add("Action is not allowed");

                    response.isNotAuthorized(true);
                    response.setMessages(messages);
                    response.setSuccess(false);

                    return response;
                }
            }

            // editable fields
            existingSv.setParticulars(sv.getParticulars());
            existingSv.setVoucherDate(sv.getVoucherDate());
            existingSv.setApprovingOfficer(approvingOfficer);
            existingSv.setChecker(checker);
            existingSv.setYear(voucherYear);
            existingSv.setAmount(sv.getAmount());

            SalesVoucher newSv = svRepo.save(existingSv);

            if (newSv != null) {

                ledgerFacade.postGeneralLedger(newSv.getTransaction(), sv.getGeneralLedgerLines(), sv.getSubLedgerLines());

                if (insertMode) { // log action only when adding document
                    documentProcessingFacade.processAction(newSv.getTransaction(), null, newSv.getWorkflow(), createdBy);
                }

                response.setModelId(newSv.getId());
                response.setSuccessMessage("Sales Voucher successfully saved!");
                response.setSuccess(true);
            }
        }
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public List<SalesVoucherListDto> findAll() {
        List<SalesVoucher> vouchers = svRepo.findAll();

        List<SalesVoucherListDto> returnVouchers = new ArrayList<>();
        if (!Checker.collectionIsEmpty(vouchers)) {
            for(SalesVoucher sv : vouchers) {
                SalesVoucherListDto svListDto = new SalesVoucherListDto();
                svListDto.setId(sv.getId());
                svListDto.setTransId(sv.getTransaction().getId());
                svListDto.setAmount(sv.getAmount());
                svListDto.setDate(sv.getVoucherDate());
                svListDto.setLocalCode(sv.getCode());
                svListDto.setParticulars(sv.getParticulars());
                svListDto.setStatus(sv.getDocumentStatus().getStatus());

                returnVouchers.add(svListDto);
            }

            return returnVouchers;
        }
        return null;
    }

    @Override
    public SalesVoucherDto findById(Integer id) {

        SalesVoucher salesVoucher =  svRepo.findOne(id);
        SalesVoucherDto svDto = new SalesVoucherDto();

        if (salesVoucher != null) {
            svDto.setId(salesVoucher.getId());
            svDto.setParticulars(salesVoucher.getParticulars());
            svDto.setLocalCode(salesVoucher.getCode());
            svDto.setTransId(salesVoucher.getTransaction().getId());

            SlEntity approvingOfficer = slEntityRepo.findOne(salesVoucher.getApprovingOfficer().getAccountNo());
            SlEntity checker = slEntityRepo.findOne(salesVoucher.getChecker().getAccountNo());

            svDto.setApprovingOfficer(approvingOfficer);
            svDto.setChecker(checker);
            svDto.setVoucherDate(salesVoucher.getVoucherDate());
            svDto.setAmount(salesVoucher.getAmount());
            svDto.setDocumentStatus(salesVoucher.getDocumentStatus());
            svDto.setCreated(salesVoucher.getCreatedAt());
            svDto.setLastUpdated(salesVoucher.getUpdatedAt());
        }

        return  svDto;
    }

    @Override
    public  List<SalesVoucherListDto> findByStatusId(Integer id) {
        List<SalesVoucher> vouchers = svRepo.findByDocumentStatusId(id);

        List<SalesVoucherListDto> returnVouchers = new ArrayList<>();
        if (!Checker.collectionIsEmpty(vouchers)) {
            for(SalesVoucher sv : vouchers) {
                SalesVoucherListDto svListDto = new SalesVoucherListDto();
                svListDto.setId(sv.getId());
                svListDto.setTransId(sv.getTransaction().getId());
                svListDto.setAmount(sv.getAmount());
                svListDto.setDate(sv.getVoucherDate());
                svListDto.setLocalCode(sv.getCode());
                svListDto.setParticulars(sv.getParticulars());
                svListDto.setStatus(sv.getDocumentStatus().getStatus());

                returnVouchers.add(svListDto);
            }

            return returnVouchers;
        }
        return null;
    }

    @Override
    public HashMap reportParameters(Integer vid, HttpServletRequest request) {
        HashMap<String, Object> params = ReportUtil.setupSharedReportHeaders(request);

        SalesVoucher salesVoucher = svRepo.findOne(vid);

        if (salesVoucher != null) {
            params.put("VOUCHER_NO", salesVoucher.getCode());
            params.put("V_DATE", salesVoucher.getVoucherDate());
//            params.put("AMOUNT_IN_WORDS", CurrencyIntoWords.convert(salesVoucher.getAmount()));
            params.put("TOTAL", salesVoucher.getAmount());
            params.put("APPROVAR", salesVoucher.getApprovingOfficer().getFullName());
            params.put("CHECKER", salesVoucher.getChecker().getFullName());
            params.put("PREPARAR", salesVoucher.getCreatedBy().getFullName());
            params.put("PARTICULARS", salesVoucher.getParticulars());
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

        SalesVoucher voucher = svRepo.findOne(vid);
        if (voucher != null) {
            details = ledgerDtoers.getVoucherLedgerLines(voucher.getTransaction().getId());
        }
        return new JRBeanCollectionDataSource(details);
    }

    @Override
    public PostResponse process(ProcessDocumentDto postData, BindingResult bindingResult, MessageSource messageSource) {
        PostResponse response = new PostResponse();

        User processedBy = authenticationFacade.getLoggedIn();
        SalesVoucher salesVoucher =  svRepo.findOne(postData.getDocumentId());

        if (salesVoucher != null) {
            DocumentWorkflowActionMap actionMap = workflowActionMapRepo.findOne(postData.getWorkflowActionsDto().getActionMapId());
            DocumentStatus afterActionDocumentStatus = actionMap.getAfterActionDocumentStatus();

            salesVoucher.setDocumentStatus(afterActionDocumentStatus);
            salesVoucher.setUpdatedAt(null);
            salesVoucher = svRepo.save(salesVoucher);

            if (salesVoucher != null) {
                documentProcessingFacade.processAction(salesVoucher.getTransaction(), actionMap, null, processedBy);

                response.setSuccessMessage("Document successfully processed");
                response.setSuccess(true);
            }

        }
        return response;
    }
}
