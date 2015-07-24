package com.tri.erp.spring.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tri.erp.spring.response.PoDetailDto;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Personal on 5/15/2015.
 */
@Entity
public class PurchaseOrder extends Document implements Serializable {

    @NotNull(message = "Please enter voucher date.")
    @Column
    private Date voucherDate;

    @Column
    private Integer year;

    @Column
    private Integer term;

    @NotNull(message = "Please select vendor.")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "FK_vendorAccountNo")
    private SlEntity vendor;

    @NotNull(message = "Total Amount must be greater than zero.")
    private BigDecimal amount = BigDecimal.ZERO;

    @NotNull(message = "Please select noting officer.")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_notedByUserId")
    private User notedBy;

    @Transient
    @JsonIgnoreProperties(ignoreUnknown = true)
    @NotFound(action = NotFoundAction.IGNORE)
    private ArrayList<PoDetailDto> poDetails = new ArrayList<>();

    public PurchaseOrder() {
    }

    public PurchaseOrder(Date voucherDate, Integer year, SlEntity vendor, ArrayList<PoDetailDto> poDetails,
                         Integer term, BigDecimal amount, User notedBy) {
        this.voucherDate = voucherDate;
        this.year = year;
        this.vendor = vendor;
        this.poDetails = poDetails;
        this.term = term;
        this.amount = amount;
        this.notedBy = notedBy;
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

    public SlEntity getVendor() {
        return vendor;
    }

    public void setVendor(SlEntity vendor) {
        this.vendor = vendor;
    }

    public ArrayList<PoDetailDto> getPoDetails() {
        return poDetails;
    }

    public void setPoDetails(ArrayList<PoDetailDto> poDetails) {
        this.poDetails = poDetails;
    }

    public Integer getTerm() {
        return term;
    }

    public void setTerm(Integer term) {
        this.term = term;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public User getNotedBy() {
        return notedBy;
    }

    public void setNotedBy(User notedBy) {
        this.notedBy = notedBy;
    }
}