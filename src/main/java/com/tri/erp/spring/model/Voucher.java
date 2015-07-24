package com.tri.erp.spring.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tri.erp.spring.response.GeneralLedgerLineDto;
import com.tri.erp.spring.response.GeneralLedgerLineDto2;
import com.tri.erp.spring.response.SubLedgerDto;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by TSI Admin on 4/24/2015.
 */
@MappedSuperclass
@JsonIgnoreProperties(ignoreUnknown = true)
public class Voucher extends Document {

    @NotNull(message = "Please enter voucher date.")
    @Column
    private Date voucherDate;

    @Column
    private Integer year;

    @Column
    private BigDecimal amount = BigDecimal.ZERO;

    @NotNull(message = "Please select checker.")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_checkedByUserId")
    private User checker;

    @Transient
    private List<GeneralLedgerLineDto2> generalLedgerLines = new ArrayList<>();

    @Transient
    @NotFound(action = NotFoundAction.IGNORE)
    private List<SubLedgerDto> subLedgerLines = new ArrayList<>();

    public Voucher() {}

    public Voucher(Date voucherDate, Integer year, BigDecimal amount, User checker, List<GeneralLedgerLineDto2> generalLedgerLines, List<SubLedgerDto> subLedgerLines) {
        this.voucherDate = voucherDate;
        this.year = year;
        this.amount = amount;
        this.checker = checker;
        this.generalLedgerLines = generalLedgerLines;
        this.subLedgerLines = subLedgerLines;
    }

    public Date getVoucherDate() {
        return voucherDate;
    }

    public void setVoucherDate(Date voucherDate) {
        this.voucherDate = voucherDate;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public User getChecker() {
        return checker;
    }

    public void setChecker(User checker) {
        this.checker = checker;
    }

    public List<GeneralLedgerLineDto2> getGeneralLedgerLines() {
        return generalLedgerLines;
    }

    public void setGeneralLedgerLines(ArrayList<GeneralLedgerLineDto2> generalLedgerLines) {
        this.generalLedgerLines = generalLedgerLines;
    }

    public List<SubLedgerDto> getSubLedgerLines() {
        return subLedgerLines;
    }

    public void setSubLedgerLines(List<SubLedgerDto> subLedgerLines) {
        this.subLedgerLines = subLedgerLines;
    }
}
