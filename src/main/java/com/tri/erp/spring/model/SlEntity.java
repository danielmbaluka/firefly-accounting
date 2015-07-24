package com.tri.erp.spring.model;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.stereotype.Component;

import javax.persistence.*;

/**
 * Created by TSI Admin on 10/5/2014.
 */

@Entity
@Table(name = "slentity")
public class SlEntity {

    @Id
    @GeneratedValue
    @Column
    Integer accountNo;

    @Column
    String name;

    @Column
    String address;

    @Column
    int marker;

    public SlEntity(Integer accountNo, String name, String address, int marker) {
        this.accountNo = accountNo;
        this.name = name;
        this.address = address;
        this.marker = marker;
    }

    public SlEntity() {}

    public Integer getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(Integer accountNo) {
        this.accountNo = accountNo;
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

    public int getMarker() {
        return marker;
    }

    public void setMarker(int marker) {
        this.marker = marker;
    }
}
