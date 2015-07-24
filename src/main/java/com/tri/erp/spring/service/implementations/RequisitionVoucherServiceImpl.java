package com.tri.erp.spring.service.implementations;

import com.tri.erp.spring.commons.GlobalConstant;
import com.tri.erp.spring.commons.facade.AuthenticationFacade;
import com.tri.erp.spring.commons.facade.DocumentProcessingFacade;
import com.tri.erp.spring.commons.facade.GeneratorFacade;
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
import com.tri.erp.spring.response.reports.RVDetail;
import com.tri.erp.spring.service.interfaces.PrintableVoucher;
import com.tri.erp.spring.service.interfaces.RequisitionVoucherService;
import com.tri.erp.spring.validator.RvValidator;
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
 * Created by Personal on 3/20/2015.
 */
@Service(value = "rvServiceImpl")
public class RequisitionVoucherServiceImpl implements RequisitionVoucherService, PrintableVoucher {

    @Autowired
    GeneratorFacade generatorFacade;

    @Autowired
    private AuthenticationFacade authenticationFacade;

    @Autowired
    RequisitionVoucherRepo requisitionVoucherRepo;

    @Autowired
    UserRepo userRepo;

    @Autowired
    SlEntityRepo slEntityRepo;

    @Autowired
    RvDetailRepo rvDetailRepo;

    @Autowired
    DocumentWorkflowActionMapRepo workflowActionMapRepo;

    @Autowired
    DocumentProcessingFacade documentProcessingFacade;

    @Autowired
    RvDetailServiceImpl rvDetailDto;

    @Override
    @Transactional(readOnly = true)
    public RequisitionVoucher findByCode(String code) {
        List<RequisitionVoucher> requisitionVouchers = requisitionVoucherRepo.findByCode(code);

        if (!Checker.collectionIsEmpty(requisitionVouchers)) {
            return requisitionVouchers.get(0);
        } else return null;
    }

    @Override
    @Transactional
    public PostResponse processUpdate(Document v, BindingResult bindingResult, MessageSource messageSource) {
        RequisitionVoucher rv = (RequisitionVoucher) v;
        return this.processCreate(rv, bindingResult, messageSource);
    }

