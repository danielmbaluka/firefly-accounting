package com.tri.erp.spring.service.implementations;

import com.tri.erp.spring.commons.facade.AuthenticationFacade;
import com.tri.erp.spring.commons.facade.DocumentProcessingFacade;
import com.tri.erp.spring.commons.facade.GeneratorFacade;
import com.tri.erp.spring.commons.helpers.Checker;
import com.tri.erp.spring.commons.helpers.MessageFormatter;
import com.tri.erp.spring.commons.helpers.ReportUtil;
import com.tri.erp.spring.model.*;
import com.tri.erp.spring.repo.DocumentWorkflowActionMapRepo;
import com.tri.erp.spring.repo.PrepaymentRepo;
import com.tri.erp.spring.repo.SlEntityRepo;
import com.tri.erp.spring.repo.UserRepo;
import com.tri.erp.spring.response.PostResponse;
import com.tri.erp.spring.response.PrepaymentDto;
import com.tri.erp.spring.response.PrepaymentListDto;
import com.tri.erp.spring.response.ProcessDocumentDto;
import com.tri.erp.spring.service.interfaces.PrepaymentService;
import com.tri.erp.spring.service.interfaces.PrintableVoucher;
import com.tri.erp.spring.validator.PrepaymentValidator;
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
 * Created by Personal on 6/3/2015.
 */
@Service(value = "ppServiceImpl")
public class PrepaymentServiceImpl implements PrepaymentService, PrintableVoucher {

    @Autowired
    GeneratorFacade generatorFacade;

    @Autowired
    private AuthenticationFacade authenticationFacade;

    @Autowired
    PrepaymentRepo prepaymentRepo;

    @Autowired
    UserRepo userRepo;

    @Autowired
    SlEntityRepo slEntityRepo;

    @Autowired
    DocumentWorkflowActionMapRepo workflowActionMapRepo;

    @Autowired
    DocumentProcessingFacade documentProcessingFacade;

    @Override
    @Transactional(readOnly = true)
    public Prepayment findByDescription(String description) {
        List<Prepayment> prepayments = prepaymentRepo.findByDescription(description);

        if (!Checker.collectionIsEmpty(prepayments)) {
            return prepayments.get(0);
        } else return null;
    }

    @Override
    @Transactional
    public PostResponse processUpdate(Document v, BindingResult bindingResult, MessageSource messageSource) {
        Prepayment pp = (Prepayment) v;
        return this.processCreate(pp, bindingResult, messageSource);
    }

