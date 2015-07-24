package com.tri.erp.spring.response;

/**
 * Created by TSI Admin on 5/6/2015.
 */
public class ProcessDocumentDto {
    private Integer transId;
    private Integer documentId;
    private WorkflowActionsDto workflowActionsDto;

    public ProcessDocumentDto() {}

    public ProcessDocumentDto(Integer transId, Integer documentId, WorkflowActionsDto workflowActionsDto) {
        this.transId = transId;
        this.documentId = documentId;
        this.workflowActionsDto = workflowActionsDto;
    }

    public Integer getTransId() {
        return transId;
    }

    public void setTransId(Integer transId) {
        this.transId = transId;
    }

    public Integer getDocumentId() {
        return documentId;
    }

    public void setDocumentId(Integer documentId) {
        this.documentId = documentId;
    }

    public WorkflowActionsDto getWorkflowActionsDto() {
        return workflowActionsDto;
    }

    public void setWorkflowActionsDto(WorkflowActionsDto workflowActionsDto) {
        this.workflowActionsDto = workflowActionsDto;
    }
}
