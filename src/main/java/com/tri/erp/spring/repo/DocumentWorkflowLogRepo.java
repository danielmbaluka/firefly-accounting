package com.tri.erp.spring.repo;

import com.tri.erp.spring.model.DocumentWorkflowActionMap;
import com.tri.erp.spring.model.DocumentWorkflowLog;
import com.tri.erp.spring.model.SlEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by TSI Admin on 5/5/2015.
 */
public interface DocumentWorkflowLogRepo extends JpaRepository<DocumentWorkflowLog, Integer> {

    @Transactional(readOnly = true)
    @Query(value = "SELECT " +
            "FK_workflowId, " +
            "sequence " +
            "from DocumentWorkflowLog " +
            "JOIN DocumentWorkflowActionMap ON DocumentWorkflowActionMap.id = DocumentWorkflowLog.FK_documentWorkflowActionMapId " +
            "where DocumentWorkflowLog.FK_documentTransactionId = :transId " +
            "ORDER BY DocumentWorkflowLog.id DESC LIMIT 1", nativeQuery = true)
    public List<Object[]> findLatestDocumentLogByTransactionId(@Param("transId") Integer transId);

    @Transactional(readOnly = true)
    @Query(value = "SELECT " +
            "FK_workflowId, " +
            "sequence " +
            "FROM DocumentWorkflowLog  " +
            "JOIN DocumentWorkflowActionMap ON DocumentWorkflowLog.FK_documentWorkflowActionMapId = DocumentWorkflowActionMap.id " +
            "WHERE FK_documentTransactionId = :transId " +
            "AND sequence = (SELECT MAX(sequence) as maxSequence FROM DocumentWorkflowActionMap) " +
            "ORDER BY DocumentWorkflowLog.id DESC LIMIT 1", nativeQuery = true)
    public List<Object[]> findLogTopSequenceTransactionId(@Param("transId") Integer transId);
}
