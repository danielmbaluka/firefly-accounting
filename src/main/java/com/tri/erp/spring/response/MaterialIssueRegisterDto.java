package com.tri.erp.spring.response;

import com.tri.erp.spring.model.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by nsutgio2015 on 4/27/2015.
 */
public class MaterialIssueRegisterDto implements Serializable {

    private Integer id;
    private SlEntity vendor;
    private String particulars;
    private Date voucherDate;
    private Date dueDate;
    private String localCode;
    private SlEntity sLChecker;
    private SlEntity sLApprovingOfficer;
    private User createdBy;
    private User checker;
    private User approvingOfficer;
    private Integer transId;
    private BigDecimal amount;
    private String status;
    private Date lastUpdated;
    private Date created;

    private ArrayList<GeneralLedgerLineDto2> generalLedgerLines;
    private ArrayList<SubLedgerDto> subLedgerLines;
    private ArrayList<MaterialIssueRegisterDetailDto> materialIssueRegisterDetails;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public SlEntity getVendor() {
        return vendor;
    }

    public void setVendor(SlEntity vendor) {
        this.vendor = vendor;
    }

    public String getParticulars() {
        return particulars;
    }

    public void setParticulars(String particulars) {
        this.particulars = particulars;
    }

    public Date getVoucherDate() {
        return voucherDate;
    }

    public void setVoucherDate(Date voucherDate) {
        this.voucherDate = voucherDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public String getLocalCode() {
        return localCode;
    }

    public void setLocalCode(String localCode) {
        this.localCode = localCode;
    }

    public SlEntity getsLChecker() {
        return sLChecker;
    }

    public void setsLChecker(SlEntity sLChecker) {
        this.sLChecker = sLChecker;
    }

    public SlEntity getsLApprovingOfficer() {
        return sLApprovingOfficer;
    }

    public void setsLApprovingOfficer(SlEntity sLApprovingOfficer) {
        this.sLApprovingOfficer = sLApprovingOfficer;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public User getChecker() {
        return checker;
    }

    public void setChecker(User checker) {
        this.checker = checker;
    }

    public User getApprovingOfficer() {
        return approvingOfficer;
    }

    public void setApprovingOfficer(User approvingOfficer) {
        this.approvingOfficer = approvingOfficer;
    }

    public Integer getTransId() {
        return transId;
    }

    public void setTransId(Integer transId) {
        this.transId = transId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public ArrayList<GeneralLedgerLineDto2> getGeneralLedgerLines() {
        return generalLedgerLines;
    }

    public void setGeneralLedgerLines(ArrayList<GeneralLedgerLineDto2> generalLedgerLines) {
        this.generalLedgerLines = generalLedgerLines;
    }

    public ArrayList<SubLedgerDto> getSubLedgerLines() {
        return subLedgerLines;
    }

    public void setSubLedgerLines(ArrayList<SubLedgerDto> subLedgerLines) {
        this.subLedgerLines = subLedgerLines;
    }

    public ArrayList<MaterialIssueRegisterDetailDto> getMaterialIssueRegisterDetails() {
        return materialIssueRegisterDetails;
    }

    public void setMaterialIssueRegisterDetails(ArrayList<MaterialIssueRegisterDetailDto> materialIssueRegisterDetails) {
        this.materialIssueRegisterDetails = materialIssueRegisterDetails;
    }

    public MaterialIssueRegister toModel(){

        MaterialIssueRegister ret = new MaterialIssueRegister();
        List<MaterialIssueRegisterDetail> details = new ArrayList<>();

        ret.setId(this.getId());
        ret.setVoucherDate(this.getVoucherDate());
        ret.setParticulars(this.getParticulars());
        ret.setCreatedAt(this.getCreated());
        ret.setUpdatedAt(this.getLastUpdated());
        ret.setCreatedBy(this.getCreatedBy());
        ret.setApprovingOfficer(this.getApprovingOfficer());
        ret.setChecker(this.getChecker());
        ret.setCode(this.getLocalCode());
        ret.setGeneralLedgerLines(this.getGeneralLedgerLines());
        ret.setSubLedgerLines(this.getSubLedgerLines());

        for(MaterialIssueRegisterDetailDto d : this.getMaterialIssueRegisterDetails()){

            MaterialIssueRegisterDetail md = new MaterialIssueRegisterDetail();

            md.setUnitPrice(d.getUnitPrice());
            md.setQuantity(d.getQuantity());
            md.setDescription(d.getDescription());
            md.setAmount(d.getAmount());
            md.setId(d.getId());

            details.add(md);
        }

        ret.setMaterialIssueRegisterDetails(details);

        return ret;
    }
}
