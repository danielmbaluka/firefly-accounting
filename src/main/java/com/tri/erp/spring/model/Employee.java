package com.tri.erp.spring.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by TSI Admin on 6/26/2015.
 */
@Entity
public class Employee implements Serializable {
    @Id
    @GeneratedValue
    @Column
    private Integer id;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private String tin;

    @Column(name = "FK_accountNo")
    private Integer accountNumber;

    @Column
    private String zip;

    public Employee() {}

    public Employee(String firstName, String lastName, String tin, Integer accountNumber,  String zip) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.tin = tin;
        this.zip = zip;
        this.accountNumber = accountNumber;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getTin() {
        return tin;
    }

    public void setTin(String tin) {
        this.tin = tin;
    }

    public Integer getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Integer accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getName() {
        StringBuilder builder = new StringBuilder();
        if (this.firstName != null ) builder.append(this.firstName + " ");
        if (this.lastName != null ) builder.append(this.lastName);

        return builder.toString();
    }

    public String getAddress(String addressType) {
        // build address here
        return "";
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }
}
