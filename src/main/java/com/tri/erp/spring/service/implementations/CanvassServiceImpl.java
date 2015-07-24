package com.tri.erp.spring.service.implementations;

import com.tri.erp.spring.commons.GlobalConstant;
import com.tri.erp.spring.commons.facade.AuthenticationFacade;
import com.tri.erp.spring.commons.facade.DocumentProcessingFacade;
import com.tri.erp.spring.commons.facade.GeneratorFacade;
import com.tri.erp.spring.commons.helpers.Checker;
import com.tri.erp.spring.commons.helpers.MessageFormatter;
import com.tri.erp.spring.commons.helpers.ReportUtil;
import com.tri.erp.spring.model.*;
import com.tri.erp.spring.repo.*;
import com.tri.erp.spring.response.*;
import com.tri.erp.spring.response.reports.CNVSDetail;
import com.tri.erp.spring.service.interfaces.CanvassService;
import com.tri.erp.spring.service.interfaces.PrintableVoucher;
import com.tri.erp.spring.validator.CanvassValidator;
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
 * Created by Personal on 5/12/2015.
 */
@Service(value = "cnvsServiceImpl")
public class CanvassServiceImpl implements CanvassService, PrintableVoucher {

    @Autowired
    GeneratorFacade generatorFacade;

    @Autowired
    private AuthenticationFacade authenticationFacade;

    @Autowired
    CanvassRepo canvassRepo;

    @Autowired
    UserRepo userRepo;

    @Autowired
    SlEntityRepo slEntityRepo;

    @Autowired
    CanvassDetailRepo canvassDetailRepo;

    @Autowired
    DocumentWorkflowActionMapRepo workflowActionMapRepo;

    @Autowired
    DocumentProcessingFacade documentProcessingFacade;

    @Autowired
    CanvassDetailServiceImpl canvassDetailDto;

    @Override
    @Transactional(readOnly = true)
    public Canvass findByCode(String code) {
        List<Canvass> canvasses = canvassRepo.findByCode(code);

        if (!Checker.collectionIsEmpty(canvasses)) {
            return canvasses.get(0);
        } else return null;
    }

    @Override
    @Transactional
    public PostResponse processUpdate(Document v, BindingResult bindingResult, MessageSource messageSource) {
        Canvass canvass = (Canvass) v;
        return this.processCreate(canvass, bindingResult, messageSource);
    }

