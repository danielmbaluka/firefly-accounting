package com.tri.erp.spring.model;

import com.tri.erp.spring.response.GeneralLedgerLineDto;
import com.tri.erp.spring.response.GeneralLedgerLineDto2;
import com.tri.erp.spring.response.SubLedgerDto;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nsutgio2015 on 4/27/2015.
 */
@Entity
public class MaterialIssueRegister  extends Voucher implements Serializable {

    @NotNull(message = "Please enter particulars.")
    @Column
    private String particulars;

    @Transient
    private List<MaterialIssueRegisterDetail> materialIssueRegisterDetails;

    public MaterialIssueRegister() {}

    public MaterialIssueRegister(String particulars) {
        this.particulars = particulars;
    }

    public MaterialIssueRegister(java.util.Date voucherDate, Integer year, BigDecimal amount, User checker, ArrayList<GeneralLedgerLineDto2> generalLedgerLines, ArrayList<SubLedgerDto> subLedgerLines, String particulars) {
        super(voucherDate, year, amount, checker, generalLedgerLines, subLedgerLines);
        this.particulars = particulars;
    }

    public String getParticulars() {
        return particulars;
    }

    public void setParticulars(String particulars) {
        this.particulars = particulars;
    }

    public List<MaterialIssueRegisterDetail> getMaterialIssueRegisterDetails() {
        return materialIssueRegisterDetails;
    }

    public void setMaterialIssueRegisterDetails(List<MaterialIssueRegisterDetail> materialIssueRegisterDetails) {
        this.materialIssueRegisterDetails = materialIssueRegisterDetails;
    }
}
