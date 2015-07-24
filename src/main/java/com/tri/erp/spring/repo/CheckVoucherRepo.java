package com.tri.erp.spring.repo;

import com.tri.erp.spring.model.AccountsPayableVoucher;
import com.tri.erp.spring.model.CheckVoucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by TSI Admin on 4/21/2015.
 */
public interface CheckVoucherRepo extends JpaRepository<CheckVoucher, Integer> {
    @Query(value = "SELECT e.code FROM CheckVoucher e WHERE year = :year ORDER BY id DESC LIMIT 1", nativeQuery = true)
    public Object findLatestCvCodeByYear(@Param("year") Integer year);

    public CheckVoucher findOneByTransactionId(Integer transId);

    @Query(value = "SELECT " +
            "CheckVoucher.id, " +
            "CheckVoucher.FK_transactionId, " +
            "CheckVoucher.checkAmount, " +
            "CheckVoucher.particulars, " +
            "CheckVoucher.code, " +
            "CheckVoucher.voucherDate, " +
            "slentity.name, " +
            "CheckVoucher.remarks " +
            "FROM CheckVoucher " +
            "JOIN CheckVoucherCheque ON CheckVoucher.FK_transactionId = CheckVoucherCheque.FK_transactionId " +
            "JOIN slentity ON CheckVoucher.FK_payeeAccountNo = slentity.accountNo " +
            "WHERE CheckVoucher.FK_documentStatusId = 7 " +
            "AND CheckVoucherCheque.released = 0", nativeQuery = true)
    public List<Object[]> findAllForCheckReleasing();

    @Query(value = "SELECT " +
            "CheckVoucher.id, " +
            "CheckVoucher.code, " +
            "CheckVoucher.particulars, " +
            "CheckVoucher.voucherDate, " +
            "CheckVoucher.FK_transactionId " +
            "FROM CheckVoucher " +
            "WHERE CheckVoucher.voucherDate >= :from AND CheckVoucher.voucherDate <= :to " +
            "AND CheckVoucher.FK_documentStatusId = :documentStatusId " +
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
            "JOIN CheckVoucher on GeneralLedger.FK_transactionId = CheckVoucher.FK_transactionId " +
            "WHERE (CheckVoucher.voucherDate >= :from AND CheckVoucher.voucherDate <= :to) AND CheckVoucher.FK_documentStatusId = :documentStatusId " +
            "GROUP BY GeneralLedger.FK_segmentAccountId) as generals  " +
            "LEFT JOIN (SELECT " +
            "SubLedger.FK_generalLedgerLineId AS slGLId, " +
            "SubLedger.FK_segmentAccountId as slSegmentAccountId," +
            "SubLedger.FK_accountNo as accountNo," +
            "IF(SUM(COALESCE(SubLedger.debit, 0))-SUM(COALESCE(SubLedger.credit, 0)) > 0, SUM(COALESCE(SubLedger.debit, 0))-SUM(COALESCE(SubLedger.credit, 0)), 0) AS slDebit, " +
            "IF(SUM(COALESCE(SubLedger.debit, 0))-SUM(COALESCE(SubLedger.credit, 0)) > 0, 0, SUM(COALESCE(SubLedger.credit, 0))-SUM(COALESCE(SubLedger.debit, 0))) AS slCredit  " +
            "FROM SubLedger " +
            "JOIN CheckVoucher on SubLedger.FK_transactionId = CheckVoucher.FK_transactionId " +
            "WHERE (CheckVoucher.voucherDate >= :from AND CheckVoucher.voucherDate <= :to) AND CheckVoucher.FK_documentStatusId = :documentStatusId " +
            "GROUP BY SubLedger.FK_segmentAccountId, SubLedger.FK_accountNo) as subs  " +
            "ON generals.glSegmentAccountId = subs.slSegmentAccountId  " +
            "JOIN SegmentAccount ON generals.glSegmentAccountId = SegmentAccount.id  " +
            "JOIN Account ON SegmentAccount.FK_accountId = Account.id  " +
            "LEFT JOIN slentity ON  subs.accountNo = slentity.accountNo " +
            "ORDER BY side, code", nativeQuery = true)
    public List<Object[]> findForRegisterRecapByDateRange(@Param("from") String from, @Param("to") String to,  @Param("documentStatusId") Integer documentStatusId);

}
