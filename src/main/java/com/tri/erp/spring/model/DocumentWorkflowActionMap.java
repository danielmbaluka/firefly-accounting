package com.tri.erp.spring.model;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by TSI Admin on 5/5/2015.
 */

@Entity
public class DocumentWorkflowActionMap implements Serializable {
    @Id
    @GeneratedValue
    @Column
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "FK_workflowId")
    private Workflow workflow;

    @ManyToOne
    @JoinColumn(name = "FK_workflowActionId")
    private WorkflowAction workflowAction;

    @Column
    private Integer sequence;

    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="FK_beforeActionDocumentStatusId")
    private DocumentStatus beforeActionDocumentStatus;

    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="FK_afterActionDocumentStatusId")
    private DocumentStatus afterActionDocumentStatus;

    public DocumentWorkflowActionMap() {}

    public DocumentWorkflowActionMap(Workflow workflow, WorkflowAction workflowAction, Integer sequence,
                                     DocumentStatus beforeActionDocumentStatus, DocumentStatus afterActionDocumentStatus) {
        this.workflow = workflow;
        this.workflowAction = workflowAction;
        this.sequence = sequence;
        this.beforeActionDocumentStatus = beforeActionDocumentStatus;
        this.afterActionDocumentStatus = afterActionDocumentStatus;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Workflow getWorkflow() {
        return workflow;
    }

    public void setWorkflow(Workflow workflow) {
        this.workflow = workflow;
    }

    public WorkflowAction getWorkflowAction() {
        return workflowAction;
    }

    public void setWorkflowAction(WorkflowAction workflowAction) {
        this.workflowAction = workflowAction;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public DocumentStatus getBeforeActionDocumentStatus() {
        return beforeActionDocumentStatus;
    }

    public void setBeforeActionDocumentStatus(DocumentStatus beforeActionDocumentStatus) {
        this.beforeActionDocumentStatus = beforeActionDocumentStatus;
    }

    public DocumentStatus getAfterActionDocumentStatus() {
        return afterActionDocumentStatus;
    }

    public void setAfterActionDocumentStatus(DocumentStatus afterActionDocumentStatus) {
        this.afterActionDocumentStatus = afterActionDocumentStatus;
    }
}
