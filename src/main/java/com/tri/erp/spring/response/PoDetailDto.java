package com.tri.erp.spring.response;

import java.math.BigDecimal;

/**
 * Created by Personal on 5/15/2015.
 */
public class PoDetailDto {
    private Integer id;
    private Integer purchaseOrderId;
    private Integer rvDetailId;
    private String itemCode;
    private String unitCode;
    private String itemDescription;
    private BigDecimal quantity = BigDecimal.ZERO;
    private BigDecimal vat = BigDecimal.ZERO;
    private BigDecimal discount = BigDecimal.ZERO;
    private BigDecimal unitPrice = BigDecimal.ZERO;
    private BigDecimal itemAmount = BigDecimal.ZERO;
    private BigDecimal rvdQuantity = BigDecimal.ZERO;

    public PoDetailDto() {}

    public PoDetailDto(Integer id, Integer rvDetailId, Integer purchaseOrderId, String itemCode, String unitCode,
                            String itemDescription, BigDecimal quantity, BigDecimal discount,
                            BigDecimal unitPrice, BigDecimal itemAmount) {
        this.id = id;
        this.purchaseOrderId = purchaseOrderId;
        this.rvDetailId = rvDetailId;
        this.itemCode = itemCode;
        this.unitCode = unitCode;
        this.itemDescription = itemDescription;
        this.quantity = quantity;
        this.discount = discount;
        this.unitPrice = unitPrice;
        this.itemAmount = itemAmount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRvDetailId() {
        return rvDetailId;
    }

    public void setRvDetailId(Integer rvDetailId) {
        this.rvDetailId = rvDetailId;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
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

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getItemAmount() {
        return itemAmount;
    }

    public void setItemAmount(BigDecimal itemAmount) {
        this.itemAmount = itemAmount;
    }

    public Integer getPurchaseOrderId() {
        return purchaseOrderId;
    }

    public void setPurchaseOrderId(Integer purchaseOrderId) {
        this.purchaseOrderId = purchaseOrderId;
    }

    public BigDecimal getRvdQuantity() {
        return rvdQuantity;
    }

    public void setRvdQuantity(BigDecimal rvdQuantity) {
        this.rvdQuantity = rvdQuantity;
    }
}