package com.tri.erp.spring.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tri.erp.spring.response.GeneralLedgerLineDto;
import com.tri.erp.spring.response.GeneralLedgerLineDto2;
import com.tri.erp.spring.response.SubLedgerDto;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by TSI Admin on 2/26/2015.
 */

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class CheckVoucher extends Voucher implements Serializable {

    @NotNull(message = "Please enter particulars.")
    @Column
    private String particulars;

    @Column
    private String remarks;

    @Column
    private BigDecimal checkAmount = BigDecimal.ZERO;

    @NotNull(message = "Please select payee.")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="FK_payeeAccountNo")
    private SlEntity payee;

    @NotNull(message = "Please select recommending officer.")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_recommendedByUserId")
    private User recommendingOfficer;

    @NotNull(message = "Please select auditor")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_auditedByUserId")
    private User auditor;

    @Transient
    @NotFound(action = NotFoundAction.IGNORE)
    private List<AccountsPayableVoucher> accountsPayableVouchers = new ArrayList<>();

    @Transient
    @NotFound(action = NotFoundAction.IGNORE)
    private List<CheckVoucherCheque> checkNumbers = new ArrayList<>();

    public CheckVoucher() {}

    public CheckVoucher(Date voucherDate, Integer year, BigDecimal amount, User checker, ArrayList<GeneralLedgerLineDto2> generalLedgerLines,
                        ArrayList<SubLedgerDto> subLedgerLines, String particulars, String remarks, BigDecimal checkAmount,
                        SlEntity payee, User recommendingOfficer, User auditor, List<AccountsPayableVoucher> accountsPayableVouchers,
                        List<CheckVoucherCheque> checkNumbers) {
        super(voucherDate, year, amount, checker, generalLedgerLines, subLedgerLines);
        this.particulars = particulars;
        this.remarks = remarks;
        this.checkAmount = checkAmount;
        this.payee = payee;
        this.recommendingOfficer = recommendingOfficer;
        this.auditor = auditor;
        this.accountsPayableVouchers = accountsPayableVouchers;
        this.checkNumbers = checkNumbers;
    }

    public String getParticulars() {
        return particulars;
    }

    public void setParticulars(String particulars) {
        this.particulars = particulars;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public BigDecimal getCheckAmount() {
        return checkAmount;
    }

    public void setCheckAmount(BigDecimal checkAmount) {
        this.checkAmount = checkAmount;
    }

    public SlEntity getPayee() {
        return payee;
    }

    public void setPayee(SlEntity payee) {
        this.payee = payee;
    }

    public User getRecommendingOfficer() {
        return recommendingOfficer;
    }

    public void setRecommendingOfficer(User recommendingOfficer) {
        this.recommendingOfficer = recommendingOfficer;
    }

    public User getAuditor() {
        return auditor;
    }

    public void setAuditor(User auditor) {
        this.auditor = auditor;
    }

    public List<AccountsPayableVoucher> getAccountsPayableVouchers() {
        return accountsPayableVouchers;
    }

    public void setAccountsPayableVouchers(List<AccountsPayableVoucher> accountsPayableVouchers) {
        this.accountsPayableVouchers = accountsPayableVouchers;
    }

    public List<CheckVoucherCheque> getCheckNumbers() {
        return checkNumbers;
    }

    public void setCheckNumbers(List<CheckVoucherCheque> checkNumbers) {
        this.checkNumbers = checkNumbers;
    }
}
