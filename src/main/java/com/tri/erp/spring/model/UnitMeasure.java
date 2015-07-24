package com.tri.erp.spring.model;

import javax.persistence.*;

/**
 * Created by TSI Admin on 10/6/2014.
 */
@Entity
public class UnitMeasure {
    @Id
    @GeneratedValue
    @Column
    private Integer id;

    @Column(unique = true)
    private String code;

    @Column(unique = true)
    private String description;

    public UnitMeasure(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public UnitMeasure() { }

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
