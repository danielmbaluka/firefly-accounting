package com.tri.erp.spring.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

/**
 * Created by TSI Admin on 9/9/2014.
 */

@Entity
public class AccountGroup implements java.io.Serializable {

    @Id
    @GeneratedValue
    private int id;

    @NotEmpty
    @Column
    private String code;

    @NotEmpty
    @Column
    private String description;

    public AccountGroup(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public AccountGroup() {}

    public String getCode() {
        return code;
    }

    /**
     * @param code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof AccountGroup) {
            return description.equalsIgnoreCase(((AccountGroup) obj).description);
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

}