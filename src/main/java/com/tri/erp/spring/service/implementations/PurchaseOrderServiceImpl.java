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
import com.tri.erp.spring.response.reports.PODetail;
import com.tri.erp.spring.service.interfaces.PrintableVoucher;
import com.tri.erp.spring.service.interfaces.PurchaseOrderService;
import com.tri.erp.spring.validator.PoValidator;
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
 * Created by Personal on 5/15/2015.
 */
@Service(value = "poServiceImpl")
public class PurchaseOrderServiceImpl implements PurchaseOrderService, PrintableVoucher {

    @Autowired
    GeneratorFacade generatorFacade;

    @Autowired
    private AuthenticationFacade authenticationFacade;

    @Autowired
    PurchaseOrderRepo purchaseOrderRepo;

    @Autowired
    UserRepo userRepo;

    @Autowired
    SlEntityRepo slEntityRepo;

    @Autowired
    PoDetailRepo poDetailRepo;

    @Autowired
    RvDetailRepo rvDetailRepo;

    @Autowired
    DocumentWorkflowActionMapRepo workflowActionMapRepo;

    @Autowired
    DocumentProcessingFacade documentProcessingFacade;

    @Autowired
    PoDetailServiceImpl poDetailDto;

    @Override
    @Transactional(readOnly = true)
    public PurchaseOrder findByCode(String code) {
        List<PurchaseOrder> pos = purchaseOrderRepo.findByCode(code);

        if (!Checker.collectionIsEmpty(pos)) {
            return pos.get(0);
        } else return null;
    }

    @Override
    @Transactional
    public PostResponse processUpdate(Document v, BindingResult bindingResult, MessageSource messageSource) {
        PurchaseOrder purchaseOrder = (PurchaseOrder) v;
        return this.processCreate(purchaseOrder, bindingResult, messageSource);
    }

