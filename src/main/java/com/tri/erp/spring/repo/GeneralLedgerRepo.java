package com.tri.erp.spring.repo;

import com.tri.erp.spring.model.GeneralLedger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by TSI Admin on 9/9/2014.
 */
public interface GeneralLedgerRepo extends JpaRepository<GeneralLedger, Integer> {
    public List<GeneralLedger> findByTransactionId(Integer transId);
    
    @Query(value = "SELECT " +
            "debit, " +
            "credit, " +
            "Account.id, " +
            "Account.code, " +
            "Account.title " +
            "FROM(SELECT " +
            "SegmentAccount.FK_accountId as accountId, " +
            "SUM(GeneralLedger.debit) AS debit, " +
            "SUM(GeneralLedger.credit) AS credit " +
            "FROM GeneralLedger " +
            "JOIN SegmentAccount ON GeneralLedger.FK_segmentAccountId = SegmentAccount.id " +
            "WHERE GeneralLedger.FK_transactionId = :transId " +
            "GROUP BY SegmentAccount.FK_accountId " +
            "ORDER BY GeneralLedger.id) as gl  " +
            "JOIN Account ON gl.accountId = Account.id", nativeQuery = true)
    public List<Object[]> findByTransactionIdGroupByAccount(@Param("transId") Integer transId);
    public Long deleteByTransactionId(Integer transId);

    @Query(value = "SELECT " +
            "GeneralLedger.*, " +
            "BusinessSegment.description " +
            "FROM GeneralLedger " +
            "JOIN SegmentAccount ON GeneralLedger.FK_segmentAccountId = SegmentAccount.id " +
            "JOIN BusinessSegment ON SegmentAccount.FK_businessSegmentId = BusinessSegment.id " +
            "WHERE GeneralLedger.FK_transactionId = :transId " +
            "AND SegmentAccount.FK_accountId = :accountId", nativeQuery = true)
    public List<Object[]> findByTransactionIdAndAccount(@Param("transId") Integer transId, @Param("accountId") Integer accountId);

    @Query(value = "SELECT " +
            "SegmentAccount.accountCode, " +
            "COALESCE(GeneralLedger.debit, 0) debit, " +
            "COALESCE(GeneralLedger.credit, 0) credit " +
            "FROM GeneralLedger " +
            "JOIN SegmentAccount ON GeneralLedger.FK_segmentAccountId = SegmentAccount.id " +
            "WHERE GeneralLedger.FK_transactionId = :transId " +
            "ORDER BY GeneralLedger.id ASC", nativeQuery = true)
    public List<Object[]> findForRegisterByTransId(@Param("transId") Integer transId);
}
