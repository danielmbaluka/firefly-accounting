package com.tri.erp.spring.dtoers;

import com.tri.erp.spring.repo.DocumentWorkflowActionMapRepo;
import com.tri.erp.spring.repo.DocumentWorkflowLogRepo;
import com.tri.erp.spring.response.WorkflowActionsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TSI Admin on 5/5/2015.
 */

@Component
public class WorkflowDtoerImpl implements WorkflowDtoer {

    @Autowired
    DocumentWorkflowLogRepo wfRepo;

    @Autowired
    DocumentWorkflowActionMapRepo dwfMapRepo;
    
    @Override
    public List<WorkflowActionsDto> getWorkflowActionsDtoByWfId(Integer transId) {
        List<WorkflowActionsDto> workflowActionsDtos = new ArrayList<>();

        // check if document reached last action
        List<Object[]> ts = wfRepo.findLogTopSequenceTransactionId(transId);
        if (ts == null || ts.size() == 0) { // not: approved, closed, or denied

            List<Object[]> wfLog = wfRepo.findLatestDocumentLogByTransactionId(transId);

            if (wfLog != null && wfLog.size() > 0) {
                Object[] obj = wfLog.get(0);

                Integer workflowId = (Integer) obj[0];
                Integer sequence = (Integer) obj[1];
                List<Object[]> actions = dwfMapRepo.getActionsByWorkflowIdAndSequence(workflowId, sequence);

                for (Object[] a : actions) {
                    WorkflowActionsDto dto = new WorkflowActionsDto();
                    dto.setActionMapId((Integer)a[0]);
                    dto.setAction(String.valueOf(a[1]));

                    workflowActionsDtos.add(dto);
                }
            }
        }
        return workflowActionsDtos;
    }
}
