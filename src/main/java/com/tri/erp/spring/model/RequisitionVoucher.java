package com.tri.erp.spring.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tri.erp.spring.response.RvDetailDto;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Personal on 03/20/2014.
 */

@Entity
public class RequisitionVoucher extends Document implements Serializable {

    @NotNull(message = "Please enter voucher date.")
    @Column
    private Date voucherDate;

    @NotNull(message = "Please enter purpose.")
    @Column
    private String purpose;

    @NotNull(message = "Please enter delivery date.")
    @Column
    private Date deliveryDate;

    @Column
    private Integer year;

    @NotNull(message = "Please enter Rec. Approval Officer.")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="FK_recAppByUserId")
    private User recAppBy;

    @NotNull(message = "Please enter checker.")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="FK_checkedByUserId")
    private User checkedBy;

    @NotNull(message = "Please enter auditor.")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="FK_auditedByUserId")
    private User auditedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="FK_canvassedByUserId")
    private User canvassedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="FK_conformedByUserId")
    private User conformedBy;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="FK_employeeAccountNo")
    private SlEntity employee;

    @Column
    private Integer rvType;

    @Column
    private Date durationStart;

    @Column
    private Date durationEnd;

    @Transient
    @JsonIgnoreProperties(ignoreUnknown = true)
    @NotFound(action = NotFoundAction.IGNORE)
    private ArrayList<RvDetailDto> rvDetails = new ArrayList<>();

    @Column
    private String rvItType;

    public RequisitionVoucher() {}

    public RequisitionVoucher(Date voucherDate, String purpose, Date deliveryDate, User recAppBy,
                              User auditedBy, User canvassedBy, Integer rvType, User checkedBy,
                              Integer year, ArrayList<RvDetailDto> rvDetails, User conformedBy,
                              String rvItType, SlEntity employee, Date durationStart,
                              Date durationEnd) {
        this.voucherDate = voucherDate;
        this.purpose = purpose;
        this.deliveryDate = deliveryDate;
        this.recAppBy = recAppBy;
        this.checkedBy = checkedBy;
        this.auditedBy = auditedBy;
        this.canvassedBy = canvassedBy;
        this.rvType = rvType;
        this.year = year;
        this.rvDetails = rvDetails;
        this.setConformedBy(conformedBy);
        this.rvItType = rvItType;
        this.setEmployee(employee);
        this.durationStart = durationStart;
        this.durationEnd = durationEnd;
    }

    public Date getVoucherDate() {
        return voucherDate;
    }

    public void setVoucherDate(Date voucherDate) {
        this.voucherDate = voucherDate;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public User getRecAppBy() {
        return recAppBy;
    }

    public void setRecAppBy(User recAppBy) {
        this.recAppBy = recAppBy;
    }

    public User getCheckedBy() {
        return checkedBy;
    }

    public void setCheckedBy(User checkedBy) {
        this.checkedBy = checkedBy;
    }

    public User getAuditedBy() {
        return auditedBy;
    }

    public void setAuditedBy(User auditedBy) {
        this.auditedBy = auditedBy;
    }

    public User getCanvassedBy() {
        return canvassedBy;
    }

    public void setCanvassedBy(User canvassedBy) {
        this.canvassedBy = canvassedBy;
    }

    public Integer getRvType() {
        return rvType;
    }

    public void setRvType(Integer rvType) {
        this.rvType = rvType;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public ArrayList<RvDetailDto> getRvDetails() {
        return rvDetails;
    }

    public void setRvDetails(ArrayList<RvDetailDto> rvDetails) {
        this.rvDetails = rvDetails;
    }

    public User getConformedBy() {
        return conformedBy;
    }

    public void setConformedBy(User conformedBy) {
        this.conformedBy = conformedBy;
    }

    public String getRvItType() {
        return rvItType;
    }

    public void setRvItType(String rvItType) {
        this.rvItType = rvItType;
    }

    public SlEntity getEmployee() {
        return employee;
    }

    public void setEmployee(SlEntity employee) {
        this.employee = employee;
    }

    public Date getDurationStart() {
        return durationStart;
    }

    public void setDurationStart(Date durationStart) {
        this.durationStart = durationStart;
    }

    public Date getDurationEnd() {
        return durationEnd;
    }

    public void setDurationEnd(Date durationEnd) {
        this.durationEnd = durationEnd;
    }
}
