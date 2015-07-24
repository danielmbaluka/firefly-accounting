package com.tri.erp.spring.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Created by TSI Admin on 9/9/2014.
 */

@Entity
public class AccountType implements java.io.Serializable {

    @Id
    @GeneratedValue
    private int id;

    @Column
    @NotEmpty
    private String description;

    @NotEmpty
    @Column
    private String code;

    public AccountType(String description, String code) {
        this.description = description;
        this.code = code;
    }

    public AccountType() {}
    /**
     * @return the acctTypeDesc
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param acctTypeDesc the acctTypeDesc to set
     */
    public void setDescription(String acctTypeDesc) {
        this.description = acctTypeDesc;
    }

    @Override
    public String toString() {
        return this.description;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof AccountType) {
            return id == (((AccountType) obj).id);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + code.hashCode();
        return result;
    }
}