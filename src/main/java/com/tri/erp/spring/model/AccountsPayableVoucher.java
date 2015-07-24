package com.tri.erp.spring.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by TSI Admin on 2/26/2015.
 */

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountsPayableVoucher extends Voucher {

    @NotNull(message = "Please enter particulars.")
    @Column
    private String particulars;

    @NotNull(message = "Please enter due date.")
    @Column
    private Date dueDate;

    @NotNull(message = "Please select vendor.")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="FK_vendorAccountNo")
    private SlEntity vendor;

    public AccountsPayableVoucher() {}

    public AccountsPayableVoucher(String particulars, Date dueDate, SlEntity vendor) {
        this.particulars = particulars;
        this.dueDate = dueDate;
        this.vendor = vendor;
    }

    public String getParticulars() {
        return particulars;
    }

    public void setParticulars(String particulars) {
        this.particulars = particulars;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public SlEntity getVendor() {
        return vendor;
    }

    public void setVendor(SlEntity vendor) {
        this.vendor = vendor;
    }
}
