package com.tri.erp.spring.repo;

import com.tri.erp.spring.model.TemporaryGeneralLedger;
import com.tri.erp.spring.model.TemporarySubLedger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by TSI Admin on 7/7/2015.
 */
public interface TemporarySubLedgerRepo extends JpaRepository<TemporarySubLedger, Integer> {

    @Query(value = "SELECT " +
            "TemporarySubLedger.id, " +
            "TemporarySubLedger.FK_accountNo, " +
            "slentity.name, " +
            "SegmentAccount.FK_accountId, " +
            "SUM(COALESCE(TemporarySubLedger.debit,0) + COALESCE(TemporarySubLedger.credit, 0)) AS amount " +
            "FROM TemporarySubLedger " +
            "JOIN SegmentAccount ON TemporarySubLedger.FK_segmentAccountId = SegmentAccount.id " +
            "JOIN slentity ON TemporarySubLedger.FK_accountNo = slentity.accountNo " +
            "WHERE TemporarySubLedger.FK_temporaryBatchId = :tempBatchId " +
            "AND SegmentAccount.FK_accountId = :accountId " +
            "GROUP BY TemporarySubLedger.FK_accountNo", nativeQuery = true)
    public List<Object[]> findByTempBatcIdAndAccountId(@Param("tempBatchId") Integer tempBatchId, @Param("accountId") Integer accountId);

}
