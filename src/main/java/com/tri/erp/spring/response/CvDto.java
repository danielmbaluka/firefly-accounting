package com.tri.erp.spring.response;

import com.tri.erp.spring.model.DocumentStatus;
import com.tri.erp.spring.model.SlEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by TSI Admin on 4/23/2015.
 */
public class CvDto {
    private Integer id;
    private SlEntity payee;
    private String particulars;
    private Date voucherDate;
    private Date dueDate;
    private String localCode;
    private SlEntity checker;
    private SlEntity approvingOfficer;
    private SlEntity recommendingOfficer;
    private SlEntity auditor;
    private Integer transId;
    private BigDecimal amount;
    private BigDecimal checkAmount;
    private DocumentStatus documentStatus;
    private Date lastUpdated;
    private Date created;
    private ApvDto apvDto; // one for now

    public CvDto() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public SlEntity getPayee() {
        return payee;
    }

    public void setPayee(SlEntity payee) {
        this.payee = payee;
    }

    public String getParticulars() {
        return particulars;
    }

    public void setParticulars(String particulars) {
        this.particulars = particulars;
    }

    public Date getVoucherDate() {
        return voucherDate;
    }

    public void setVoucherDate(Date voucherDate) {
        this.voucherDate = voucherDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public String getLocalCode() {
        return localCode;
    }

    public void setLocalCode(String localCode) {
        this.localCode = localCode;
    }

    public SlEntity getChecker() {
        return checker;
    }

    public void setChecker(SlEntity checker) {
        this.checker = checker;
    }

    public SlEntity getApprovingOfficer() {
        return approvingOfficer;
    }

    public void setApprovingOfficer(SlEntity approvingOfficer) {
        this.approvingOfficer = approvingOfficer;
    }

    public Integer getTransId() {
        return transId;
    }

    public void setTransId(Integer transId) {
        this.transId = transId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
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

    public SlEntity getRecommendingOfficer() {
        return recommendingOfficer;
    }

    public void setRecommendingOfficer(SlEntity recommendingOfficer) {
        this.recommendingOfficer = recommendingOfficer;
    }

    public SlEntity getAuditor() {
        return auditor;
    }

    public void setAuditor(SlEntity auditor) {
        this.auditor = auditor;
    }

    public BigDecimal getCheckAmount() {
        return checkAmount;
    }

    public void setCheckAmount(BigDecimal checkAmount) {
        this.checkAmount = checkAmount;
    }

    public ApvDto getApvDto() {
        return apvDto;
    }

    public void setApvDto(ApvDto apvDto) {
        this.apvDto = apvDto;
    }
}
