package com.tri.erp.spring.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tri.erp.spring.response.GeneralLedgerLineDto;
import com.tri.erp.spring.response.GeneralLedgerLineDto2;
import com.tri.erp.spring.response.SubLedgerDto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by nsutgio2015 on 5/19/2015.
 */
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class SalesVoucher extends Voucher {

    @NotNull(message = "Please enter particulars.")
    @Column
    private String particulars;

    public SalesVoucher() {
    }

    public SalesVoucher(String particulars) {
        this.particulars = particulars;
    }

    public String getParticulars() {
        return particulars;
    }

    public void setParticulars(String particulars) {
        this.particulars = particulars;
    }
}
