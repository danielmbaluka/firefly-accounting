package com.tri.erp.spring.dtoers;

import com.tri.erp.spring.response.WorkflowActionsDto;

import java.util.List;

/**
 * Created by TSI Admin on 5/3/2015.
 */
public interface WorkflowDtoer {
    public List<WorkflowActionsDto> getWorkflowActionsDtoByWfId(Integer transId);
}