    @Override
    @Transactional
    public PostResponse processCreate(Document v, BindingResult bindingResult, MessageSource messageSource) {
        PurchaseOrder purchaseOrder = (PurchaseOrder) v;
        PostResponse response = new PostResponse();
        response.setSuccess(false);

        MessageFormatter messageFormatter = new MessageFormatter(bindingResult, messageSource, response);

        PoValidator validator = new PoValidator();
        validator.setService(this);
        validator.validate(purchaseOrder, bindingResult);

        if (bindingResult.hasErrors()) {
            messageFormatter.buildErrorMessages();
            response = messageFormatter.getResponse();
        } else {
            User createdBy = authenticationFacade.getLoggedIn();
            PurchaseOrder existingPo = null;

            Integer voucherYear = Integer.parseInt(GlobalConstant.YYYY_DATE_FORMAT.format(purchaseOrder.getVoucherDate()));

            User notedBy = userRepo.findOneByAccountNo(purchaseOrder.getNotedBy().getAccountNo());
            User approvedBy = userRepo.findOneByAccountNo(purchaseOrder.getApprovingOfficer().getAccountNo());

            Boolean insertMode = purchaseOrder.getId() == null;
            if (insertMode) { // insert mode
                Object latestCanvassCode = purchaseOrderRepo.findLatestPoCodeByYear(voucherYear);
                purchaseOrder.setCode(generatorFacade.voucherCode("PO", (latestCanvassCode == null ? "" : String.valueOf(latestCanvassCode)), purchaseOrder.getVoucherDate()));

                DocumentStatus documentStatus = new DocumentStatus();
                documentStatus.setId(com.tri.erp.spring.model.enums.DocumentStatus.DOCUMENT_CREATED.getId());
                purchaseOrder.setDocumentStatus(documentStatus);

                Workflow wf = new Workflow();
                wf.setId(1);

                purchaseOrder.setTransaction(generatorFacade.transaction());
                purchaseOrder.setWorkflow(wf);
                purchaseOrder.setCreatedBy(createdBy);
                existingPo = purchaseOrder;
            } else {
                existingPo = purchaseOrderRepo.findOne(purchaseOrder.getId());
            }
            existingPo.setVendor(purchaseOrder.getVendor());
            existingPo.setVoucherDate(purchaseOrder.getVoucherDate());
            existingPo.setYear(voucherYear);
            existingPo.setNotedBy(notedBy);
            existingPo.setApprovingOfficer(approvedBy);
            existingPo.setAmount(purchaseOrder.getAmount());

            PurchaseOrder newPo = purchaseOrderRepo.save(existingPo);

            if (newPo != null) {
                if (!insertMode) {
                    poDetailRepo.deleteByPurchaseOrderId(existingPo.getId());
                }

                if (insertMode) { // log action only when adding document
                    documentProcessingFacade.processAction(newPo.getTransaction(), null, newPo.getWorkflow(), createdBy);
                }

                ArrayList<PoDetailDto> poDetails = purchaseOrder.getPoDetails();
                for(PoDetailDto poDetailLine: poDetails) {

                    PoDetail poDetail = new PoDetail();

                    PurchaseOrder po1 = new PurchaseOrder();
                    po1.setId(newPo.getId());
                    poDetail.setPurchaseOrder(po1);

                    RvDetail rvDetail = new RvDetail();
                    rvDetail.setId(poDetailLine.getRvDetailId());
                    rvDetail.setPoQuantity(poDetailLine.getQuantity());
                    poDetail.setRvDetail(rvDetail);

                    poDetail.setQuantity(poDetailLine.getQuantity());
                    poDetail.setUnitPrice(poDetailLine.getUnitPrice());
                    poDetail.setVat(poDetailLine.getVat());
                    poDetail.setDiscount(poDetailLine.getDiscount());
                    poDetail.setAmount(poDetailLine.getItemAmount());

                    PoDetail newPod = poDetailRepo.save(poDetail);
                    if(newPod != null){
                        rvDetailRepo.updatePoQuantityById(rvDetail.getId(), rvDetail.getPoQuantity());
                    }
                }

                response.setModelId(newPo.getId());
                response.setSuccessMessage("Purchase Oreder successfully saved!");
                response.setSuccess(true);
            }
        }

        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public List<PoListDto> findAll() {
        List<PurchaseOrder> vouchers = purchaseOrderRepo.findAll();

        List<PoListDto> returnVouchers = new ArrayList<>();
        if (!Checker.collectionIsEmpty(vouchers)) {
            for(PurchaseOrder po : vouchers) {
                PoListDto poListDto = new PoListDto();
                poListDto.setId(po.getId());
                poListDto.setVoucherDate(po.getVoucherDate());
                poListDto.setLocalCode(po.getCode());
                poListDto.setSupplier(po.getVendor().getName());
                poListDto.setAmount(po.getAmount());
                poListDto.setStatus(po.getDocumentStatus().getStatus());

                SlEntity createdBy = slEntityRepo.findOne(po.getCreatedBy().getAccountNo());
                poListDto.setPreparedBy(createdBy.getName());

                returnVouchers.add(poListDto);
            }

            return returnVouchers;
        }
        return null;
    }

    @Override
    public PoDto findById(Integer id) {
        PurchaseOrder po =  purchaseOrderRepo.findOne(id);
        PoDto poDto = new PoDto();

        if (po != null) {
            poDto.setId(po.getId());
            poDto.setLocalCode(po.getCode());
            poDto.setTransId(po.getTransaction().getId());
            poDto.setAmount(po.getAmount());

            SlEntity createdBy = slEntityRepo.findOne(po.getCreatedBy().getAccountNo());
            SlEntity notedBy = slEntityRepo.findOne(po.getNotedBy().getAccountNo());
            SlEntity approvedBy = slEntityRepo.findOne(po.getApprovingOfficer().getAccountNo());

            poDto.setCreatedBy(createdBy);
            poDto.setNotedBy(notedBy);
            poDto.setApprovedBy(approvedBy);
            poDto.setTerm(po.getTerm());
            poDto.setVendor(po.getVendor());
            poDto.setVoucherDate(po.getVoucherDate());
            poDto.setDocumentStatus(po.getDocumentStatus());
            poDto.setCreated(po.getCreatedAt());
            poDto.setLastUpdated(po.getUpdatedAt());
        }

        return  poDto;
    }

    @Override
    public HashMap reportParameters(Integer id, HttpServletRequest request) {
        HashMap<String, Object> params = ReportUtil.setupSharedReportHeaders(request);

        PurchaseOrder purchaseOrder = purchaseOrderRepo.findOne(id);

        if (purchaseOrder != null) {
            params.put("VOUCHER_NO", purchaseOrder.getCode());
            params.put("V_DATE", purchaseOrder.getVoucherDate());
            params.put("APPROVEDBY", purchaseOrder.getApprovingOfficer().getFullName());
            params.put("NOTEDBY", purchaseOrder.getNotedBy().getFullName());
            params.put("PREPAREDBY", purchaseOrder.getCreatedBy().getFullName());
            params.put("SUPPLIER", purchaseOrder.getVendor().getName());
            params.put("APPROVEDBY_POS", "");
            params.put("NOTEDBY_POS", "");
            params.put("PREPAREDBY_POS", "");
            params.put("PURPOSE", "");
            params.put("AMOUNT", purchaseOrder.getAmount());
        }

        return params;
    }

    @Override
    public JRDataSource datasource(Integer id) {
        List<PODetail> details = new ArrayList<>();

        PurchaseOrder voucher = purchaseOrderRepo.findOne(id);
        if (voucher != null) {
            List<PoDetailDto> poDetailLineDtos = poDetailDto.getPoDetails(id);

            if (!Checker.collectionIsEmpty(poDetailLineDtos)) {
                for(PoDetailDto dto : poDetailLineDtos) {
                    PODetail d = new PODetail();

                    d.setId(poDetailLineDtos.indexOf(dto) + 1);
                    d.setDescription(dto.getItemDescription());
                    d.setUnitCode(dto.getUnitCode());
                    d.setQuantity(dto.getQuantity());
                    d.setUnitPrice(dto.getUnitPrice());
                    d.setAmount(dto.getItemAmount());

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
        PurchaseOrder purchaseOrder =  purchaseOrderRepo.findOne(postData.getDocumentId());

        if (purchaseOrder != null) {
            DocumentWorkflowActionMap actionMap = workflowActionMapRepo.findOne(postData.getWorkflowActionsDto().getActionMapId());
            DocumentStatus afterActionDocumentStatus = actionMap.getAfterActionDocumentStatus();

            purchaseOrder.setDocumentStatus(afterActionDocumentStatus);
            purchaseOrder.setUpdatedAt(null);
            purchaseOrder = purchaseOrderRepo.save(purchaseOrder);

            if (purchaseOrder != null) {
                documentProcessingFacade.processAction(purchaseOrder.getTransaction(), actionMap, null, processedBy);

                response.setSuccessMessage("Document successfully processed");
                response.setSuccess(true);
            }

        }
        return response;
    }
}