    @Override
    @Transactional
    public PostResponse processCreate(Document v, BindingResult bindingResult, MessageSource messageSource) {
        RequisitionVoucher rv = (RequisitionVoucher) v;
        PostResponse response = new PostResponse();
        response.setSuccess(false);

        MessageFormatter messageFormatter = new MessageFormatter(bindingResult, messageSource, response);

        RvValidator validator = new RvValidator();
        validator.setService(this);
        validator.validate(rv, bindingResult);

        if (bindingResult.hasErrors()) {
            messageFormatter.buildErrorMessages();
            response = messageFormatter.getResponse();
        } else {
            User createdBy = authenticationFacade.getLoggedIn();
            RequisitionVoucher existingRv = null;

            Integer voucherYear = Integer.parseInt(GlobalConstant.YYYY_DATE_FORMAT.format(rv.getVoucherDate()));

            User recAppBy = userRepo.findOneByAccountNo(rv.getRecAppBy().getAccountNo());
            User checkedBy = userRepo.findOneByAccountNo(rv.getCheckedBy().getAccountNo());
            User auditedBy = userRepo.findOneByAccountNo(rv.getAuditedBy().getAccountNo());
            User approvedBy = userRepo.findOneByAccountNo(rv.getApprovingOfficer().getAccountNo());
            Boolean insertMode = rv.getId() == null;
            if (insertMode) { // insert mode
                Object latestApvCode = requisitionVoucherRepo.findLatestRvCodeByYear(voucherYear);
                rv.setCode(generatorFacade.voucherCode("RV", (latestApvCode == null ? "" : String.valueOf(latestApvCode)), rv.getVoucherDate()));

                DocumentStatus documentStatus = new DocumentStatus();
                documentStatus.setId(com.tri.erp.spring.model.enums.DocumentStatus.DOCUMENT_CREATED.getId());
                rv.setDocumentStatus(documentStatus);

                Workflow wf = new Workflow();
                wf.setId(com.tri.erp.spring.model.enums.Workflow.RV.getId());

                rv.setTransaction(generatorFacade.transaction());
                rv.setWorkflow(wf);
                rv.setCreatedBy(createdBy);
                existingRv = rv;
            } else {
                existingRv = requisitionVoucherRepo.findOne(rv.getId());
            }
            if(rv.getRvType() == RvType.FOR_IT.getId()){
                User conformedBy = userRepo.findOneByAccountNo(rv.getConformedBy().getAccountNo());
                rv.setConformedBy(conformedBy);
            }
            if(rv.getRvType() == RvType.FOR_LAB.getId()){
                existingRv.setEmployee(rv.getEmployee());
                existingRv.setDurationStart(rv.getDurationStart());
                existingRv.setDurationEnd(rv.getDurationEnd());
            }
            if(rv.getDocumentStatus().getId() == com.tri.erp.spring.model.enums.DocumentStatus.FOR_CANVASSING.getId()) {
                User canvassedBy = userRepo.findOneByAccountNo(rv.getCanvassedBy().getAccountNo());
                existingRv.setCanvassedBy(canvassedBy);
            }
            existingRv.setVoucherDate(rv.getVoucherDate());
            existingRv.setDeliveryDate(rv.getDeliveryDate());
            existingRv.setPurpose(rv.getPurpose());
            existingRv.setRecAppBy(recAppBy);
            existingRv.setCheckedBy(checkedBy);
            existingRv.setAuditedBy(auditedBy);
            existingRv.setApprovingOfficer(approvedBy);
            existingRv.setYear(voucherYear);
            existingRv.setRvItType(rv.getRvItType());

            RequisitionVoucher newRv = requisitionVoucherRepo.save(existingRv);

            if (newRv != null) {
                if (!insertMode) {
                    rvDetailRepo.deleteByRequisitionVoucherId(existingRv.getId());
                }

                if (insertMode) { // log action only when adding document
                    documentProcessingFacade.processAction(newRv.getTransaction(), null, newRv.getWorkflow(), createdBy);
                }

                ArrayList<RvDetailDto> rvDetails = rv.getRvDetails();
                for(RvDetailDto rvDetailLine: rvDetails) {

                    RvDetail rvDetail = new RvDetail();
                    rvDetail.setQuantity(rvDetailLine.getQuantity());
                    rvDetail.setPoQuantity(BigDecimal.ZERO);
                    rvDetail.setJoDescription(rvDetailLine.getJoDescription());

                    RequisitionVoucher requisitionVoucher = new RequisitionVoucher();
                    requisitionVoucher.setId(newRv.getId());
                    rvDetail.setRequisitionVoucher(requisitionVoucher);

                    if (rvDetailLine.getItemId() != 0){
                        Item item = new Item();
                        item.setId(rvDetailLine.getItemId());
                        rvDetail.setItem(item);
                    }

                    UnitMeasure unit = new UnitMeasure();
                    unit.setId(rvDetailLine.getUnitId());
                    rvDetail.setUnitMeasure(unit);

                    rvDetailRepo.save(rvDetail);
                }

                response.setModelId(newRv.getId());
                response.setSuccessMessage("RV successfully saved!");
                response.setSuccess(true);
            }
        }

        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public List<RvListDto> findAll() {
        List<RequisitionVoucher> vouchers = requisitionVoucherRepo.findAll();

        List<RvListDto> returnVouchers = new ArrayList<>();
        if (!Checker.collectionIsEmpty(vouchers)) {
            for(RequisitionVoucher rv : vouchers) {
                RvListDto rvListDto = new RvListDto();
                rvListDto.setId(rv.getId());
                rvListDto.setVoucherDate(rv.getVoucherDate());
                rvListDto.setDeliveryDate(rv.getDeliveryDate());
                rvListDto.setLocalCode(rv.getCode());
                rvListDto.setPurpose(rv.getPurpose());
                rvListDto.setStatus(rv.getDocumentStatus().getStatus());
                if (rv.getRvType() == RvType.FOR_PO.getId()) {
                    rvListDto.setRvType(RvType.FOR_PO.getDescription());
                } else if (rv.getRvType() == RvType.FOR_IT.getId()){
                    rvListDto.setRvType(RvType.FOR_IT.getDescription());
                } else if (rv.getRvType() == RvType.FOR_REP.getId()){
                    rvListDto.setRvType(RvType.FOR_REP.getDescription());
                } else if (rv.getRvType() == RvType.FOR_LAB.getId()){
                    rvListDto.setRvType(RvType.FOR_LAB.getDescription());
                }
                rvListDto.setRvTypeId(rv.getRvType());

                SlEntity createdBy = slEntityRepo.findOne(rv.getCreatedBy().getAccountNo());
                rvListDto.setPreparedBy(createdBy.getName());

                returnVouchers.add(rvListDto);
            }

            return returnVouchers;
        }
        return null;
    }

    @Override
    public RvDto findByRvId(Integer id) {
        RequisitionVoucher requisitionVoucher =  requisitionVoucherRepo.findOne(id);
        RvDto rvDto = new RvDto();

        if (requisitionVoucher != null) {
            rvDto.setId(requisitionVoucher.getId());
            rvDto.setPurpose(requisitionVoucher.getPurpose());
            rvDto.setLocalCode(requisitionVoucher.getCode());
            rvDto.setTransId(requisitionVoucher.getTransaction().getId());

            SlEntity createdBy = slEntityRepo.findOne(requisitionVoucher.getCreatedBy().getAccountNo());
            SlEntity recAppBy = slEntityRepo.findOne(requisitionVoucher.getRecAppBy().getAccountNo());
            SlEntity checkedBy = slEntityRepo.findOne(requisitionVoucher.getCheckedBy().getAccountNo());
            SlEntity auditedBy= slEntityRepo.findOne(requisitionVoucher.getAuditedBy().getAccountNo());
            SlEntity approvedBy = slEntityRepo.findOne(requisitionVoucher.getApprovingOfficer().getAccountNo());
            if(requisitionVoucher.getDocumentStatus().getId() == com.tri.erp.spring.model.enums.DocumentStatus.FOR_CANVASSING.getId()) {
                SlEntity canvassedBy = slEntityRepo.findOne(requisitionVoucher.getCanvassedBy().getAccountNo());
                rvDto.setCanvassedBy(canvassedBy);
            }
            if(requisitionVoucher.getRvType() == RvType.FOR_IT.getId()) {
                SlEntity conformedBy = slEntityRepo.findOne(requisitionVoucher.getConformedBy().getAccountNo());
                rvDto.setConformedBy(conformedBy);
                rvDto.setRvItType(requisitionVoucher.getRvItType());
            }
            if (requisitionVoucher.getRvType() == RvType.FOR_PO.getId()) {
                rvDto.setRvType(RvType.FOR_PO.getDescription());
            } else if (requisitionVoucher.getRvType() == RvType.FOR_IT.getId()){
                rvDto.setRvType(RvType.FOR_IT.getDescription());
            } else if (requisitionVoucher.getRvType() == RvType.FOR_REP.getId()){
                rvDto.setRvType(RvType.FOR_REP.getDescription());
            } else if (requisitionVoucher.getRvType() == RvType.FOR_LAB.getId()){
                rvDto.setRvType(RvType.FOR_LAB.getDescription());
                SlEntity employee = slEntityRepo.findOne(requisitionVoucher.getEmployee().getAccountNo());
                rvDto.setEmployee(employee);
                rvDto.setDurationStart(requisitionVoucher.getDurationStart());
                rvDto.setDurationEnd(requisitionVoucher.getDurationEnd());
            }

            rvDto.setCreatedBy(createdBy);
            rvDto.setRecAppBy(recAppBy);
            rvDto.setCheckedBy(checkedBy);
            rvDto.setAuditedBy(auditedBy);
            rvDto.setApprovedBy(approvedBy);
            rvDto.setDeliveryDate(requisitionVoucher.getDeliveryDate());
            rvDto.setVoucherDate(requisitionVoucher.getVoucherDate());
            rvDto.setDocumentStatus(requisitionVoucher.getDocumentStatus());
            rvDto.setCreated(requisitionVoucher.getCreatedAt());
            rvDto.setLastUpdated(requisitionVoucher.getUpdatedAt());
        }

        return  rvDto;
    }

    @Override
    public HashMap reportParameters(Integer id, HttpServletRequest request) {
        HashMap<String, Object> params = ReportUtil.setupSharedReportHeaders(request);

        RequisitionVoucher requisitionVoucher = requisitionVoucherRepo.findOne(id);

        if (requisitionVoucher != null) {
            params.put("VOUCHER_NO", requisitionVoucher.getCode());
            params.put("V_DATE", requisitionVoucher.getVoucherDate());
            params.put("APPROVEDBY", requisitionVoucher.getApprovingOfficer().getFullName());
            params.put("AUDITBY", requisitionVoucher.getAuditedBy().getFullName());
            params.put("RECAPPBY", requisitionVoucher.getRecAppBy().getFullName());
            params.put("REQUESTEDBY", requisitionVoucher.getCreatedBy().getFullName());
            params.put("CONFORMBY", requisitionVoucher.getConformedBy() == null ? "" : requisitionVoucher.getConformedBy().getFullName());
            params.put("CHECKBY", requisitionVoucher.getCheckedBy() == null ? "" : requisitionVoucher.getCheckedBy().getFullName());
            params.put("NAME", requisitionVoucher.getCreatedBy().getFullName());
            params.put("POSITION", "");
            params.put("DEPARTMENT", "");
            params.put("PURPOSE", requisitionVoucher.getPurpose());
            params.put("REMARKS", "");
            params.put("APPROVEDBY_POS", "");
            params.put("AUDITBY_POS", "");
            params.put("REQUESTEDBY_POS", "");
            params.put("RECAPPBY_POS", "");
            params.put("CONFORMBY_POS", "");
            params.put("CHECKBY_POS", "");
            params.put("EMPLOYEE", requisitionVoucher.getEmployee() == null ? "" : requisitionVoucher.getEmployee().getName());
            params.put("DURATION_S", requisitionVoucher.getDurationStart() == null ? "" : requisitionVoucher.getDurationStart());
            params.put("DURATION_E", requisitionVoucher.getDurationEnd() == null ? "" : requisitionVoucher.getDurationEnd());
            params.put("IT_TYPE", requisitionVoucher.getRvItType() == null  ? "" : requisitionVoucher.getRvItType());
        }

        return params;
    }

    @Override
    public JRDataSource datasource(Integer id) {
        List<RVDetail> details = new ArrayList<>();

        RequisitionVoucher voucher = requisitionVoucherRepo.findOne(id);
        if (voucher != null) {
            List<RvDetailDto> rvDetailLineDtos = rvDetailDto.getRvDetails(id);

            if (!Checker.collectionIsEmpty(rvDetailLineDtos)) {
                for(RvDetailDto dto : rvDetailLineDtos) {
                    RVDetail d = new RVDetail();

                    d.setId(rvDetailLineDtos.indexOf(dto) + 1);
                    d.setDescription(dto.getJoDescription() == null ? dto.getItemDescription() : dto.getJoDescription());
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
        RequisitionVoucher requisitionVoucher =  requisitionVoucherRepo.findOne(postData.getDocumentId());

        if (requisitionVoucher != null) {
            DocumentWorkflowActionMap actionMap = workflowActionMapRepo.findOne(postData.getWorkflowActionsDto().getActionMapId());
            DocumentStatus afterActionDocumentStatus = actionMap.getAfterActionDocumentStatus();

            requisitionVoucher.setDocumentStatus(afterActionDocumentStatus);
            requisitionVoucher.setUpdatedAt(null);
            requisitionVoucher = requisitionVoucherRepo.save(requisitionVoucher);

            if (requisitionVoucher != null) {
                documentProcessingFacade.processAction(requisitionVoucher.getTransaction(), actionMap, null, processedBy);

                response.setSuccessMessage("Document successfully processed");
                response.setSuccess(true);
            }

        }
        return response;
    }
}