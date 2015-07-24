package com.tri.erp.spring.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

/**
 * Created by TSI Admin on 10/6/2014.
 */
@Entity
public class
        Item {
    @Id
    @GeneratedValue
    @Column
    private Integer id;

    @NotEmpty
    @Column
    private String code;

    @NotEmpty
    @Column
    private String description;

    @JsonIgnoreProperties(ignoreUnknown = true)
    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_unitId")
    private UnitMeasure unit;

    @JsonIgnoreProperties(ignoreUnknown = true)
    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_segmentAccountId")
    private SegmentAccount segmentAccount;

    public Item(String code, String description, UnitMeasure unit, SegmentAccount segmentAccount) {
        this.code = code;
        this.description = description;
        this.unit = unit;
        this.segmentAccount = segmentAccount;
    }

    public Item(){}

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

    public UnitMeasure getUnit() {
        return unit;
    }

    public void setUnit(UnitMeasure unit) {
        this.unit = unit;
    }

    public SegmentAccount getSegmentAccount() {
        return segmentAccount;
    }

    public void setSegmentAccount(SegmentAccount segmentAccount) {
        this.segmentAccount = segmentAccount;
    }
}
