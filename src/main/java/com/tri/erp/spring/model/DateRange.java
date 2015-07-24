package com.tri.erp.spring.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by TSI Admin on 5/19/2015.
 */
@Entity
public class DateRange {

    @Id
    @GeneratedValue
    @Column
    private Integer id;

    @Column
    private Date start;

    @Column
    private Date end;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date updatedAt;

    public DateRange() {}

    public DateRange(Date start, Date end, Date createdAt, Date updatedAt) {
        this.start = start;
        this.end = end;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
