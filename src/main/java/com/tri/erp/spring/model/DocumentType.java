package com.tri.erp.spring.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by TSI Admin on 7/6/2015.
 */

@Entity
public class DocumentType implements Serializable {

    @Id
    @GeneratedValue
    @Column
    private Integer id;

    @Column
    private String description;

    public DocumentType() {}

    public DocumentType(String description) {
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