    @Override
    @Transactional
    public PostResponse processCreate(Document v, BindingResult bindingResult, MessageSource messageSource) {
        Canvass canvass = (Canvass) v;
        PostResponse response = new PostResponse();
        response.setSuccess(false);

        MessageFormatter messageFormatter = new MessageFormatter(bindingResult, messageSource, response);

        CanvassValidator validator = new CanvassValidator();
        validator.setService(this);
        validator.validate(canvass, bindingResult);

        if (bindingResult.hasErrors()) {
            messageFormatter.buildErrorMessages();
            response = messageFormatter.getResponse();
        } else {
            User createdBy = authenticationFacade.getLoggedIn();
            Canvass existingCanvass = null;

            Integer voucherYear = Integer.parseInt(GlobalConstant.YYYY_DATE_FORMAT.format(canvass.getVoucherDate()));

            Boolean insertMode = canvass.getId() == null;
            if (insertMode) { // insert mode
                Object latestCanvassCode = canvassRepo.findLatestCanvassCodeByYear(voucherYear);
                canvass.setCode(generatorFacade.voucherCode("CF", (latestCanvassCode == null ? "" : String.valueOf(latestCanvassCode)), canvass.getVoucherDate()));

                DocumentStatus documentStatus = new DocumentStatus();
                documentStatus.setId(com.tri.erp.spring.model.enums.DocumentStatus.DOCUMENT_CREATED.getId());
                canvass.setDocumentStatus(documentStatus);

                Workflow wf = new Workflow();
                wf.setId(1);

                canvass.setTransaction(generatorFacade.transaction());
                canvass.setWorkflow(wf);
                canvass.setCreatedBy(createdBy);
                existingCanvass = canvass;
            } else {
                existingCanvass = canvassRepo.findOne(canvass.getId());
            }
            existingCanvass.setVendor(canvass.getVendor());
            existingCanvass.setVoucherDate(canvass.getVoucherDate());
            existingCanvass.setYear(voucherYear);
            existingCanvass.setApprovingOfficer(createdBy);

            Canvass newCanvass = canvassRepo.save(existingCanvass);

            if (newCanvass != null) {
                if (!insertMode) {
                    canvassDetailRepo.deleteByCanvassId(existingCanvass.getId());
                }

                if (insertMode) { // log action only when adding document
                    documentProcessingFacade.processAction(newCanvass.getTransaction(), null, newCanvass.getWorkflow(), createdBy);
                }

                ArrayList<CanvassDetailDto> canvassDetails = canvass.getCanvassDetails();
                for(CanvassDetailDto canvassDetailLine: canvassDetails) {

                    CanvassDetail canvassDetail = new CanvassDetail();

                    Canvass canvass1 = new Canvass();
                    canvass1.setId(newCanvass.getId());
                    canvassDetail.setCanvass(canvass1);

                    RvDetail rvDetail = new RvDetail();
                    rvDetail.setId(canvassDetailLine.getRvDetailId());
                    canvassDetail.setRvDetail(rvDetail);

                    canvassDetailRepo.save(canvassDetail);
                }

                response.setModelId(newCanvass.getId());
                response.setSuccessMessage("Canvass successfully saved!");
                response.setSuccess(true);
            }
        }

        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CanvassListDto> findAll() {
        List<Canvass> vouchers = canvassRepo.findAll();

        List<CanvassListDto> returnVouchers = new ArrayList<>();
        if (!Checker.collectionIsEmpty(vouchers)) {
            for(Canvass canvass : vouchers) {
                CanvassListDto canvassListDto = new CanvassListDto();
                canvassListDto.setId(canvass.getId());
                canvassListDto.setVoucherDate(canvass.getVoucherDate());
                canvassListDto.setLocalCode(canvass.getCode());
                canvassListDto.setSupplier(canvass.getVendor().getName());

                SlEntity createdBy = slEntityRepo.findOne(canvass.getCreatedBy().getAccountNo());
                canvassListDto.setPreparedBy(createdBy.getName());

                returnVouchers.add(canvassListDto);
            }

            return returnVouchers;
        }
        return null;
    }

    @Override
    public CanvassDto findById(Integer id) {
        Canvass canvass =  canvassRepo.findOne(id);
        CanvassDto canvassDto = new CanvassDto();

        if (canvass != null) {
            canvassDto.setId(canvass.getId());
            canvassDto.setLocalCode(canvass.getCode());
            canvassDto.setTransId(canvass.getTransaction().getId());

            SlEntity createdBy = slEntityRepo.findOne(canvass.getCreatedBy().getAccountNo());
            SlEntity approvedBy = slEntityRepo.findOne(canvass.getApprovingOfficer().getAccountNo());

            canvassDto.setCreatedBy(createdBy);
            canvassDto.setApprovedBy(approvedBy);
            canvassDto.setVendor(canvass.getVendor());
            canvassDto.setVoucherDate(canvass.getVoucherDate());
            canvassDto.setDocumentStatus(canvass.getDocumentStatus());
            canvassDto.setCreated(canvass.getCreatedAt());
            canvassDto.setLastUpdated(canvass.getUpdatedAt());
        }

        return  canvassDto;
    }

    @Override
    public HashMap reportParameters(Integer id, HttpServletRequest request) {
        HashMap<String, Object> params = ReportUtil.setupSharedReportHeaders(request);

        Canvass canvass = canvassRepo.findOne(id);

        if (canvass != null) {
            params.put("VOUCHER_NO", canvass.getCode());
            params.put("V_DATE", canvass.getVoucherDate());
            params.put("APPROVEDBY", canvass.getApprovingOfficer().getFullName());
            params.put("SUPPLIER", canvass.getVendor().getName());
            params.put("APPROVEDBY_POS", "");
        }

        return params;
    }

    @Override
    public JRDataSource datasource(Integer id) {
        List<CNVSDetail> details = new ArrayList<>();

        Canvass voucher = canvassRepo.findOne(id);
        if (voucher != null) {
            List<CanvassDetailDto> cnvsDetailLineDtos = canvassDetailDto.getCanvassDetails(id);

            if (!Checker.collectionIsEmpty(cnvsDetailLineDtos)) {
                for(CanvassDetailDto dto : cnvsDetailLineDtos) {
                    CNVSDetail d = new CNVSDetail();

                    d.setId(cnvsDetailLineDtos.indexOf(dto) + 1);
                    d.setDescription(dto.getItemDescription());
                    d.setUnitCode(dto.getUnitCode());
                    d.setQuantity(dto.getQuantity());

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
        Canvass canvass =  canvassRepo.findOne(postData.getDocumentId());

        if (canvass != null) {
            DocumentWorkflowActionMap actionMap = workflowActionMapRepo.findOne(postData.getWorkflowActionsDto().getActionMapId());
            DocumentStatus afterActionDocumentStatus = actionMap.getAfterActionDocumentStatus();

            canvass.setDocumentStatus(afterActionDocumentStatus);
            canvass.setUpdatedAt(null);
            canvass = canvassRepo.save(canvass);

            if (canvass != null) {
                documentProcessingFacade.processAction(canvass.getTransaction(), actionMap, null, processedBy);

                response.setSuccessMessage("Document successfully processed");
                response.setSuccess(true);
            }

        }
        return response;
    }
}
