package com.tri.erp.spring.repo;

import com.tri.erp.spring.model.DocumentWorkflowActionMap;
import com.tri.erp.spring.model.DocumentWorkflowLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by TSI Admin on 5/5/2015.
 */
public interface DocumentWorkflowActionMapRepo extends JpaRepository<DocumentWorkflowActionMap, Integer> {

    @Transactional(readOnly = true)
    @Query(value = "select " +
            "DocumentWorkflowActionMap.id as actionMapId, " +
            "WorkflowAction.`action` as 'action' " +
            "from DocumentWorkflowActionMap " +
            "JOIN WorkflowAction ON FK_workflowActionId = WorkflowAction.id " +
            "WHERE FK_workflowId = :wfId  " +
            "AND sequence < :sequence " +
            "AND FK_beforeActionDocumentStatusId > 0 " +
            "UNION  " +
            "SELECT * FROM(select " +
            "DocumentWorkflowActionMap.id as actionMapId, " +
            "WorkflowAction.`action` as 'action' " +
            "from DocumentWorkflowActionMap " +
            "JOIN WorkflowAction ON FK_workflowActionId = WorkflowAction.id " +
            "WHERE FK_workflowId = :wfId  " +
            "AND sequence = (:sequence + 1) " +
            "AND FK_beforeActionDocumentStatusId > 0 " +
            "ORDER BY sequence ASC) as forwardActions", nativeQuery = true)
    public List<Object[]> getActionsByWorkflowIdAndSequence(@Param("wfId") Integer wfId, @Param("sequence") Integer sequence);

    @Transactional(readOnly = true)
    @Query(value = "SELECT e FROM DocumentWorkflowActionMap e " +
            "WHERE FK_workflowId = :workflowId " +
            "AND FK_beforeActionDocumentStatusId = 0")
    public List<DocumentWorkflowActionMap> findDocumentCreatedAndWorkflowId(@Param("workflowId") Integer workflowId);
}
