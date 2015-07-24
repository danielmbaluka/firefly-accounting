package com.tri.erp.spring.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

/**
 * Created by nsutgio2015 on 5/19/2015.
 */
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class CashReceipts extends Voucher {

    @NotNull(message = "Please enter particulars.")
    @Column
    private String particulars;

    public CashReceipts() {
    }

    public CashReceipts(String particulars) {
        this.particulars = particulars;
    }

    public String getParticulars() {
        return particulars;
    }

    public void setParticulars(String particulars) {
        this.particulars = particulars;
    }
}
