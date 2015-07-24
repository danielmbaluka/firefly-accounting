package com.tri.erp.spring.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by TSI Admin on 4/23/2015.
 */
@Entity
public class DocumentStatus {

    @Id
    @GeneratedValue
    @Column
    private Integer id;

    @Column
    String status;

    public DocumentStatus() {}

    public DocumentStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
