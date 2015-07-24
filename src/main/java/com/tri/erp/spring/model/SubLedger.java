package com.tri.erp.spring.model;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by TSI Admin on 4/24/2015.
 */

@Entity
public class SubLedger extends Ledger implements Serializable {

    @ManyToOne
    @JoinColumn(name="FK_accountNo")
    private SlEntity slEntity;

    @ManyToOne
    @JoinColumn(name="FK_generalLedgerLineId", nullable = true)
    private GeneralLedger generalLedger;

    public SubLedger() {}

    public SubLedger(SlEntity slEntity, GeneralLedger generalLedger) {
        this.slEntity = slEntity;
        this.generalLedger = generalLedger;
    }

    public SubLedger(BigDecimal debit, BigDecimal credit, Transaction transaction, SegmentAccount segmentAccount, SlEntity slEntity, GeneralLedger generalLedger) {
        super(debit, credit, transaction, segmentAccount);
        this.slEntity = slEntity;
        this.generalLedger = generalLedger;
    }

    public SlEntity getSlEntity() {
        return slEntity;
    }

    public void setSlEntity(SlEntity slEntity) {
        this.slEntity = slEntity;
    }

    public GeneralLedger getGeneralLedger() {
        return generalLedger;
    }

    @Transient
    public void setGeneralLedger(GeneralLedger generalLedger) {
        this.generalLedger = generalLedger;
    }
}
