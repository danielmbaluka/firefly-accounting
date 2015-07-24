package com.tri.erp.spring.model;

import java.math.BigDecimal;

/**
 * Created by nsutgio2015 on 6/8/2015.
 */
public class PostedMirDetail {

    private String particulars;
    private String account;
    private BigDecimal debit;
    private BigDecimal credit;

    public PostedMirDetail() {
    }

    public String getParticulars() {
        return particulars;
    }

    public void setParticulars(String particulars) {
        this.particulars = particulars;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public BigDecimal getDebit() {
        return debit;
    }

    public void setDebit(BigDecimal debit) {
        this.debit = debit;
    }

    public BigDecimal getCredit() {
        return credit;
    }

    public void setCredit(BigDecimal credit) {
        this.credit = credit;
    }
}
