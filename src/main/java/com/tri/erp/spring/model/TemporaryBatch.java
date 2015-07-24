package com.tri.erp.spring.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.ManyToAny;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by TSI Admin on 7/6/2015.
 */

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class TemporaryBatch implements Serializable {
    @Id
    @GeneratedValue
    @Column
    private Integer id;

    @Column
    private Date date;

    @Column
    private Boolean voucherCreated;

    @ManyToOne
    @JoinColumn(name="FK_documentTypeId")
    private DocumentType documentType;

    public TemporaryBatch() {}

    public TemporaryBatch(Date date, Boolean voucherCreated, DocumentType documentType) {
        this.date = date;
        this.voucherCreated = voucherCreated;
        this.documentType = documentType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Boolean isVoucherCreated() {
        return voucherCreated;
    }

    public void setVoucherCreated(Boolean voucherCreated) {
        this.voucherCreated = voucherCreated;
    }

    public DocumentType getDocumentType() {
        return documentType;
    }

    public void setDocumentType(DocumentType documentType) {
        this.documentType = documentType;
    }
}
