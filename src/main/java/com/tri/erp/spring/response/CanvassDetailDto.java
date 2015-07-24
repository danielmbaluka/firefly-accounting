package com.tri.erp.spring.response;

import java.math.BigDecimal;

/**
 * Created by Personal on 5/12/2015.
 */
public class CanvassDetailDto {
    private Integer id;
    private Integer canvassId;
    private Integer rvDetailId;
    private String itemCode;
    private String unitCode;
    private String itemDescription;
    private BigDecimal quantity = BigDecimal.ZERO;
    private String rvNumber;

    public CanvassDetailDto() {}

    public CanvassDetailDto(Integer id, Integer canvassId, Integer rvDetailId, String itemCode, String unitCode,
                            String itemDescription, BigDecimal quantity, String rvNumber) {
        this.id = id;
        this.rvDetailId = rvDetailId;
        this.canvassId = canvassId;
        this.itemCode = itemCode;
        this.unitCode = unitCode;
        this.itemDescription = itemDescription;
        this.quantity = quantity;
        this.rvNumber = rvNumber;
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

    public Integer getCanvassId() {
        return canvassId;
    }

    public void setCanvassId(Integer canvassId) {
        this.canvassId = canvassId;
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

    public String getRvNumber() {
        return rvNumber;
    }

    public void setRvNumber(String rvNumber) {
        this.rvNumber = rvNumber;
    }
}
