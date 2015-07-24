package com.tri.erp.spring.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by nsutgio2015 on 4/27/2015.
 */

@Entity
public class MaterialIssueRegisterDetail implements Serializable {

    @Id
    @GeneratedValue
    @Column
    private Integer id;

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Column
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "FK_materialIssueRegisterId", referencedColumnName = "id")
    private MaterialIssueRegister materialIssueRegister;

    @Column
    private Integer quantity;

    @Column
    private BigDecimal unitPrice = BigDecimal.ZERO;

    @Column
    private BigDecimal amount = BigDecimal.ZERO;

    public MaterialIssueRegisterDetail() {
    }

    public MaterialIssueRegisterDetail(String description, MaterialIssueRegister materialIssueRegister, Integer quantity, BigDecimal unitPrice, BigDecimal amount) {
        this.description = description;
        this.materialIssueRegister = materialIssueRegister;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.amount = amount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MaterialIssueRegister getMaterialIssueRegister() {
        return materialIssueRegister;
    }

    public void setMaterialIssueRegister(MaterialIssueRegister materialIssueRegister) {
        this.materialIssueRegister = materialIssueRegister;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
