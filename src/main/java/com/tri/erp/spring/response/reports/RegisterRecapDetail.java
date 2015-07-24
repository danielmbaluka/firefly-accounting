package com.tri.erp.spring.response.reports;

import java.math.BigDecimal;

/**
 * Created by TSI Admin on 7/22/2015.
 */
public class RegisterRecapDetail {

    private String glAccountTitle;
    private String glAccountCode;
    private String slAccountTitle;
    private String slAccountCode;
    private BigDecimal glDebit;
    private BigDecimal slDebit;
    private BigDecimal glCredit;
    private BigDecimal slCredit;

    public  RegisterRecapDetail() {}

    public RegisterRecapDetail(String glAccountTitle, String glAccountCode, String slAccountTitle, String slAccountCode,
                               BigDecimal glDebit, BigDecimal slDebit, BigDecimal glCredit, BigDecimal slCredit) {
        this.glAccountTitle = glAccountTitle;
        this.glAccountCode = glAccountCode;
        this.slAccountTitle = slAccountTitle;
        this.slAccountCode = slAccountCode;
        this.glDebit = glDebit;
        this.slDebit = slDebit;
        this.glCredit = glCredit;
        this.slCredit = slCredit;
    }

    public String getGlAccountTitle() {
        return glAccountTitle;
    }

    public void setGlAccountTitle(String glAccountTitle) {
        this.glAccountTitle = glAccountTitle;
    }

    public String getGlAccountCode() {
        return glAccountCode;
    }

    public void setGlAccountCode(String glAccountCode) {
        this.glAccountCode = glAccountCode;
    }

    public String getSlAccountTitle() {
        return slAccountTitle;
    }

    public void setSlAccountTitle(String slAccountTitle) {
        this.slAccountTitle = slAccountTitle;
    }

    public String getSlAccountCode() {
        return slAccountCode;
    }

    public void setSlAccountCode(String slAccountCode) {
        this.slAccountCode = slAccountCode;
    }

    public BigDecimal getGlDebit() {
        return glDebit;
    }

    public void setGlDebit(BigDecimal glDebit) {
        this.glDebit = glDebit;
    }

    public BigDecimal getSlDebit() {
        return slDebit;
    }

    public void setSlDebit(BigDecimal slDebit) {
        this.slDebit = slDebit;
    }

    public BigDecimal getGlCredit() {
        return glCredit;
    }

    public void setGlCredit(BigDecimal glCredit) {
        this.glCredit = glCredit;
    }

    public BigDecimal getSlCredit() {
        return slCredit;
    }

    public void setSlCredit(BigDecimal slCredit) {
        this.slCredit = slCredit;
    }
}
