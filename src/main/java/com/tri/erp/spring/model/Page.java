package com.tri.erp.spring.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by TSI Admin on 3/6/2015.
 */

@Entity
public class Page {

    @Id
    @GeneratedValue
    @Column
    private Integer id;

    @Column
    private String name;

    public Page() {
    }

    public Page(String name) {
        this.name = name;
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
}
