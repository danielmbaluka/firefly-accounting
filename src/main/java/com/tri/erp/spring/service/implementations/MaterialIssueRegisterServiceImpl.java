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
import com.tri.erp.spring.response.*;
import com.tri.erp.spring.response.reports.APDetail;
import com.tri.erp.spring.response.reports.CommonLedgerDetail;
import com.tri.erp.spring.service.interfaces.MaterialIssueRegisterService;
import com.tri.erp.spring.service.interfaces.PrintableVoucher;
import com.tri.erp.spring.validator.MaterialIssueRegisterValidator;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * Created by nsutgio2015 on 4/29/2015.
 */
@Service(value = "mirServiceImpl")
public class MaterialIssueRegisterServiceImpl implements MaterialIssueRegisterService, PrintableVoucher {

    @Autowired
    GeneratorFacade generatorFacade;

    @Autowired
    private AuthenticationFacade authenticationFacade;

    @Autowired
    MaterialIssueRegisterRepo mirRepo;

    @Autowired
    MaterialIssueRegisterDetailRepo mirDetailRepo;

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

    @Override
    public PostResponse process(ProcessDocumentDto postData, BindingResult bindingResult, MessageSource messageSource) {
        PostResponse response = new PostResponse();

        User processedBy = authenticationFacade.getLoggedIn();
        MaterialIssueRegister mir =  mirRepo.findOne(postData.getDocumentId());

        if (mir != null) {
            DocumentWorkflowActionMap actionMap = workflowActionMapRepo.findOne(postData.getWorkflowActionsDto().getActionMapId());
            DocumentStatus afterActionDocumentStatus = actionMap.getAfterActionDocumentStatus();

            mir.setDocumentStatus(afterActionDocumentStatus);
            mir.setUpdatedAt(null);
            mir = mirRepo.save(mir);

            if (mir != null) {
                documentProcessingFacade.processAction(mir.getTransaction(), actionMap, null, processedBy);

                response.setSuccessMessage("Document successfully processed");
                response.setSuccess(true);
            }

        }
        return response;
    }

    @Override
    public List<MaterialIssueRegisterDto> findAll() {
        return null;
    }

    @Override
    public MaterialIssueRegisterDto findById(Integer id) {

        MaterialIssueRegister mir =  mirRepo.findOne(id);
        MaterialIssueRegisterDto dto = new MaterialIssueRegisterDto();

        if (mir != null) {
            dto.setId(mir.getId());
            dto.setParticulars(mir.getParticulars());
            dto.setLocalCode(mir.getCode());
            dto.setTransId(mir.getTransaction().getId());

            SlEntity approvingOfficer = slEntityRepo.findOne(mir.getApprovingOfficer().getAccountNo());
            SlEntity checker = slEntityRepo.findOne(mir.getChecker().getAccountNo());

            dto.setsLApprovingOfficer(approvingOfficer);
            dto.setsLChecker(checker);
            dto.setVoucherDate(mir.getVoucherDate());
            dto.setStatus(mir.getDocumentStatus().getStatus());
            dto.setVoucherDate(mir.getCreatedAt());
            dto.setLastUpdated(mir.getUpdatedAt());
        }

        return  dto;
    }

    @Override
    public List<MaterialIssueRegisterDetailDto> getDetails(Integer id) {
        return null;
    }

    @Override
    public List<MaterialIssueRegisterDto> getAllMaterialIssueRegister() {

        List<Objects[]> regs = mirRepo.getAllMaterialIssueRegister();
        List<MaterialIssueRegisterDto> ret = new ArrayList<>();

        for(Object[] o : regs)
        {
            MaterialIssueRegisterDto dto = new MaterialIssueRegisterDto();

            String code = (String)o[0];
            String particulars = (String)o[1];
            String userStr = (String)o[2];
            String checkerStr = (String)o[3];
            String approvingOfficerStr = (String)o[4];
            String status = (String)o[5];
            java.sql.Date voucherDate = (java.sql.Date)o[6];
            Integer id = (Integer)o[7];

            dto.setLocalCode(code);
            dto.setParticulars(particulars);
            dto.setVoucherDate(voucherDate);

            User user = new User();
            User checker = new User();
            User approvingOfficer = new User();

            user.setFullName(userStr);
            checker.setFullName(checkerStr);
            approvingOfficer.setFullName(approvingOfficerStr);

            dto.setCreatedBy(user);
            dto.setChecker(checker);
            dto.setApprovingOfficer(approvingOfficer);
            dto.setStatus(status);
            dto.setId(id);

            ret.add(dto);
        }

        return ret;
    }

    private boolean detailsSaved(MaterialIssueRegister mir, Integer newMirId) {
        try {

            ArrayList<MaterialIssueRegisterDetail> mirDetails = new ArrayList<>();
            MaterialIssueRegister newMir = mirRepo.findOne(newMirId);

            for (MaterialIssueRegisterDetail detail : mir.getMaterialIssueRegisterDetails()) {
                detail.setMaterialIssueRegister(newMir);
            }

            List<MaterialIssueRegisterDetail> newMirDetail = mirDetailRepo.save(mir.getMaterialIssueRegisterDetails());

            return newMirDetail.size() > 0;

        }catch(Exception ex){
            return false;
        }
    }


