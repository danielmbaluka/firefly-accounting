package com.tri.erp.spring.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;

/**
 * Created by TSI Admin on 9/15/2014.
 */

@Entity
public class SegmentAccount  implements java.io.Serializable {

    @Id
    @GeneratedValue
    @Column
    private int id;

    @Column
    private String accountCode;

    @ManyToOne
    @JoinColumn(name = "FK_accountId")
    private Account account;

    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_businessSegmentId")
    private BusinessSegment businessSegment;

    public SegmentAccount(String accountCode, Account account, BusinessSegment businessSegment) {
        this.accountCode = accountCode;
        this.account = account;
        this.businessSegment = businessSegment;
    }

    public SegmentAccount() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }

    public Account getAccount() {
        return account;
    }
    public void setAccount(Account account) {
        this.account = account;
    }

    public BusinessSegment getBusinessSegment() {
        return businessSegment;
    }

    public void setBusinessSegment(BusinessSegment businessSegment) {
        this.businessSegment = businessSegment;
    }

    @Override
    public String toString() {
        return this.getAccountCode() + " " + this.getAccount().getTitle();
    }
}
