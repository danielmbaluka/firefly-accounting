package com.tri.erp.spring.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by TSI Admin.
 */
@Entity
public class Asset implements Serializable {

    @Id
    @GeneratedValue
    @Column
    private Integer id;

    @Column(name = "FK_accountNo")
    private Integer fkAccountNo;

    @Column
    private String refNo;

    @Column
    private String description;

    @Column
    private Date acquisitionDate;

    @Column
    private BigDecimal value;

    @Column
    private Integer depreciationMonths;

    @Column
    private BigDecimal depreciationYears;

    @Column
    private Integer startYear;

    @Column
    private Integer startMonth;

    @Column
    private Integer endYear;

    @Column
    private Integer endMonth;

    @Column
    private BigDecimal monthlyDepreciation;

    @Column
    private BigDecimal depreciatedValue;

    @Column
    private BigDecimal remainingValue;

    @JsonIgnoreProperties(ignoreUnknown = true)
    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_assetAccountId", nullable = true, columnDefinition = "0")
    private Account fkAssetAccount;

    @JsonIgnoreProperties(ignoreUnknown = true)
    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_expenseAccountId", nullable = true, columnDefinition = "0")
    private Account fkExpenseAccount;

    @JsonIgnoreProperties(ignoreUnknown = true)
    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_accountPepAccountId", nullable = true, columnDefinition = "0")
    private Account fkAccountPepAccount;

    @JsonIgnoreProperties(ignoreUnknown = true)
    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_createdByUserId", nullable = true, columnDefinition = "0")
    private User fkCreatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date createdAt;

    public Asset() {
    }

    public Asset(Integer fkAccountNo, String refNo, String description, Date acquisitionDate, BigDecimal value, Integer depreciationMonths, BigDecimal depreciationYears, Integer startYear, Integer startMonth, Integer endYear, Integer endMonth, BigDecimal monthlyDepreciation, BigDecimal depreciatedValue, BigDecimal remainingValue, Account fkAssetAccount, Account fkExpenseAccount, Account fkAccountPepAccount, User fkCreatedBy, Date createdAt) {
        this.fkAccountNo = fkAccountNo;
        this.refNo = refNo;
        this.description = description;
        this.acquisitionDate = acquisitionDate;
        this.value = value;
        this.depreciationMonths = depreciationMonths;
        this.depreciationYears = depreciationYears;
        this.startYear = startYear;
        this.startMonth = startMonth;
        this.endYear = endYear;
        this.endMonth = endMonth;
        this.monthlyDepreciation = monthlyDepreciation;
        this.depreciatedValue = depreciatedValue;
        this.remainingValue = remainingValue;
        this.fkAssetAccount = fkAssetAccount;
        this.fkExpenseAccount = fkExpenseAccount;
        this.fkAccountPepAccount = fkAccountPepAccount;
        this.fkCreatedBy = fkCreatedBy;
        this.createdAt = createdAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFkAccountNo() {
        return fkAccountNo;
    }

    public void setFkAccountNo(Integer fkAccountNo) {
        this.fkAccountNo = fkAccountNo;
    }

    public String getRefNo() {
        return refNo;
    }

    public void setRefNo(String refNo) {
        this.refNo = refNo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getAcquisitionDate() {
        return acquisitionDate;
    }

    public void setAcquisitionDate(Date acquisitionDate) {
        this.acquisitionDate = acquisitionDate;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public Integer getDepreciationMonths() {
        return depreciationMonths;
    }

    public void setDepreciationMonths(Integer depreciationMonths) {
        this.depreciationMonths = depreciationMonths;
    }

    public BigDecimal getDepreciationYears() {
        return depreciationYears;
    }

    public void setDepreciationYears(BigDecimal depreciationYears) {
        this.depreciationYears = depreciationYears;
    }

    public Integer getStartYear() {
        return startYear;
    }

    public void setStartYear(Integer startYear) {
        this.startYear = startYear;
    }

    public Integer getStartMonth() {
        return startMonth;
    }

    public void setStartMonth(Integer startMonth) {
        this.startMonth = startMonth;
    }

    public Integer getEndYear() {
        return endYear;
    }

    public void setEndYear(Integer endYear) {
        this.endYear = endYear;
    }

    public Integer getEndMonth() {
        return endMonth;
    }

    public void setEndMonth(Integer endMonth) {
        this.endMonth = endMonth;
    }

    public BigDecimal getMonthlyDepreciation() {
        return monthlyDepreciation;
    }

    public void setMonthlyDepreciation(BigDecimal monthlyDepreciation) {
        this.monthlyDepreciation = monthlyDepreciation;
    }

    public BigDecimal getDepreciatedValue() {
        return depreciatedValue;
    }

    public void setDepreciatedValue(BigDecimal depreciatedValue) {
        this.depreciatedValue = depreciatedValue;
    }

    public BigDecimal getRemainingValue() {
        return remainingValue;
    }

    public void setRemainingValue(BigDecimal remainingValue) {
        this.remainingValue = remainingValue;
    }

    public Account getFkAssetAccount() {
        return fkAssetAccount;
    }

    public void setFkAssetAccount(Account fkAssetAccount) {
        this.fkAssetAccount = fkAssetAccount;
    }

    public Account getFkExpenseAccount() {
        return fkExpenseAccount;
    }

    public void setFkExpenseAccount(Account fkExpenseAccount) {
        this.fkExpenseAccount = fkExpenseAccount;
    }

    public Account getFkAccountPepAccount() {
        return fkAccountPepAccount;
    }

    public void setFkAccountPepAccount(Account fkAccountPepAccount) {
        this.fkAccountPepAccount = fkAccountPepAccount;
    }

    public User getFkCreatedBy() {
        return fkCreatedBy;
    }

    public void setFkCreatedBy(User fkCreatedBy) {
        this.fkCreatedBy = fkCreatedBy;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
