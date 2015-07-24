package com.tri.erp.spring.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by Personal on 5/12/2015.
 */
@Entity
public class CanvassDetail implements Serializable {
    @Id
    @GeneratedValue
    @Column
    private Integer id;

    @JsonIgnoreProperties(ignoreUnknown = true)
    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="FK_canvassId")
    private Canvass canvass;

    @JsonIgnoreProperties(ignoreUnknown = true)
    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="FK_rvDetailId")
    private RvDetail rvDetail;

    public CanvassDetail() {}

    public CanvassDetail(Canvass canvass, RvDetail rvDetail) {
        this.canvass = canvass;
        this.rvDetail = rvDetail;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Canvass getCanvass() {
        return canvass;
    }

    public void setCanvass(Canvass canvass) {
        this.canvass = canvass;
    }

    public RvDetail getRvDetail() {
        return rvDetail;
    }

    public void setRvDetail(RvDetail rvDetail) {
        this.rvDetail = rvDetail;
    }
}

