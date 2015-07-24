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
import com.tri.erp.spring.repo.*;
import com.tri.erp.spring.response.PostResponse;
import com.tri.erp.spring.response.ProcessDocumentDto;
import com.tri.erp.spring.response.reports.CommonLedgerDetail;
import com.tri.erp.spring.service.interfaces.JvService;
import com.tri.erp.spring.service.interfaces.PrintableVoucher;
import com.tri.erp.spring.validator.JvValidator;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by TSI Admin on 6/30/2015.
 */
@Service(value = "jvServiceImpl")
public class JvServiceImpl implements JvService, PrintableVoucher {

    @Autowired
    JournalVoucherRepo jvRepo;

    @Autowired
    private AuthenticationFacade authenticationFacade;

    @Autowired
    UserRepo userRepo;

    @Autowired
    SlEntityRepo slEntityRepo;

    @Autowired
    LedgerFacadeImpl ledgerFacade;

    @Autowired
    LedgerDtoerImpl ledgerDtoers;

    @Autowired
    DocumentWorkflowActionMapRepo workflowActionMapRepo;

    @Autowired
    DocumentProcessingFacade documentProcessingFacade;

    @Autowired
    GeneratorFacade generatorFacade;

    @Autowired
    TemporaryBatchRepo temporaryBatchRepo;

    @Override
    public PostResponse process(ProcessDocumentDto postData, BindingResult bindingResult, MessageSource messageSource) {
        PostResponse response = new PostResponse();

        User processedBy = authenticationFacade.getLoggedIn();
        JournalVoucher checkVoucher =  jvRepo.findOne(postData.getDocumentId());

        if (checkVoucher != null && checkVoucher.getDocumentStatus().getId() != com.tri.erp.spring.model.enums.DocumentStatus.APPROVED.getId()) {
            DocumentWorkflowActionMap actionMap = workflowActionMapRepo.findOne(postData.getWorkflowActionsDto().getActionMapId());
            DocumentStatus afterActionDocumentStatus = actionMap.getAfterActionDocumentStatus();

            checkVoucher.setDocumentStatus(afterActionDocumentStatus);
            checkVoucher.setUpdatedAt(null);
            checkVoucher = jvRepo.save(checkVoucher);

            if (checkVoucher != null) {
                documentProcessingFacade.processAction(checkVoucher.getTransaction(), actionMap, null, processedBy);

                response.setSuccessMessage("Document successfully processed");
                response.setSuccess(true);
            }
        }
        return response;
    }

    @Override
    public PostResponse processUpdate(Document v, BindingResult bindingResult, MessageSource messageSource) {
        JournalVoucher jv = (JournalVoucher) v;
        return this.processCreate(jv, bindingResult, messageSource);
    }

    @Override
    public PostResponse processCreate(Document v, BindingResult bindingResult, MessageSource messageSource) {
        JournalVoucher jv = (JournalVoucher) v;
        PostResponse response = new PostResponse();
        MessageFormatter messageFormatter = new MessageFormatter(bindingResult, messageSource, response);

        JvValidator validator = new JvValidator();
        validator.validate(jv, bindingResult);

        if (bindingResult.hasErrors()) {
            messageFormatter.buildErrorMessages();
            response = messageFormatter.getResponse();
        } else {
            User createdBy = authenticationFacade.getLoggedIn();
            JournalVoucher existingJv = null;

            Integer voucherYear = Integer.parseInt(GlobalConstant.YYYY_DATE_FORMAT.format(jv.getVoucherDate()));
            User approvingOfficer = userRepo.findOneByAccountNo(jv.getApprovingOfficer().getAccountNo());
            User checker = userRepo.findOneByAccountNo(jv.getChecker().getAccountNo());
            User recApp = userRepo.findOneByAccountNo(jv.getRecommendingOfficer().getAccountNo());
            User auditor = userRepo.findOneByAccountNo(jv.getAuditor().getAccountNo());

            Boolean insertMode = jv.getId() == null;
            if (insertMode) { // insert mode
                Object latestApvCode = jvRepo.findLatestVvCodeByYear(voucherYear);
                jv.setCode(generatorFacade.voucherCode("JV", (latestApvCode == null ? "" : String.valueOf(latestApvCode)), jv.getVoucherDate()));

                DocumentStatus documentStatus = new DocumentStatus();
                documentStatus.setId(com.tri.erp.spring.model.enums.DocumentStatus.DOCUMENT_CREATED.getId());
                Workflow wf = new Workflow();
                wf.setId(com.tri.erp.spring.model.enums.Workflow.JV.getId());

                jv.setDocumentStatus(documentStatus);
                jv.setCreatedBy(createdBy);
                jv.setTransaction(generatorFacade.transaction());
                jv.setWorkflow(wf);

                existingJv = jv;

            } else {
                existingJv = jvRepo.findOne(jv.getId());

                List<Integer> statusAllowed = new ArrayList();
                statusAllowed.add(com.tri.erp.spring.model.enums.DocumentStatus.DOCUMENT_CREATED.getId());
                statusAllowed.add(com.tri.erp.spring.model.enums.DocumentStatus.RETURNED_TO_CREATOR.getId());

                if (statusAllowed.indexOf(existingJv.getDocumentStatus().getId()) < 0) {

                    ArrayList<String> messages = new ArrayList();
                    messages.add("Action is not allowed");

                    response.isNotAuthorized(true);
                    response.setMessages(messages);
                    response.setSuccess(false);

                    return response;
                }
            }

            // editable fields
            existingJv.setExplanation(jv.getExplanation());
            existingJv.setVoucherDate(jv.getVoucherDate());
            existingJv.setApprovingOfficer(approvingOfficer);
            existingJv.setChecker(checker);
            existingJv.setRecommendingOfficer(recApp);
            existingJv.setAuditor(auditor);
            existingJv.setYear(voucherYear);
            existingJv.setAmount(jv.getAmount());

            JournalVoucher newJv = jvRepo.save(existingJv);

            if (newJv != null) {
                ledgerFacade.postGeneralLedger(newJv.getTransaction(), jv.getGeneralLedgerLines(), jv.getSubLedgerLines());

                if (insertMode) { // log action only when adding document
                    documentProcessingFacade.processAction(newJv.getTransaction(), null, newJv.getWorkflow(), createdBy);

                    if (jv.getTempBatchId() != null && jv.getTempBatchId() > 0) { // from original post data
                        TemporaryBatch temporaryBatch = temporaryBatchRepo.findOne(jv.getTempBatchId());
                        if (temporaryBatch != null) {
                            temporaryBatch.setVoucherCreated(true);
                            temporaryBatchRepo.save(temporaryBatch);
                        }
                    }
                }

                response.setModelId(newJv.getId());
                response.setSuccessMessage("JV successfully saved!");
                response.setSuccess(true);
            }
        }
        return response;
    }

