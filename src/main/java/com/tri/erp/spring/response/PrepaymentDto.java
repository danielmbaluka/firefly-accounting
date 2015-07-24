package com.tri.erp.spring.response;

import com.tri.erp.spring.model.DocumentStatus;
import com.tri.erp.spring.model.SegmentAccount;
import com.tri.erp.spring.model.SlEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Personal on 6/3/2015.
 */
public class PrepaymentDto {
    private Integer id;
    private Date datePaid;
    private Integer accountNo;
    private String description;
    private SlEntity createdBy;
    private SlEntity approvedBy;
    private SegmentAccount prepaymentAccount;
    private SegmentAccount expenseAccount;
    private BigDecimal totalCost = BigDecimal.ZERO;
    private Integer noOfMonths;
    private BigDecimal monthlyCost = BigDecimal.ZERO;
    private BigDecimal appliedCost = BigDecimal.ZERO;
    private BigDecimal balance = BigDecimal.ZERO;
    private Integer transId;
    private DocumentStatus documentStatus;
    private Date lastUpdated;
    private Date created;

    public PrepaymentDto() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDatePaid() {
        return datePaid;
    }

    public void setDatePaid(Date datePaid) {
        this.datePaid = datePaid;
    }

    public Integer getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(Integer accountNo) {
        this.accountNo = accountNo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public SlEntity getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(SlEntity createdBy) {
        this.createdBy = createdBy;
    }

    public SlEntity getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(SlEntity approvedBy) {
        this.approvedBy = approvedBy;
    }

    public SegmentAccount getPrepaymentAccount() {
        return prepaymentAccount;
    }

    public void setPrepaymentAccount(SegmentAccount prepaymentAccount) {
        this.prepaymentAccount = prepaymentAccount;
    }

    public SegmentAccount getExpenseAccount() {
        return expenseAccount;
    }

    public void setExpenseAccount(SegmentAccount expenseAccount) {
        this.expenseAccount = expenseAccount;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public Integer getNoOfMonths() {
        return noOfMonths;
    }

    public void setNoOfMonths(Integer noOfMonths) {
        this.noOfMonths = noOfMonths;
    }

    public BigDecimal getMonthlyCost() {
        return monthlyCost;
    }

    public void setMonthlyCost(BigDecimal monthlyCost) {
        this.monthlyCost = monthlyCost;
    }

    public BigDecimal getAppliedCost() {
        return appliedCost;
    }

    public void setAppliedCost(BigDecimal appliedCost) {
        this.appliedCost = appliedCost;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
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
}
