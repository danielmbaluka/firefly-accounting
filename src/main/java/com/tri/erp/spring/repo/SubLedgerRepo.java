package com.tri.erp.spring.repo;

import com.tri.erp.spring.model.SubLedger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by TSI Admin on 9/9/2014.
 */
public interface SubLedgerRepo extends JpaRepository<SubLedger, Integer> {
    public List<SubLedger> findByGeneralLedgerId(Integer glId);
    public List<SubLedger> findByTransactionId(Integer transId);
    public Long deleteByTransactionId(Integer transId);

    @Query(value = "select " +
            "SubLedger.id, " +
            "SubLedger.FK_accountNo, " +
            "slentity.name, " +
            "SegmentAccount.FK_accountId, " +
            "SUM(SubLedger.debit + SubLedger.credit) AS amount " +
            "from SubLedger " +
            "JOIN SegmentAccount ON SubLedger.FK_segmentAccountId = SegmentAccount.id " +
            "JOIN slentity ON SubLedger.FK_accountNo = slentity.accountNo " +
            "where SubLedger.FK_transactionId = :transId " +
            "AND SegmentAccount.FK_accountId = :accountId " +
            "GROUP BY SubLedger.FK_accountNo", nativeQuery = true)
    public List<Object[]> findByTransactionIdAndAccountId(@Param("transId") Integer transId, @Param("accountId") Integer accountId);

    @Query(value = "select " +
            "SubLedger.id, " +
            "SubLedger.FK_accountNo, " +
            "slentity.name, " +
            "SegmentAccount.FK_accountId, " +
            "SUM(SubLedger.debit + SubLedger.credit) AS amount " +
            "from SubLedger " +
            "JOIN SegmentAccount ON SubLedger.FK_segmentAccountId = SegmentAccount.id " +
            "JOIN slentity ON SubLedger.FK_accountNo = slentity.accountNo " +
            "where SubLedger.FK_transactionId = :transId " +
            "GROUP BY SubLedger.FK_accountNo", nativeQuery = true)
    public List<Object[]> findByTransactionIdNative(@Param("transId") Integer transId);
}
