package com.tri.erp.spring.response;

import com.tri.erp.spring.model.DocumentStatus;
import com.tri.erp.spring.model.SlEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Personal on 5/15/2015.
 */
public class PoDto {
    private Integer id;
    private SlEntity vendor;
    private BigDecimal amount = BigDecimal.ZERO;
    private Date voucherDate;
    private String localCode;
    private Integer term;
    private SlEntity createdBy;
    private SlEntity notedBy;
    private SlEntity approvedBy;
    private Integer transId;
    private DocumentStatus documentStatus;
    private Date lastUpdated;
    private Date created;

    public PoDto() {}

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

    public Integer getTerm() {
        return term;
    }

    public void setTerm(Integer term) {
        this.term = term;
    }

    public SlEntity getNotedBy() {
        return notedBy;
    }

    public void setNotedBy(SlEntity notedBy) {
        this.notedBy = notedBy;
    }

    public SlEntity getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(SlEntity approvedBy) {
        this.approvedBy = approvedBy;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
