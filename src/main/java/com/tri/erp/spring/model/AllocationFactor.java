package com.tri.erp.spring.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by TSI Admin on 5/19/2015.
 */
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class AllocationFactor implements Serializable {
    @Column
    @GeneratedValue
    @Id
    private Integer id;

    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToOne
    @JoinColumn(name = "FK_accountId")
    private Account account;

    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToOne
    @JoinColumn(name = "FK_businessSegmentId")
    private BusinessSegment businessSegment;

    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToOne
    @JoinColumn(name = "FK_effectivityDateId")
    private DateRange effectivityDate;

    @Column
    private BigDecimal percentage = BigDecimal.ZERO;

    @Temporal(value = TemporalType.TIMESTAMP)
    @Column
    private Date createdAt;

    @Temporal(value = TemporalType.TIMESTAMP)
    @Column
    private Date updatedAt;

    public AllocationFactor() {}

    public AllocationFactor(Integer id, Account account, BusinessSegment businessSegment, BigDecimal percentage,
                            DateRange effectivityDate, Date createdAt, Date updatedAt) {
        this.id = id;
        this.account = account;
        this.businessSegment = businessSegment;
        this.percentage = percentage;
        this.effectivityDate = effectivityDate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public BusinessSegment getBusinessSegment() {
        return businessSegment;
    }

    public void setBusinessSegment(BusinessSegment businessSegment) {
        this.businessSegment = businessSegment;
    }

    public BigDecimal getPercentage() {
        return percentage;
    }

    public void setPercentage(BigDecimal percentage) {
        this.percentage = percentage;
    }

    public DateRange getEffectivityDate() {
        return effectivityDate;
    }

    public void setEffectivityDate(DateRange effectivityDate) {
        this.effectivityDate = effectivityDate;
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
