package com.tri.erp.spring.response;

/**
 * Created by TSI Admin on 5/5/2015.
 */
public class WorkflowActionsDto {
    private Integer actionMapId;
    private String action;

    public WorkflowActionsDto() {}

    public WorkflowActionsDto(Integer actionMapId, String action) {
        this.actionMapId = actionMapId;
        this.action = action;
    }

    public Integer getActionMapId() {
        return actionMapId;
    }

    public void setActionMapId(Integer actionMapId) {
        this.actionMapId = actionMapId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
