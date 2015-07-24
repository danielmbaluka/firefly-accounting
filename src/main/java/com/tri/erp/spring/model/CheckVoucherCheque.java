package com.tri.erp.spring.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by TSI Admin on 5/13/2015.
 */

@Entity
public class CheckVoucherCheque implements Serializable {

    @Id
    @GeneratedValue
    @Column
    private int id;

    @Column
    private String checkNumber;

    @ManyToOne
    @JoinColumn(name = "FK_transactionId")
    private Transaction transaction;

    @ManyToOne
    @JoinColumn(name = "FK_segmentBankAccountId")
    private SegmentAccount bankAccount;


    @Column
    private Boolean released;

    public CheckVoucherCheque() {}

    public CheckVoucherCheque(String checkNumber, Transaction transaction, SegmentAccount bankAccount, Boolean released) {
        this.checkNumber = checkNumber;
        this.transaction = transaction;
        this.bankAccount = bankAccount;
        this.released = released;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCheckNumber() {
        return checkNumber;
    }

    public void setCheckNumber(String checkNumber) {
        this.checkNumber = checkNumber;
    }

    public SegmentAccount getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(SegmentAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public Boolean isReleased() {
        return released;
    }

    public void setReleased(Boolean released) {
        this.released = released;
    }
}
