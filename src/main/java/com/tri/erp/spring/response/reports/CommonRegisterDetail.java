package com.tri.erp.spring.response.reports;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by TSI Admin on 7/14/2015.
 */
public class CommonRegisterDetail {

    private String reference;
    private Date voucherDate;
    private String code;
    private String explanation;
    private BigDecimal debit;
    private BigDecimal credit;

    public CommonRegisterDetail() {}

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Date getVoucherDate() {
        return voucherDate;
    }

    public void setVoucherDate(Date voucherDate) {
        this.voucherDate = voucherDate;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
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
