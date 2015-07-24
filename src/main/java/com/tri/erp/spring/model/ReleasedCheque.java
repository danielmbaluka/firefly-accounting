package com.tri.erp.spring.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by TSI Admin on 6/19/2015.
 */
@Entity(name = "CheckVoucherReleasedCheque")
public class ReleasedCheque implements Serializable {
    @Id
    @GeneratedValue
    @Column
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "FK_checkVoucherChequeId")
    private CheckVoucherCheque check;

    @Column
    private String receivedBy;

    @Column
    private Date dateReleased;

    @Column
    private String orNumber;

    @Column
    private String remarks;

    @Column
    private String personImage;

    @Column
    private String signature;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date updatedAt;

    public ReleasedCheque() {}

    public ReleasedCheque(CheckVoucherCheque check, String receivedBy, Date dateReleased, String orNumber, String remarks,
                          String personImage, String signature, Date createdAt, Date updatedAt) {
        this.check = check;
        this.receivedBy = receivedBy;
        this.dateReleased = dateReleased;
        this.orNumber = orNumber;
        this.remarks = remarks;
        this.personImage = personImage;
        this.signature = signature;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CheckVoucherCheque getCheck() {
        return check;
    }

    public void setCheck(CheckVoucherCheque check) {
        this.check = check;
    }

    public String getReceivedBy() {
        return receivedBy;
    }

    public void setReceivedBy(String receivedBy) {
        this.receivedBy = receivedBy;
    }

    public Date getDateReleased() {
        return dateReleased;
    }

    public void setDateReleased(Date dateReleased) {
        this.dateReleased = dateReleased;
    }

    public String getOrNumber() {
        return orNumber;
    }

    public void setOrNumber(String orNumber) {
        this.orNumber = orNumber;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getPersonImage() {
        return personImage;
    }

    public void setPersonImage(String personImage) {
        this.personImage = personImage;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
