package com.tri.erp.spring.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
/**
 * Created by Personal on 4/29/2015.
 */
@Entity
public class RvDetail implements Serializable{
    @Id
    @GeneratedValue
    @Column
    private Integer id;

    @Column
    private BigDecimal quantity;

    @Column
    private BigDecimal poQuantity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "FK_itemId")
    private Item item;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="FK_unitId")
    private UnitMeasure unitMeasure;

    @JsonIgnoreProperties(ignoreUnknown = true)
    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="FK_requisitionVoucherId")
    private RequisitionVoucher requisitionVoucher;

    @Column
    private String joDescription;

    public RvDetail() {}

    public RvDetail(BigDecimal quantity, BigDecimal poQuantity, Item item, UnitMeasure unitMeasure, RequisitionVoucher requisitionVoucher,
                    String joDescription) {
        this.setQuantity(quantity);
        this.setPoQuantity(poQuantity);
        this.setItem(item);
        this.setUnitMeasure(unitMeasure);
        this.setRequisitionVoucher(requisitionVoucher);
        this.setJoDescription(joDescription);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPoQuantity() {
        return poQuantity;
    }

    public void setPoQuantity(BigDecimal poQuantity) {
        this.poQuantity = poQuantity;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public UnitMeasure getUnitMeasure() {
        return unitMeasure;
    }

    public void setUnitMeasure(UnitMeasure unitMeasure) {
        this.unitMeasure = unitMeasure;
    }

    public RequisitionVoucher getRequisitionVoucher() {
        return requisitionVoucher;
    }

    public void setRequisitionVoucher(RequisitionVoucher requisitionVoucher) {
        this.requisitionVoucher = requisitionVoucher;
    }

    public String getJoDescription() {
        return joDescription;
    }

    public void setJoDescription(String joDescription) {
        this.joDescription = joDescription;
    }
}
