package com.tri.erp.spring.response.reports;

import java.math.BigDecimal;

/**
 * Created by TSI Admin on 5/11/2015.
 */
public class APDetail {

    private String particulars;
    private String invoiceNumber;
    private String account;
    private BigDecimal debit;
    private BigDecimal credit;

    public  APDetail() {}
    public String getParticulars() {
        return particulars;
    }

    public void setParticulars(String particulars) {
        this.particulars = particulars;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
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
