package com.tri.erp.spring.commons.facade;

import com.tri.erp.spring.model.*;
import com.tri.erp.spring.repo.DocumentWorkflowActionMapRepo;
import com.tri.erp.spring.repo.DocumentWorkflowLogRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by TSI Admin on 5/6/2015.
 */

@Component
public class DocumentProcessingFacadeImpl implements DocumentProcessingFacade {

    @Autowired
    DocumentWorkflowLogRepo documentWorkflowLogRepo;

    @Autowired
    DocumentWorkflowActionMapRepo documentWorkflowActionMapRepo;

    @Override
    public DocumentWorkflowLog processAction(Transaction transaction, DocumentWorkflowActionMap actionMap, Workflow workflow, User executedBy) {
        if (actionMap == null) {
            List<DocumentWorkflowActionMap> workflowActionMaps = documentWorkflowActionMapRepo.findDocumentCreatedAndWorkflowId(workflow.getId());
            if (workflowActionMaps != null && workflowActionMaps.size() > 0) {
                actionMap = workflowActionMaps.get(0);
            }
        }

        DocumentWorkflowLog documentWorkflowLog = new DocumentWorkflowLog();
        documentWorkflowLog.setTransaction(transaction);
        documentWorkflowLog.setDocumentWorkflowActionMap(actionMap);

        return documentWorkflowLogRepo.save(documentWorkflowLog);
    }
}
