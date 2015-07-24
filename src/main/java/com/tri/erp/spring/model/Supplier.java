package com.tri.erp.spring.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by TSI Admin on 11/10/2014.
 */

@Entity
public class Supplier implements Serializable {

    @Id
    @GeneratedValue
    @Column
    private Integer id;

    @NotNull
    @Length(min = 3, max = 512, message = "Invalid length for full name (max=512, min=3)")
    @Column
    private String name;

    @Column
    private String address;

    @Column
    private String phone;

    @Column
    private String fax;

    @Column
    private String contactPerson;

    @Column
    private String contactPersonPosition;

    @Column(name = "FK_accountNo")
    private Integer accountNumber;

    @Column
    private String email;

    @Column
    private String tin;

    @Column
    private Boolean vatable;

    @Column
    private String bankAccountNumber;

    @Column
    private Boolean status;

    @Column
    private BigDecimal creditLimit;

    @Column
    private String zip;

    @JsonIgnoreProperties(ignoreUnknown = true)
    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="FK_createdByUserId", nullable = true, columnDefinition = "0")
    private User createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date updatedAt;

    public Supplier() {}

    public Supplier(String name, String address, String phone, String fax, String contactPerson, String contactPersonPosition,
                    Integer accountNumber, String email, String tin, Boolean vatable, String bankAccountNumber, Boolean status,
                    BigDecimal creditLimit, User createdBy, Date createdAt, Date updatedAt, String zip) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.fax = fax;
        this.contactPerson = contactPerson;
        this.contactPersonPosition = contactPersonPosition;
        this.accountNumber = accountNumber;
        this.email = email;
        this.tin = tin;
        this.vatable = vatable;
        this.bankAccountNumber = bankAccountNumber;
        this.status = status;
        this.creditLimit = creditLimit;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.zip = zip;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getContactPersonPosition() {
        return contactPersonPosition;
    }

    public void setContactPersonPosition(String contactPersonPosition) {
        this.contactPersonPosition = contactPersonPosition;
    }

    public Integer getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Integer accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTin() {
        return tin;
    }

    public void setTin(String tin) {
        this.tin = tin;
    }

    public Boolean isVatable() {
        return vatable;
    }

    public void isVatable(Boolean vatable) {
        this.vatable = vatable;
    }

    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    public void setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public BigDecimal getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(BigDecimal creditLimit) {
        this.creditLimit = creditLimit;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
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

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }
}
