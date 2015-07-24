package com.tri.erp.spring.response;

import com.tri.erp.spring.model.SlEntity;

import java.util.Date;

/**
 * Created by Personal on 4/24/2015.
 */
public class RvListDto {
    private Integer id;
    private String localCode;
    private String purpose;
    private Date voucherDate;
    private Date deliveryDate;
    private String status;
    private String rvType;
    private String preparedBy;
    private int rvTypeId;

    public RvListDto() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLocalCode() {
        return localCode;
    }

    public void setLocalCode(String localCode) {
        this.localCode = localCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getRvType() {
        return rvType;
    }

    public void setRvType(String rvType) {
        this.rvType = rvType;
    }

    public Date getVoucherDate() {
        return voucherDate;
    }

    public void setVoucherDate(Date voucherDate) {
        this.voucherDate = voucherDate;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getPreparedBy() {
        return preparedBy;
    }

    public void setPreparedBy(String preparedBy) {
        this.preparedBy = preparedBy;
    }

    public int getRvTypeId() {
        return rvTypeId;
    }

    public void setRvTypeId(int rvTypeId) {
        this.rvTypeId = rvTypeId;
    }
}
