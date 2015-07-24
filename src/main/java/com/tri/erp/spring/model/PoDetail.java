package com.tri.erp.spring.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by Personal on 5/15/2015.
 */
@Entity
public class PoDetail implements Serializable {
    @Id
    @GeneratedValue
    @Column
    private Integer id;

    @JsonIgnoreProperties(ignoreUnknown = true)
    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="FK_purchaseOrderId")
    private PurchaseOrder purchaseOrder;

    @JsonIgnoreProperties(ignoreUnknown = true)
    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="FK_rvDetailId")
    private RvDetail rvDetail;

    @Column
    private BigDecimal quantity = BigDecimal.ZERO;

    @Column
    private BigDecimal unitPrice = BigDecimal.ZERO;

    @Column
    private BigDecimal vat = BigDecimal.ZERO;

    @Column
    private BigDecimal discount = BigDecimal.ZERO;

    @Column
    private BigDecimal amount = BigDecimal.ZERO;

    public PoDetail() {}

    public PoDetail(Canvass canvass, RvDetail rvDetail) {
        this.rvDetail = rvDetail;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public RvDetail getRvDetail() {
        return rvDetail;
    }

    public void setRvDetail(RvDetail rvDetail) {
        this.rvDetail = rvDetail;
    }

    public PurchaseOrder getPurchaseOrder() {
        return purchaseOrder;
    }

    public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getVat() {
        return vat;
    }

    public void setVat(BigDecimal vat) {
        this.vat = vat;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}

