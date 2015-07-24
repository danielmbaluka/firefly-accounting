package com.tri.erp.spring.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tri.erp.spring.response.SubLedgerDto;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.Entity;
import javax.persistence.Transient;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Created by TSI Admin on 4/24/2015.
 */


@Entity
public class TemporaryGeneralLedger extends Ledger implements Serializable {

    @Transient
    @JsonIgnoreProperties(ignoreUnknown = true)
    @NotFound(action = NotFoundAction.IGNORE)
    private ArrayList<SubLedgerDto> subLedgerLines = new ArrayList<>();

    public TemporaryGeneralLedger(ArrayList<SubLedgerDto> subLedgerLines) {
        this.subLedgerLines = subLedgerLines;
    }

    public TemporaryGeneralLedger(BigDecimal debit, BigDecimal credit, Transaction transaction, SegmentAccount segmentAccount,
                                  ArrayList<SubLedgerDto> subLedgerLines) {
        super(debit, credit, transaction, segmentAccount);
        this.subLedgerLines = subLedgerLines;
    }

    public TemporaryGeneralLedger() {}

    public ArrayList<SubLedgerDto> getSubLedgerLines() {
        return subLedgerLines;
    }

    public void setSubLedgerLines(ArrayList<SubLedgerDto> subLedgerLines) {
        this.subLedgerLines = subLedgerLines;
    }
}
