package com.tri.erp.spring.model;

import javax.persistence.*;

/**
 * Created by TSI Admin on 9/15/2014.
 */
@Entity
public class BusinessActivity implements java.io.Serializable {
    @Id
    @GeneratedValue
    @Column
    private Integer id;

    @Column
    private
    String description;

    @Column(unique = true)
    private
    String code;

    public BusinessActivity(String description, String code) {
        this.description = description;
        this.code = code;
    }

    public BusinessActivity() {

    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setOde(String ode) {
        this.code = ode;
    }
}
