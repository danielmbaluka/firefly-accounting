package com.tri.erp.spring.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by TSI Admin on 5/5/2015.
 */

@Entity
public class DocumentWorkflowLog implements Serializable {

    @Id
    @GeneratedValue
    @Column
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "FK_documentTransactionId")
    private Transaction transaction;

    @ManyToOne
    @JoinColumn(name = "FK_documentWorkflowActionMapId")
    private DocumentWorkflowActionMap documentWorkflowActionMap;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date executedAt;

    public DocumentWorkflowLog() {}

    public DocumentWorkflowLog(Transaction transaction, DocumentWorkflowActionMap documentWorkflowActionMap, Date executedAt) {
        this.transaction = transaction;
        this.documentWorkflowActionMap = documentWorkflowActionMap;
        this.executedAt = executedAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public DocumentWorkflowActionMap getDocumentWorkflowActionMap() {
        return documentWorkflowActionMap;
    }

    public void setDocumentWorkflowActionMap(DocumentWorkflowActionMap documentWorkflowActionMap) {
        this.documentWorkflowActionMap = documentWorkflowActionMap;
    }

    public Date getExecutedAt() {
        return executedAt;
    }

    public void setExecutedAt(Date executedAt) {
        this.executedAt = executedAt;
    }
}
