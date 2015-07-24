package com.tri.erp.spring.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by nsutgio2015 on 5/19/2015.
 */
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class BankDeposit extends Voucher {

//    @NotNull(message = "Please enter deposit date.")
//    @Column
//    private Date depositDate;
    @NotNull(message = "Please enter deposit number.")
    @Column
    private String depositNumber;

    public BankDeposit() {
    }

    public String getDepositNumber() {
        return depositNumber;
    }

    public void setDepositNumber(String depositNumber) {
        this.depositNumber = depositNumber;
    }

//    public BankDeposit(Date depositDate, String depositNumber) {
//        this.depositDate = depositDate;
//        this.depositNumber = depositNumber;
//
//    }

    public BankDeposit(String depositNumber) {
        this.depositNumber = depositNumber;

    }
//    public Date getDepositDate() {
//        return depositDate;
//    }
//
//    public void setDepositDate(Date depositDate) {
//        this.depositDate = depositDate;
//    }
}
