package com.tri.erp.spring.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by TSI Admin on 5/13/2015.
 */

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class CheckVoucherApv implements Serializable {

    @Id
    @GeneratedValue
    @Column
    private int id;

    @ManyToOne
    @JoinColumn(name = "FK_checkVoucherId")
    private CheckVoucher checkVoucher;

    @ManyToOne
    @JoinColumn(name = "FK_accountsPayableVoucherId")
    private AccountsPayableVoucher accountsPayableVoucher;

    public CheckVoucherApv() {
    }

    public CheckVoucherApv(CheckVoucher checkVoucher, AccountsPayableVoucher accountsPayableVoucher) {
        this.checkVoucher = checkVoucher;
        this.accountsPayableVoucher = accountsPayableVoucher;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CheckVoucher getCheckVoucher() {
        return checkVoucher;
    }

    public void setCheckVoucher(CheckVoucher checkVoucher) {
        this.checkVoucher = checkVoucher;
    }

    public AccountsPayableVoucher getAccountsPayableVoucher() {
        return accountsPayableVoucher;
    }

    public void setAccountsPayableVoucher(AccountsPayableVoucher accountsPayableVoucher) {
        this.accountsPayableVoucher = accountsPayableVoucher;
    }
}
