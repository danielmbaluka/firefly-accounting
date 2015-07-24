package com.tri.erp.spring.commons.facade;

import com.tri.erp.spring.model.*;

/**
 * Created by TSI Admin on 5/6/2015.
 */
public interface DocumentProcessingFacade {
    public DocumentWorkflowLog processAction(Transaction transaction, DocumentWorkflowActionMap actionMap, Workflow workflow, User executedBy);
}
