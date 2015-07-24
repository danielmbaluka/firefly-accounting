package com.tri.erp.spring.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tri.erp.spring.response.GeneralLedgerLineDto2;
import com.tri.erp.spring.response.SubLedgerDto;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by TSI Admin on 2/26/2015.
 */

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class JournalVoucher extends Voucher {

    @NotNull(message = "Please enter explanation.")
    @Column
    private String explanation;

    @Column
    private String remarks;

    @NotNull(message = "Please select recommending officer.")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_recommendedByUserId")
    private User recommendingOfficer;

    @NotNull(message = "Please select auditor")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_auditedByUserId")
    private User auditor;

    @Transient
    private Integer tempBatchId;

    public JournalVoucher() {}

    public JournalVoucher(Date voucherDate, Integer year, BigDecimal amount, User checker, List<GeneralLedgerLineDto2> generalLedgerLines, List<SubLedgerDto> subLedgerLines, String explanation, String remarks, User recommendingOfficer, User auditor, Integer tempBatchId) {
        super(voucherDate, year, amount, checker, generalLedgerLines, subLedgerLines);
        this.explanation = explanation;
        this.remarks = remarks;
        this.recommendingOfficer = recommendingOfficer;
        this.auditor = auditor;
        this.tempBatchId = tempBatchId;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public User getRecommendingOfficer() {
        return recommendingOfficer;
    }

    public void setRecommendingOfficer(User recommendingOfficer) {
        this.recommendingOfficer = recommendingOfficer;
    }

    public User getAuditor() {
        return auditor;
    }

    public void setAuditor(User auditor) {
        this.auditor = auditor;
    }

    public Integer getTempBatchId() {
        return tempBatchId;
    }

    public void setTempBatchId(Integer tempBatchId) {
        this.tempBatchId = tempBatchId;
    }
}
