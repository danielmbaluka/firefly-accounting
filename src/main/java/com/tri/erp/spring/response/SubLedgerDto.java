package com.tri.erp.spring.response;

import java.math.BigDecimal;

/**
 * Created by TSI Admin on 4/24/2015.
 */
public class SubLedgerDto {
    private Integer id;
    private Integer accountNo;
    private String name;
    private Integer segmentAccountId;
    private Integer accountId;
    private BigDecimal amount = BigDecimal.ZERO;
    private Integer generalLedgerId;

    public SubLedgerDto() {}

    public SubLedgerDto(Integer id, Integer accountNo, String name, Integer segmentAccountId, BigDecimal amount, Integer generalLedgerId) {
        this.id = id;
        this.accountNo = accountNo;
        this.name = name;
        this.segmentAccountId = segmentAccountId;
        this.amount = amount;
        this.generalLedgerId = generalLedgerId;
    }

    public Integer getGeneralLedgerId() {
        return generalLedgerId;
    }

    public void setGeneralLedgerId(Integer generalLedgerId) {
        this.generalLedgerId = generalLedgerId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(Integer accountNo) {
        this.accountNo = accountNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSegmentAccountId() {
        return segmentAccountId;
    }

    public void setSegmentAccountId(Integer segmentAccountId) {
        this.segmentAccountId = segmentAccountId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }
}