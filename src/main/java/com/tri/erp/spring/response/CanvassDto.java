package com.tri.erp.spring.response;

import com.tri.erp.spring.model.DocumentStatus;
import com.tri.erp.spring.model.SlEntity;

import java.util.Date;

/**
 * Created by Personal on 5/12/2015.
 */
public class CanvassDto {
    private Integer id;
    private SlEntity vendor;
    private Date voucherDate;
    private String localCode;
    private SlEntity createdBy;
    private SlEntity approvedBy;
    private Integer transId;
    private DocumentStatus documentStatus;
    private Date lastUpdated;
    private Date created;

    public CanvassDto() {}

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

    public SlEntity getVendor() {
        return vendor;
    }

    public void setVendor(SlEntity vendor) {
        this.vendor = vendor;
    }

    public SlEntity getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(SlEntity approvedBy) {
        this.approvedBy = approvedBy;
    }
}
