package com.tri.erp.spring.repo;

import com.tri.erp.spring.model.TemporaryBatch;
import com.tri.erp.spring.model.TemporaryGeneralLedger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by TSI Admin on 7/7/2015.
 */
public interface TemporaryBatchRepo extends JpaRepository<TemporaryBatch, Integer> {

    @Transactional(readOnly = true)
    @Query(value = "SELECT " +
            "TemporaryBatch.id AS tempBatchId, " +
            "TemporaryBatch.`date` AS tempBatchDate, " +
            "TemporaryBatch.remarks AS remarks, " +
            "DocumentType.id AS docTypeId, " +
            "DocumentType.description AS docTypeDesc, " +
            "TemporaryGeneralLedger.id AS tempGLId, " +
            "SUM(TemporaryGeneralLedger.debit) as amount " +
            "FROM TemporaryBatch " +
            "JOIN DocumentType ON TemporaryBatch.FK_documentTypeId = DocumentType.id " +
            "JOIN TemporaryGeneralLedger ON TemporaryBatch.id = TemporaryGeneralLedger.FK_temporaryBatchId " +
            "WHERE  TemporaryBatch.voucherCreated = 0 " +
            "GROUP BY TemporaryGeneralLedger.FK_temporaryBatchId " +
            "ORDER BY TemporaryBatch.`date` ASC", nativeQuery = true)
    public List<Object[]> findAllWithAmount();
}
