package com.tri.erp.spring.response;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Personal on 4/29/2015.
 */
public class RvDetailDto {
    private Integer id;
    private Integer rvId;
    private Integer itemId;
    private String itemCode;
    private Integer unitId;
    private String unitCode;
    private String itemDescription;
    private BigDecimal quantity = BigDecimal.ZERO;
    private String joDescription;
    private String rvNumber;
    private Date rvDate;
    private BigDecimal poQuantity = BigDecimal.ZERO;

    public RvDetailDto() {}

    public RvDetailDto(Integer id, Integer rvId, Integer itemId, String itemCode, String itemDescription, BigDecimal quantity,
                       Integer unitId, String unitCode, String joDescription, String rvNumber, Date rvDate) {
        this.id = id;
        this.itemId = itemId;
        this.itemCode = itemCode;
        this.unitId = unitId;
        this.unitCode = unitCode;
        this.itemDescription = itemDescription;
        this.quantity = quantity;
        this.rvId = rvId;
        this.joDescription = joDescription;
        this.rvNumber = rvNumber;
        this.rvDate = rvDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
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

    public Integer getUnitId() {
        return unitId;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }

    public String getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public Integer getRvId() {
        return rvId;
    }

    public void setRvId(Integer rvId) {
        this.rvId = rvId;
    }

    public String getJoDescription() {
        return joDescription;
    }

    public void setJoDescription(String joDescription) {
        this.joDescription = joDescription;
    }

    public String getRvNumber() {
        return rvNumber;
    }

    public void setRvNumber(String rvNumber) {
        this.rvNumber = rvNumber;
    }

    public Date getRvDate() {
        return rvDate;
    }

    public void setRvDate(Date rvDate) {
        this.rvDate = rvDate;
    }

    public BigDecimal getPoQuantity() {
        return poQuantity;
    }

    public void setPoQuantity(BigDecimal poQuantity) {
        this.poQuantity = poQuantity;
    }
}