    @Override
    public PostResponse processUpdate(Document v, BindingResult bindingResult, MessageSource messageSource) {
        MaterialIssueRegister mir = (MaterialIssueRegister) v;
        return this.processCreate(mir, bindingResult, messageSource);
    }

    @Override
    public PostResponse processCreate(Document v, BindingResult bindingResult, MessageSource messageSource) {
        MaterialIssueRegister mir = (MaterialIssueRegister) v;
        PostResponse response = new PostResponse();
        MessageFormatter messageFormatter = new MessageFormatter(bindingResult, messageSource, response);

        MaterialIssueRegisterValidator validator = new MaterialIssueRegisterValidator();
        validator.setService(this);
        validator.validate(mir, bindingResult);

        if (bindingResult.hasErrors()) {
            messageFormatter.buildErrorMessages();
            response = messageFormatter.getResponse();
        } else {
            User createdBy = authenticationFacade.getLoggedIn();
            MaterialIssueRegister existingMir = null;

            Integer voucherYear = Integer.parseInt(GlobalConstant.YYYY_DATE_FORMAT.format(mir.getVoucherDate()));
            User approvingOfficer = userRepo.findOneByAccountNo(mir.getApprovingOfficer().getAccountNo());
            User checker = userRepo.findOneByAccountNo(mir.getChecker().getAccountNo());

            Boolean insertMode = mir.getId() == null;
            if (insertMode) { // insert mode
                Object latestMirCode = mirRepo.findLatestMaterialIssueRegisterCodeByYear(voucherYear);
                mir.setCode(generatorFacade.voucherCode("MIR", (latestMirCode == null ? "" : String.valueOf(latestMirCode)), mir.getVoucherDate()));

                DocumentStatus documentStatus = new DocumentStatus();
                documentStatus.setId(com.tri.erp.spring.model.enums.DocumentStatus.DOCUMENT_CREATED.getId());
                Workflow wf = new Workflow();
                wf.setId(1); // TODO: attach workflow to created voucher, coming from the global settings

                mir.setDocumentStatus(documentStatus);
                mir.setCreatedBy(createdBy);
                mir.setTransaction(generatorFacade.transaction());
                mir.setWorkflow(wf);

                existingMir = mir;

            } else {
                existingMir = mirRepo.findOne(mir.getId());

                List<Integer> statusAllowed = new ArrayList();
                statusAllowed.add(com.tri.erp.spring.model.enums.DocumentStatus.DOCUMENT_CREATED.getId());
                statusAllowed.add(com.tri.erp.spring.model.enums.DocumentStatus.RETURNED_TO_CREATOR.getId());

                if (statusAllowed.indexOf(existingMir.getDocumentStatus().getId()) < 0) {

                    ArrayList<String> messages = new ArrayList();
                    messages.add("Action is not allowed");

                    response.isNotAuthorized(true);
                    response.setMessages(messages);
                    response.setSuccess(false);

                    return response;
                }
            }

            // editable fields
            existingMir.setParticulars(mir.getParticulars());
            existingMir.setVoucherDate(mir.getVoucherDate());
            existingMir.setApprovingOfficer(approvingOfficer);
            existingMir.setChecker(checker);
            existingMir.setYear(voucherYear);

            MaterialIssueRegister newMir = mirRepo.save(existingMir);

            if (newMir != null) {
                ledgerFacade.postGeneralLedger(newMir.getTransaction(), mir.getGeneralLedgerLines(), mir.getSubLedgerLines());

                if (insertMode) { // log action only when adding document
                    documentProcessingFacade.processAction(newMir.getTransaction(), null, newMir.getWorkflow(), createdBy);
                }

                if(detailsSaved(mir, newMir.getId())) {

                    response.setModelId(newMir.getId());
                    response.setSuccessMessage("MIR successfully saved!");
                    response.setSuccess(true);
                }
            }
        }

        return response;
    }

    @Override
    public HashMap reportParameters(Integer vid, HttpServletRequest request) {
        HashMap<String, Object> params = ReportUtil.setupSharedReportHeaders(request);

        MaterialIssueRegister mir = mirRepo.findOne(vid);

        if (mir != null) {
            params.put("VOUCHER_NO", mir.getCode());
            params.put("V_DATE", mir.getVoucherDate());
//            params.put("AMOUNT_IN_WORDS", CurrencyIntoWords.convert(mir.getAmount()));
            params.put("TOTAL", mir.getAmount());
            params.put("APPROVAR", mir.getApprovingOfficer().getFullName());
            params.put("CHECKER", mir.getChecker().getFullName());
            params.put("PREPARAR", mir.getCreatedBy().getFullName());
            params.put("PARTICULARS", mir.getParticulars());
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

        MaterialIssueRegister voucher = mirRepo.findOne(vid);
        if (voucher != null) {
            details = ledgerDtoers.getVoucherLedgerLines(voucher.getTransaction().getId());
        }
        return new JRBeanCollectionDataSource(details);
    }
}
