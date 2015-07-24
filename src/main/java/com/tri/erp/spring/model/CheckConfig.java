package com.tri.erp.spring.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by TSI Admin on 12/8/2014.
 */

@Entity
public class CheckConfig {
    @Id
    @GeneratedValue
    @Column
    private Integer id;

    @Column(nullable = false)
    String code;

    @Column(nullable = false)
    Integer dateX;

    @Column(nullable = false)
    Integer dateY;

    @Column(nullable = false)
    Integer payeeX;

    @Column(nullable = false)
    Integer payeeY;

    @Column(nullable = false)
    Integer payeeW;

    @Column(nullable = false)
    Integer numericAmountX;

    @Column(nullable = false)
    Integer numericAmountY;

    @Column(nullable = false)
    Integer alphaAmountX;

    @Column(nullable = false)
    Integer alphaAmountY;

    @Column(nullable = false)
    Integer alphaAmountW;

    @Column(nullable = false)
    Integer sig1X;

    @Column(nullable = false)
    Integer sig1Y;

    @Column(nullable = false)
    Integer sig2X;

    @Column(nullable = false)
    Integer sig2Y;

    @Column(nullable = false)
    Integer checkNoX;

    @Column(nullable = false)
    Integer checkNoY;

    @Column(nullable = false)
    String checkNoPrefix;

    @Column(nullable = false)
    Boolean withSigner;

    @Column(nullable = false)
    Boolean showDesignation;

    @JsonIgnoreProperties(ignoreUnknown = true)
    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="FK_bankAccountId", nullable = true, columnDefinition = "0")
    private SegmentAccount bankSegmentAccount;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date updatedAt;

    public CheckConfig() {}

    public CheckConfig(String code, Integer dateX, Integer dateY, Integer payeeX, Integer payeeY, Integer payeeW,
                       Integer numericAmountX, Integer numericAmountY, Integer alphaAmountX, Integer alphaAmountY,
                       Integer alphaAmountW, Integer sig1X, Integer sig1Y, Integer sig2X, Integer sig2Y,
                       Integer checkNoX, Integer checkNoY, String checkNoPrefix, Boolean withSigner,
                       Boolean showDesignation, Date updatedAt, SegmentAccount bankSegmentAccount) {
        this.code = code;
        this.dateX = dateX;
        this.dateY = dateY;
        this.payeeX = payeeX;
        this.payeeY = payeeY;
        this.payeeW = payeeW;
        this.numericAmountX = numericAmountX;
        this.numericAmountY = numericAmountY;
        this.alphaAmountX = alphaAmountX;
        this.alphaAmountY = alphaAmountY;
        this.alphaAmountW = alphaAmountW;
        this.sig1X = sig1X;
        this.sig1Y = sig1Y;
        this.sig2X = sig2X;
        this.sig2Y = sig2Y;
        this.checkNoX = checkNoX;
        this.checkNoY = checkNoY;
        this.checkNoPrefix = checkNoPrefix;
        this.withSigner = withSigner;
        this.showDesignation = showDesignation;
        this.updatedAt = updatedAt;
        this.bankSegmentAccount = bankSegmentAccount;
    }

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

    public Integer getDateX() {
        return dateX;
    }

    public void setDateX(Integer dateX) {
        this.dateX = dateX;
    }

    public Integer getDateY() {
        return dateY;
    }

    public void setDateY(Integer dateY) {
        this.dateY = dateY;
    }

    public Integer getPayeeX() {
        return payeeX;
    }

    public void setPayeeX(Integer payeeX) {
        this.payeeX = payeeX;
    }

    public Integer getPayeeY() {
        return payeeY;
    }

    public void setPayeeY(Integer payeeY) {
        this.payeeY = payeeY;
    }

    public Integer getPayeeW() {
        return payeeW;
    }

    public void setPayeeW(Integer payeeW) {
        this.payeeW = payeeW;
    }

    public Integer getNumericAmountX() {
        return numericAmountX;
    }

    public void setNumericAmountX(Integer numericAmountX) {
        this.numericAmountX = numericAmountX;
    }

    public Integer getNumericAmountY() {
        return numericAmountY;
    }

    public void setNumericAmountY(Integer numericAmountY) {
        this.numericAmountY = numericAmountY;
    }

    public Integer getAlphaAmountX() {
        return alphaAmountX;
    }

    public void setAlphaAmountX(Integer alphaAmountX) {
        this.alphaAmountX = alphaAmountX;
    }

    public Integer getAlphaAmountY() {
        return alphaAmountY;
    }

    public void setAlphaAmountY(Integer alphaAmountY) {
        this.alphaAmountY = alphaAmountY;
    }

    public Integer getAlphaAmountW() {
        return alphaAmountW;
    }

    public void setAlphaAmountW(Integer alphaAmountW) {
        this.alphaAmountW = alphaAmountW;
    }

    public Integer getSig1X() {
        return sig1X;
    }

    public void setSig1X(Integer sig1X) {
        this.sig1X = sig1X;
    }

    public Integer getSig1Y() {
        return sig1Y;
    }

    public void setSig1Y(Integer sig1Y) {
        this.sig1Y = sig1Y;
    }

    public Integer getSig2X() {
        return sig2X;
    }

    public void setSig2X(Integer sig2X) {
        this.sig2X = sig2X;
    }

    public Integer getSig2Y() {
        return sig2Y;
    }

    public void setSig2Y(Integer sig2Y) {
        this.sig2Y = sig2Y;
    }

    public Integer getCheckNoX() {
        return checkNoX;
    }

    public void setCheckNoX(Integer checkNoX) {
        this.checkNoX = checkNoX;
    }

    public Integer getCheckNoY() {
        return checkNoY;
    }

    public void setCheckNoY(Integer checkNoY) {
        this.checkNoY = checkNoY;
    }

    public String getCheckNoPrefix() {
        return checkNoPrefix;
    }

    public void setCheckNoPrefix(String checkNoPrefix) {
        this.checkNoPrefix = checkNoPrefix;
    }

    public Boolean getWithSigner() {
        return withSigner;
    }

    public void setWithSigner(Boolean withSigner) {
        this.withSigner = withSigner;
    }

    public Boolean getShowDesignation() {
        return showDesignation;
    }

    public void setShowDesignation(Boolean showDesignation) {
        this.showDesignation = showDesignation;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public SegmentAccount getBankSegmentAccount() {
        return bankSegmentAccount;
    }

    public void setBankSegmentAccount(SegmentAccount bankSegmentAccount) {
        this.bankSegmentAccount = bankSegmentAccount;
    }
}
