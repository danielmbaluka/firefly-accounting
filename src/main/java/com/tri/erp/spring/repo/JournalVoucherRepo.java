package com.tri.erp.spring.repo;

import com.tri.erp.spring.model.JournalVoucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by TSI Admin on 6/30/2015.
 */
public interface JournalVoucherRepo extends JpaRepository<JournalVoucher, Integer> {
    @Query(value = "SELECT e.code FROM JournalVoucher e WHERE year = :year ORDER BY id DESC LIMIT 1", nativeQuery = true)
    public Object findLatestVvCodeByYear(@Param("year") Integer year);

    @Query(value = "SELECT " +
            "JournalVoucher.id, " +
            "JournalVoucher.code, " +
            "JournalVoucher.explanation, " +
            "JournalVoucher.voucherDate, " +
            "JournalVoucher.FK_transactionId " +
            "FROM JournalVoucher " +
            "WHERE JournalVoucher.voucherDate >= :from AND JournalVoucher.voucherDate <= :to " +
            "AND JournalVoucher.FK_documentStatusId = :documentStatusId " +
            "ORDER BY voucherDate DESC", nativeQuery = true)
    public List<Object[]> findForRegisterByDateRange(@Param("from") String from, @Param("to") String to,  @Param("documentStatusId") Integer documentStatusId);

    @Query(value = "SELECT  " +
            "generals.glDebit,  " +
            "generals.glCredit,  " +
            "generals.glId,  " +
            "COALESCE(subs.slDebit, 0) slDebit,  " +
            "COALESCE(subs.slCredit, 0) slCredit,  " +
            "subs.accountNo,  " +
            "SegmentAccount.accountCode,  " +
            "Account.title,  " +
            "slentity.name,  " +
            "if (glDebit-glCredit > 0 , 1, 2) as side  " +
            "FROM (SELECT " +
            "GeneralLedger.id as glId," +
            "GeneralLedger.FK_segmentAccountId as glSegmentAccountId," +
            "IF(SUM(COALESCE(GeneralLedger.debit, 0))-SUM(COALESCE(GeneralLedger.credit, 0)) > 0, SUM(COALESCE(GeneralLedger.debit, 0))-SUM(COALESCE(GeneralLedger.credit, 0)), 0) AS glDebit," +
            "IF(SUM(COALESCE(GeneralLedger.debit, 0))-SUM(COALESCE(GeneralLedger.credit, 0)) > 0, 0, SUM(COALESCE(GeneralLedger.credit, 0))-SUM(COALESCE(GeneralLedger.debit, 0))) AS glCredit  " +
            "FROM GeneralLedger " +
            "JOIN JournalVoucher on GeneralLedger.FK_transactionId = JournalVoucher.FK_transactionId " +
            "WHERE (JournalVoucher.voucherDate >= :from AND JournalVoucher.voucherDate <= :to) AND JournalVoucher.FK_documentStatusId = :documentStatusId " +
            "GROUP BY GeneralLedger.FK_segmentAccountId) as generals  " +
            "LEFT JOIN (SELECT " +
            "SubLedger.FK_generalLedgerLineId AS slGLId, " +
            "SubLedger.FK_segmentAccountId as slSegmentAccountId," +
            "SubLedger.FK_accountNo as accountNo," +
            "IF(SUM(COALESCE(SubLedger.debit, 0))-SUM(COALESCE(SubLedger.credit, 0)) > 0, SUM(COALESCE(SubLedger.debit, 0))-SUM(COALESCE(SubLedger.credit, 0)), 0) AS slDebit, " +
            "IF(SUM(COALESCE(SubLedger.debit, 0))-SUM(COALESCE(SubLedger.credit, 0)) > 0, 0, SUM(COALESCE(SubLedger.credit, 0))-SUM(COALESCE(SubLedger.debit, 0))) AS slCredit  " +
            "FROM SubLedger " +
            "JOIN JournalVoucher on SubLedger.FK_transactionId = JournalVoucher.FK_transactionId " +
            "WHERE (JournalVoucher.voucherDate >= :from AND JournalVoucher.voucherDate <= :to) AND JournalVoucher.FK_documentStatusId = :documentStatusId " +
            "GROUP BY SubLedger.FK_segmentAccountId, SubLedger.FK_accountNo) as subs  " +
            "ON generals.glSegmentAccountId = subs.slSegmentAccountId  " +
            "JOIN SegmentAccount ON generals.glSegmentAccountId = SegmentAccount.id  " +
            "JOIN Account ON SegmentAccount.FK_accountId = Account.id  " +
            "LEFT JOIN slentity ON  subs.accountNo = slentity.accountNo " +
            "ORDER BY side, code", nativeQuery = true)
    public List<Object[]> findForRegisterRecapByDateRange(@Param("from") String from, @Param("to") String to,  @Param("documentStatusId") Integer documentStatusId);


}