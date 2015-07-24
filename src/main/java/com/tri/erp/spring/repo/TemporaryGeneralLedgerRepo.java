package com.tri.erp.spring.repo;

import com.tri.erp.spring.model.TemporaryGeneralLedger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by TSI Admin on 7/7/2015.
 */
public interface TemporaryGeneralLedgerRepo extends JpaRepository<TemporaryGeneralLedger, Integer> {

    @Query(value = "SELECT " +
            "debit, " +
            "credit, " +
            "Account.id, " +
            "Account.code, " +
            "Account.title " +
            "FROM(SELECT " +
            "SegmentAccount.FK_accountId as accountId, " +
            "SUM(COALESCE(TemporaryGeneralLedger.debit,0)) AS debit, " +
            "SUM(COALESCE(TemporaryGeneralLedger.credit,0)) AS credit " +
            "FROM TemporaryGeneralLedger " +
            "JOIN SegmentAccount ON TemporaryGeneralLedger.FK_segmentAccountId = SegmentAccount.id " +
            "WHERE TemporaryGeneralLedger.FK_temporaryBatchId = :tempBatchId " +
            "GROUP BY SegmentAccount.FK_accountId " +
            "ORDER BY TemporaryGeneralLedger.id) as gl  " +
            "JOIN Account ON gl.accountId = Account.id", nativeQuery = true)
    public List<Object[]> findByBatchId(@Param("tempBatchId") Integer tempBatchId);
}
