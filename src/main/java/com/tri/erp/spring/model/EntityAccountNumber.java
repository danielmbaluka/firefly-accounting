package com.tri.erp.spring.model;

import javax.persistence.*;

/**
 * Created by TSI Admin on 11/12/2014.
 */

@Entity
public class EntityAccountNumber {

    @Id
    @GeneratedValue
    @Column
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="FK_createdByUserId", nullable = true, columnDefinition = "0")
    private User createdBy;

    public EntityAccountNumber(User createdBy) {
        this.createdBy = createdBy;
    }

    public EntityAccountNumber() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }
}
