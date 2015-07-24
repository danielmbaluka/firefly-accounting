package com.tri.erp.spring.response;

import com.tri.erp.spring.model.DocumentStatus;
import com.tri.erp.spring.model.SlEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Personal on 4/24/2015.
 */
public class RvDto {
    private Integer id;
    private String purpose;
    private Date voucherDate;
    private Date deliveryDate;
    private String localCode;
    private SlEntity createdBy;
    private SlEntity recAppBy;
    private SlEntity checkedBy;
    private SlEntity auditedBy;
    private SlEntity canvassedBy;
    private SlEntity approvedBy;
    private SlEntity conformedBy;
    private String rvItType;
    private Integer transId;
    private DocumentStatus documentStatus;
    private Date lastUpdated;
    private Date created;
    private String rvType;
    private SlEntity employee;
    private Date durationStart;
    private Date durationEnd;

    public RvDto() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getVoucherDate() {
        return voucherDate;
    }

    public void setVoucherDate(Date voucherDate) {
        this.voucherDate = voucherDate;
    }

    public String getLocalCode() {
        return localCode;
    }

    public void setLocalCode(String localCode) {
        this.localCode = localCode;
    }

    public SlEntity getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(SlEntity createdBy) {
        this.createdBy = createdBy;
    }

    public SlEntity getRecAppBy() {
        return recAppBy;
    }

    public void setRecAppBy(SlEntity recAppBy) {
        this.recAppBy = recAppBy;
    }

    public SlEntity getCheckedBy() {
        return checkedBy;
    }

    public void setCheckedBy(SlEntity checkedBy) {
        this.checkedBy = checkedBy;
    }

    public SlEntity getAuditedBy() {
        return auditedBy;
    }

    public void setAuditedBy(SlEntity auditedBy) {
        this.auditedBy = auditedBy;
    }

    public SlEntity getCanvassedBy() {
        return canvassedBy;
    }

    public void setCanvassedBy(SlEntity canvassedBy) {
        this.canvassedBy = canvassedBy;
    }

    public SlEntity getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(SlEntity approvedBy) {
        this.approvedBy = approvedBy;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public SlEntity getConformedBy() {
        return conformedBy;
    }

    public void setConformedBy(SlEntity conformedBy) {
        this.conformedBy = conformedBy;
    }

    public String getRvItType() {
        return rvItType;
    }

    public void setRvItType(String rvItType) {
        this.rvItType = rvItType;
    }

    public Integer getTransId() {
        return transId;
    }

    public void setTransId(Integer transId) {
        this.transId = transId;
    }

    public DocumentStatus getDocumentStatus() {
        return documentStatus;
    }

    public void setDocumentStatus(DocumentStatus documentStatus) {
        this.documentStatus = documentStatus;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getRvType() {
        return rvType;
    }

    public void setRvType(String rvType) {
        this.rvType = rvType;
    }

    public SlEntity getEmployee() {
        return employee;
    }

    public void setEmployee(SlEntity employee) {
        this.employee = employee;
    }

    public Date getDurationStart() {
        return durationStart;
    }

    public void setDurationStart(Date durationStart) {
        this.durationStart = durationStart;
    }

    public Date getDurationEnd() {
        return durationEnd;
    }

    public void setDurationEnd(Date durationEnd) {
        this.durationEnd = durationEnd;
    }
}
