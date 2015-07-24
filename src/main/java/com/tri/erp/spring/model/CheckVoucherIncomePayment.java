package com.tri.erp.spring.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by TSI Admin on 6/25/2015.
 */
@Entity
public class CheckVoucherIncomePayment {
    @Id
    @GeneratedValue
    @Column
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "FK_transactionId")
    private Transaction transaction;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "FK_taxCodeId")
    private TaxCode taxCode;

    @Column
    private BigDecimal amount = BigDecimal.ZERO;

    @Column
    private BigDecimal percentage = BigDecimal.ZERO;

    public CheckVoucherIncomePayment() {}

    public CheckVoucherIncomePayment(Transaction transaction, TaxCode taxCode, BigDecimal amount, BigDecimal percentage) {
        this.transaction = transaction;
        this.taxCode = taxCode;
        this.amount = amount;
        this.percentage = percentage;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public TaxCode getTaxCode() {
        return taxCode;
    }

    public void setTaxCode(TaxCode taxCode) {
        this.taxCode = taxCode;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getPercentage() {
        return percentage;
    }

    public void setPercentage(BigDecimal percentage) {
        this.percentage = percentage;
    }
}
