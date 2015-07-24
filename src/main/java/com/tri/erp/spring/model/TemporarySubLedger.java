package com.tri.erp.spring.model;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by TSI Admin on 4/24/2015.
 */

@Entity
public class TemporarySubLedger extends Ledger implements Serializable {

    @ManyToOne
    @JoinColumn(name="FK_accountNo")
    private SlEntity slEntity;

    @ManyToOne
    @JoinColumn(name="FK_temporaryGeneralLedger", nullable = true)
    private TemporaryGeneralLedger temporaryGeneralLedger;

    public TemporarySubLedger() {}

    public TemporarySubLedger(SlEntity slEntity, TemporaryGeneralLedger temporaryGeneralLedger) {
        this.slEntity = slEntity;
        this.temporaryGeneralLedger = temporaryGeneralLedger;
    }

    public TemporarySubLedger(BigDecimal debit, BigDecimal credit, Transaction transaction, SegmentAccount segmentAccount,
                              SlEntity slEntity, TemporaryGeneralLedger temporaryGeneralLedger) {
        super(debit, credit, transaction, segmentAccount);
        this.slEntity = slEntity;
        this.temporaryGeneralLedger = temporaryGeneralLedger;
    }

    public SlEntity getSlEntity() {
        return slEntity;
    }

    public void setSlEntity(SlEntity slEntity) {
        this.slEntity = slEntity;
    }


    public TemporaryGeneralLedger getTemporaryGeneralLedger() {
        return temporaryGeneralLedger;
    }

    public void setTemporaryGeneralLedger(TemporaryGeneralLedger temporaryGeneralLedger) {
        this.temporaryGeneralLedger = temporaryGeneralLedger;
    }
}
