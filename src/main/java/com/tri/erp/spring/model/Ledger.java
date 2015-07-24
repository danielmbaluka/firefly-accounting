package com.tri.erp.spring.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by TSI Admin on 4/24/2015.
 */

@MappedSuperclass
public class Ledger implements Serializable {

    @Id
    @GeneratedValue
    @Column
    private Integer id;

    @Column
    private BigDecimal debit = BigDecimal.ZERO;

    @Column
    private BigDecimal credit = BigDecimal.ZERO;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "FK_transactionId")
    private Transaction transaction;

    @JsonIgnoreProperties(ignoreUnknown = true)
    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="FK_segmentAccountId", nullable = true, columnDefinition = "0")
    private SegmentAccount segmentAccount;

    public Ledger() {}

    public Ledger(BigDecimal debit, BigDecimal credit, Transaction transaction, SegmentAccount segmentAccount) {
        this.debit = debit;
        this.credit = credit;
        this.transaction = transaction;
        this.segmentAccount = segmentAccount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getDebit() {
        return debit;
    }

    public void setDebit(BigDecimal debit) {
        this.debit = debit;
    }

    public BigDecimal getCredit() {
        return credit;
    }

    public void setCredit(BigDecimal credit) {
        this.credit = credit;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public SegmentAccount getSegmentAccount() {
        return segmentAccount;
    }

    public void setSegmentAccount(SegmentAccount segmentAccount) {
        this.segmentAccount = segmentAccount;
    }
}
