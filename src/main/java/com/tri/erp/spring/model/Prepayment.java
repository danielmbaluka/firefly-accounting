package com.tri.erp.spring.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Personal on 6/3/2015.
 */
@Entity
public class Prepayment extends Document implements Serializable {

    @NotNull(message = "Please enter date paid1.")
    @Column
    private Date datePaid;

    @NotNull(message = "Failed to insert accountNo.")
    @Column(name = "FK_accountNo")
    private Integer accountNo;

    @NotNull(message = "Please enter description.")
    @Column
    private String description;

    @NotNull(message = "Please select Prepayment Account.")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="FK_prepaymentSegmentAccountId")
    private SegmentAccount prepaymentAccount;

    @NotNull(message = "Please select Expense Account.")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="FK_expenseSegmentAccountId")
    private SegmentAccount expenseAccount;

    @NotNull(message = "Total Cost must be greater than zero.")
    @Column
    private BigDecimal totalCost = BigDecimal.ZERO;

    @NotNull(message = "Please enter no. of months.")
    @Column
    private Integer noOfMonths;

    @NotNull(message = "Monthly Cost must be greater than zero.")
    @Column
    private BigDecimal monthlyCost = BigDecimal.ZERO;

    @NotNull(message = "Applied Cost must be greater than zero.")
    @Column
    private BigDecimal appliedCost = BigDecimal.ZERO;

    @NotNull(message = "Balance must be greater than zero.")
    @Column
    private BigDecimal balance = BigDecimal.ZERO;

    public Prepayment() {}

    public Prepayment(Date datePaid, Integer accountNo, String description, SegmentAccount prepaymentAccount,
                      SegmentAccount expenseAccount, BigDecimal totalCost, Integer noOfMonths, BigDecimal monthlyCost,
                      BigDecimal appliedCost, BigDecimal balance) {
        this.datePaid = datePaid;
        this.accountNo = accountNo;
        this.description = description;
        this.prepaymentAccount = prepaymentAccount;
        this.expenseAccount = expenseAccount;
        this.totalCost = totalCost;
        this.noOfMonths = noOfMonths;
        this.monthlyCost = monthlyCost;
        this.appliedCost = appliedCost;
        this.balance = balance;
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
}