    @Override
    @Transactional
    public PostResponse processCreate(Document v, BindingResult bindingResult, MessageSource messageSource) {
        Prepayment pp = (Prepayment) v;
        PostResponse response = new PostResponse();
        response.setSuccess(false);

        MessageFormatter messageFormatter = new MessageFormatter(bindingResult, messageSource, response);

        PrepaymentValidator validator = new PrepaymentValidator();
        validator.setService(this);
        validator.validate(pp, bindingResult);

        if (bindingResult.hasErrors()) {
            messageFormatter.buildErrorMessages();
            response = messageFormatter.getResponse();
        } else {
            User createdBy = authenticationFacade.getLoggedIn();
            Prepayment existingPp = null;

            Boolean insertMode = pp.getId() == null;
            if (insertMode) { // insert mode
                DocumentStatus documentStatus = new DocumentStatus();
                documentStatus.setId(com.tri.erp.spring.model.enums.DocumentStatus.DOCUMENT_CREATED.getId());
                pp.setDocumentStatus(documentStatus);

                Workflow wf = new Workflow();
                wf.setId(com.tri.erp.spring.model.enums.Workflow.RV.getId());

                pp.setCode("pp");
                pp.setTransaction(generatorFacade.transaction());
                pp.setAccountNo(generatorFacade.entityAccountNumber());
                pp.setWorkflow(wf);
                pp.setCreatedBy(createdBy);
                pp.setApprovingOfficer(createdBy);
                existingPp = pp;
            } else {
                existingPp = prepaymentRepo.findOne(pp.getId());
            }
            existingPp.setDatePaid(pp.getDatePaid());
            existingPp.setDescription(pp.getDescription());
            existingPp.setPrepaymentAccount(pp.getPrepaymentAccount());
            existingPp.setExpenseAccount(pp.getExpenseAccount());
            existingPp.setTotalCost(pp.getTotalCost());
            existingPp.setNoOfMonths(pp.getNoOfMonths());
            existingPp.setMonthlyCost(pp.getMonthlyCost());
            existingPp.setAppliedCost(pp.getAppliedCost());
            existingPp.setBalance(pp.getBalance());

            Prepayment newPp = prepaymentRepo.save(existingPp);

            if (newPp != null) {
//                if (!insertMode) {
//                    rvDetailRepo.deleteByRequisitionVoucherId(existingPp.getId());
//                }

                if (insertMode) { // log action only when adding document
                    documentProcessingFacade.processAction(newPp.getTransaction(), null, newPp.getWorkflow(), createdBy);
                }

//                ArrayList<RvDetailDto> rvDetails = pp.getRvDetails();
//                for(RvDetailDto rvDetailLine: rvDetails) {
//
//                    RvDetail rvDetail = new RvDetail();
//                    rvDetail.setQuantity(rvDetailLine.getQuantity());
//                    rvDetail.setPoQuantity(BigDecimal.ZERO);
//                    rvDetail.setJoDescription(rvDetailLine.getJoDescription());
//
//                    RequisitionVoucher requisitionVoucher = new RequisitionVoucher();
//                    requisitionVoucher.setId(newPp.getId());
//                    rvDetail.setRequisitionVoucher(requisitionVoucher);
//
//                    if (rvDetailLine.getItemId() != 0){
//                        Item item = new Item();
//                        item.setId(rvDetailLine.getItemId());
//                        rvDetail.setItem(item);
//                    }
//
//                    UnitMeasure unit = new UnitMeasure();
//                    unit.setId(rvDetailLine.getUnitId());
//                    rvDetail.setUnitMeasure(unit);
//
//                    rvDetailRepo.save(rvDetail);
//                }

                response.setModelId(newPp.getId());
                response.setSuccessMessage("RV successfully saved!");
                response.setSuccess(true);
            }
        }

        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public List<PrepaymentListDto> findAll() {
        List<Prepayment> vouchers = prepaymentRepo.findAll();

        List<PrepaymentListDto> returnVouchers = new ArrayList<>();
        if (!Checker.collectionIsEmpty(vouchers)) {
            for(Prepayment pp : vouchers) {
                PrepaymentListDto rvListDto = new PrepaymentListDto();
                rvListDto.setId(pp.getId());
                rvListDto.setDatePaid(pp.getDatePaid());
                rvListDto.setDescription(pp.getDescription());
                rvListDto.setTotalCost(pp.getTotalCost());
                rvListDto.setMonthlyCost(pp.getMonthlyCost());
                rvListDto.setAppliedCost(pp.getAppliedCost());
                rvListDto.setBalance(pp.getBalance());
                rvListDto.setNoOfMonths(pp.getNoOfMonths());

                returnVouchers.add(rvListDto);
            }

            return returnVouchers;
        }
        return null;
    }

    @Override
    public PrepaymentDto findById(Integer id) {
        Prepayment pp =  prepaymentRepo.findOne(id);
        PrepaymentDto ppDto = new PrepaymentDto();

        if (pp != null) {
            ppDto.setId(pp.getId());
            ppDto.setDescription(pp.getDescription());
            ppDto.setTransId(pp.getTransaction().getId());

            SlEntity createdBy = slEntityRepo.findOne(pp.getCreatedBy().getAccountNo());
            SlEntity approvedBy = slEntityRepo.findOne(pp.getApprovingOfficer().getAccountNo());

            ppDto.setCreatedBy(createdBy);
            ppDto.setApprovedBy(approvedBy);
            ppDto.setDatePaid(pp.getDatePaid());
            ppDto.setDocumentStatus(pp.getDocumentStatus());
            ppDto.setCreated(pp.getCreatedAt());
            ppDto.setLastUpdated(pp.getUpdatedAt());
            ppDto.setPrepaymentAccount(pp.getPrepaymentAccount());
            ppDto.setExpenseAccount(pp.getExpenseAccount());
            ppDto.setTotalCost(pp.getTotalCost());
            ppDto.setNoOfMonths(pp.getNoOfMonths());
            ppDto.setMonthlyCost(pp.getMonthlyCost());
            ppDto.setAppliedCost(pp.getAppliedCost());
            ppDto.setBalance(pp.getBalance());
        }

        return  ppDto;
    }

    @Override
    public HashMap reportParameters(Integer id, HttpServletRequest request) {
        HashMap<String, Object> params = ReportUtil.setupSharedReportHeaders(request);

        Prepayment pp = prepaymentRepo.findOne(id);

        if (pp != null) {
//            params.put("VOUCHER_NO", pp.getCode());
//            params.put("V_DATE", pp.getVoucherDate());
//            params.put("APPROVEDBY", pp.getApprovingOfficer().getFullName());
//            params.put("AUDITBY", pp.getAuditedBy().getFullName());
//            params.put("RECAPPBY", pp.getRecAppBy().getFullName());
//            params.put("REQUESTEDBY", pp.getCreatedBy().getFullName());
//            params.put("CONFORMBY", pp.getConformedBy() == null ? "" : pp.getConformedBy().getFullName());
//            params.put("CHECKBY", pp.getCheckedBy() == null ? "" : pp.getCheckedBy().getFullName());
//            params.put("NAME", pp.getCreatedBy().getFullName());
//            params.put("POSITION", "");
//            params.put("DEPARTMENT", "");
//            params.put("PURPOSE", pp.getPurpose());
//            params.put("REMARKS", "");
//            params.put("APPROVEDBY_POS", "");
//            params.put("AUDITBY_POS", "");
//            params.put("REQUESTEDBY_POS", "");
//            params.put("RECAPPBY_POS", "");
//            params.put("CONFORMBY_POS", "");
//            params.put("CHECKBY_POS", "");
//            params.put("EMPLOYEE", pp.getEmployee() == null ? "" : pp.getEmployee().getName());
//            params.put("DURATION_S", pp.getDurationStart() == null ? "" : pp.getDurationStart());
//            params.put("DURATION_E", pp.getDurationEnd() == null ? "" : pp.getDurationEnd());
//            params.put("IT_TYPE", pp.getRvItType() == null  ? "" : pp.getRvItType());
        }

        return params;
    }

    @Override
    public JRDataSource datasource(Integer id) {
//        List<RVDetail> details = new ArrayList<>();
//
//        RequisitionVoucher voucher = prepaymentRepo.findOne(id);
//        if (voucher != null) {
//            List<RvDetailDto> rvDetailLineDtos = rvDetailDto.getRvDetails(id);
//
//            if (!Checker.collectionIsEmpty(rvDetailLineDtos)) {
//                for(RvDetailDto dto : rvDetailLineDtos) {
//                    RVDetail d = new RVDetail();
//
//                    d.setId(rvDetailLineDtos.indexOf(dto) + 1);
//                    d.setDescription(dto.getJoDescription() == null ? dto.getItemDescription() : dto.getJoDescription());
//                    d.setUnitCode(dto.getUnitCode());
//                    d.setQuantity(dto.getQuantity());
//
//                    details.add(d);
//                }
//            }
//        }
//        return new JRBeanCollectionDataSource(details);
        return null;
    }

    @Override
    public PostResponse process(ProcessDocumentDto postData, BindingResult bindingResult, MessageSource messageSource) {
        PostResponse response = new PostResponse();

        User processedBy = authenticationFacade.getLoggedIn();
        Prepayment pp =  prepaymentRepo.findOne(postData.getDocumentId());

        if (pp != null) {
            DocumentWorkflowActionMap actionMap = workflowActionMapRepo.findOne(postData.getWorkflowActionsDto().getActionMapId());
            DocumentStatus afterActionDocumentStatus = actionMap.getAfterActionDocumentStatus();

            pp.setDocumentStatus(afterActionDocumentStatus);
            pp.setUpdatedAt(null);
            pp = prepaymentRepo.save(pp);

            if (pp != null) {
                documentProcessingFacade.processAction(pp.getTransaction(), actionMap, null, processedBy);

                response.setSuccessMessage("Document successfully processed");
                response.setSuccess(true);
            }

        }
        return response;
    }
}