    @Override
    public List<Map> findAll() {
        List<Map> mapList = new ArrayList<>();

        List<JournalVoucher> vouchers = jvRepo.findAll();
        if (!Checker.collectionIsEmpty(vouchers)) {
            for(JournalVoucher jv:vouchers) {
                mapList.add(composeJvMap(jv));
            }
        }
        return mapList;
    }

    @Override
    public Map findById(Integer id) {
        Map map = new HashMap();

        JournalVoucher journalVoucher = jvRepo.findOne(id);
        if (journalVoucher != null) {
            map = composeJvMap(journalVoucher);
        }

        return map;
    }

    private Map composeJvMap(JournalVoucher journalVoucher) {
        Map map = new HashMap();

        map.put("id", journalVoucher.getId());
        map.put("code", journalVoucher.getCode());
        map.put("voucherDate", journalVoucher.getVoucherDate());
        map.put("year", journalVoucher.getYear());
        map.put("transId", journalVoucher.getTransaction().getId());
        map.put("documentStatus", journalVoucher.getDocumentStatus());
        map.put("amount", journalVoucher.getAmount());
        map.put("explanation", journalVoucher.getExplanation());
        map.put("remarks", journalVoucher.getRemarks());
        map.put("createdAt", journalVoucher.getCreatedAt());
        map.put("updatedAt", journalVoucher.getUpdatedAt());

        Map crUser = new HashMap();
        crUser.put("accountNo", journalVoucher.getCreatedBy().getAccountNo());
        crUser.put("id", journalVoucher.getCreatedBy().getId());
        crUser.put("name", journalVoucher.getCreatedBy().getFullName());
        map.put("createdBy", crUser);

        Map chUser = new HashMap();
        chUser.put("accountNo", journalVoucher.getChecker().getAccountNo());
        chUser.put("id", journalVoucher.getChecker().getId());
        chUser.put("name", journalVoucher.getChecker().getFullName());
        map.put("checker", chUser);

        Map recUser = new HashMap();
        recUser.put("accountNo", journalVoucher.getRecommendingOfficer().getAccountNo());
        recUser.put("id", journalVoucher.getRecommendingOfficer().getId());
        recUser.put("name", journalVoucher.getRecommendingOfficer().getFullName());
        map.put("recommendingOfficer", recUser);

        Map auUser = new HashMap();
        auUser.put("accountNo", journalVoucher.getAuditor().getAccountNo());
        auUser.put("id", journalVoucher.getAuditor().getId());
        auUser.put("name", journalVoucher.getAuditor().getFullName());
        map.put("auditor", auUser);

        Map apUser = new HashMap();
        apUser.put("accountNo", journalVoucher.getApprovingOfficer().getAccountNo());
        apUser.put("id", journalVoucher.getApprovingOfficer().getId());
        apUser.put("name", journalVoucher.getApprovingOfficer().getFullName());
        map.put("approvingOfficer", apUser);

        return map;
    }

    @Override
    public HashMap reportParameters(Integer vid, HttpServletRequest request) {
        HashMap<String, Object> params = ReportUtil.setupSharedReportHeaders(request);

        JournalVoucher checkVoucher = jvRepo.findOne(vid);

        if (checkVoucher != null) {
            params.put("TRANS_ID", checkVoucher.getTransaction().getId());
            params.put("VOUCHER_NO", checkVoucher.getCode());
            params.put("V_DATE", checkVoucher.getVoucherDate());
            params.put("APPROVAR", checkVoucher.getApprovingOfficer().getFullName());
            params.put("APPROVAR_POS", "");
            params.put("CHECKER", checkVoucher.getChecker().getFullName());
            params.put("CHECKER_POS", "");
            params.put("PREPARAR", checkVoucher.getCreatedBy().getFullName());
            params.put("PREPARAR_POS", "");
            params.put("RECOMMENDAR", checkVoucher.getRecommendingOfficer().getFullName());
            params.put("RECOMMENDAR_POS", "");
            params.put("AUDITOR", checkVoucher.getAuditor().getFullName());
            params.put("AUDITOR_POS", "");
            params.put("EXPLANATION", checkVoucher.getExplanation().trim());
        }

        return params;
    }

    @Override
    public JRDataSource datasource(Integer vid) {
        List<CommonLedgerDetail> details = new ArrayList<>();

        JournalVoucher voucher = jvRepo.findOne(vid);
        if (voucher != null) {
            details = ledgerDtoers.getVoucherLedgerLines(voucher.getTransaction().getId());
        }
        return new JRBeanCollectionDataSource(details);
    }
}
