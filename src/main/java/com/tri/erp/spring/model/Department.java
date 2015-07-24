package com.tri.erp.spring.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Created by Ryan D. Repe on 10/6/2014.
 */
@Entity
public class Department implements Serializable {

    @Id
    @GeneratedValue
    @Column
    private int id;

    @NotEmpty
    @Column
    private String name;

    @NotEmpty
    @Column
    private String abbreviation;

    public Department() {
    }

    public Department(String name, String abbreviation) {
        this.name = name;
        this.abbreviation = abbreviation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }
}
