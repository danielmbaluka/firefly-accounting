package com.tri.erp.spring.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by TSI Admin on 5/7/2015.
 */

@MappedSuperclass
@JsonIgnoreProperties(ignoreUnknown = true)
public class Document implements Serializable {

    @Id
    @GeneratedValue
    @Column
    private Integer id;

    @Column
    private String code;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "FK_transactionId")
    private Transaction transaction;

    @NotFound(action = NotFoundAction.IGNORE)
    @NotNull(message = "Please select approving officer.")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "FK_approvedByUserId")
    private User approvingOfficer;

    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_workflowId")
    private Workflow workflow;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date updatedAt;

    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="FK_createdByUserId", nullable = true, columnDefinition = "0")
    private User createdBy;

    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="FK_documentStatusId", nullable = true, columnDefinition = "0")
    private DocumentStatus documentStatus;

    public Document() {}

    public Document(String code, Transaction transaction, User approvingOfficer, Workflow workflow, Date createdAt, Date updatedAt, User createdBy, DocumentStatus documentStatus) {
        this.code = code;
        this.transaction = transaction;
        this.approvingOfficer = approvingOfficer;
        this.workflow = workflow;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.createdBy = createdBy;
        this.documentStatus = documentStatus;
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

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public User getApprovingOfficer() {
        return approvingOfficer;
    }

    public void setApprovingOfficer(User approvingOfficer) {
        this.approvingOfficer = approvingOfficer;
    }

    public Workflow getWorkflow() {
        return workflow;
    }

    public void setWorkflow(Workflow workflow) {
        this.workflow = workflow;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public DocumentStatus getDocumentStatus() {
        return documentStatus;
    }

    public void setDocumentStatus(DocumentStatus documentStatus) {
        this.documentStatus = documentStatus;
    }
}
