package com.tri.erp.spring.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by TSI Admin on 6/25/2015.
 */
@Entity
public class TaxCode {

    @Id
    @GeneratedValue
    @Column
    private Integer id;

    @Column
    private String code;

    @Column
    private String description;

    public TaxCode() {}

    public TaxCode(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
