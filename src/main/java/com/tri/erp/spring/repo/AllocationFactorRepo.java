package com.tri.erp.spring.repo;

import com.tri.erp.spring.model.AllocationFactor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by TSI Admin on 5/19/2015.
 */
public interface AllocationFactorRepo extends JpaRepository<AllocationFactor, Integer> {
    @Transactional(readOnly = true)
    @Query(value = "SELECT " +
            "DISTINCTROW a.id AS factorId,  " +
            "Account.id as accountId,  " +
            "Account.code,  " +
            "Account.title,  " +
            "BusinessSegment.id as segmentId,  " +
            "BusinessSegment.description, " +
            "DateRange.`start`, " +
            "DateRange.`end`, " +
            "a.`createdAt`, " +
            "a.`updatedAt`, " +
            "DateRange.id, " +
            "a.percentage " +
            "FROM AllocationFactor a  " +
            "JOIN DateRange ON a.FK_effectivityDateId = DateRange.id " +
            "JOIN Account ON a.FK_accountId = Account.id " +
            "JOIN BusinessSegment ON a.FK_businessSegmentId = BusinessSegment.id " +
            "WHERE DateRange.`end` = (SELECT MAX(DateRange.`end`) FROM AllocationFactor a JOIN DateRange ON a.FK_effectivityDateId = DateRange.id) " +
            "ORDER BY Account.code ASC, BusinessSegment.code ASC", nativeQuery = true)
    public List<Object[]> findAllCustom();

    @Transactional(readOnly = true)
    @Query(value = "SELECT " +
            "DISTINCTROW a.id AS factorId,  " +
            "Account.id as accountId,  " +
            "Account.code,  " +
            "Account.title,  " +
            "BusinessSegment.id as segmentId,  " +
            "BusinessSegment.description, " +
            "DateRange.`start`, " +
            "DateRange.`end`, " +
            "a.`createdAt`, " +
            "a.`updatedAt`, " +
            "DateRange.id, " +
            "a.percentage " +
            "FROM AllocationFactor a  " +
            "JOIN DateRange ON a.FK_effectivityDateId = DateRange.id " +
            "JOIN Account ON a.FK_accountId = Account.id " +
            "JOIN BusinessSegment ON a.FK_businessSegmentId = BusinessSegment.id " +
            "WHERE a.FK_accountId = :accountId " +
            "AND a.FK_effectivityDateId = :effectId " +
            "AND DateRange.`end` = (SELECT MAX(DateRange.`end`) FROM AllocationFactor a JOIN DateRange ON a.FK_effectivityDateId = DateRange.id) " +
            "ORDER BY Account.code ASC, BusinessSegment.code ASC", nativeQuery = true)
    public List<Object[]> findOneCustom(@Param("accountId") Integer accountId, @Param("effectId") Integer effectId);

    public Long deleteByAccountIdAndEffectivityDateId(Integer accountId, Integer effectId);

    @Transactional(readOnly = true)
    @Query(value = "SELECT " +
            "a.FK_accountId, " +
            "SegmentAccount.id, " +
            "a.percentage " +
            "FROM AllocationFactor a " +
            "JOIN DateRange ON a.FK_effectivityDateId = DateRange.id " +
            "JOIN SegmentAccount ON a.FK_businessSegmentId = SegmentAccount.FK_businessSegmentId AND a.FK_accountId = SegmentAccount.FK_accountId " +
            "WHERE a.FK_accountId = :accountId " +
            "AND DateRange.`end` = (SELECT MAX(DateRange.`end`) FROM AllocationFactor a JOIN DateRange ON a.FK_effectivityDateId = DateRange.id)", nativeQuery = true)
    public List<Object[]> findByAccountId(@Param("accountId") Integer accountId);

    @Transactional(readOnly = true)
    @Query(value = "SELECT " +
            " SegmentAccount.id as segmentAccountId, " +
            " BusinessSegment.id as segmentId, " +
            " BusinessSegment.description, " +
            " a.percentage " +
            " FROM AllocationFactor a   " +
            " JOIN DateRange ON a.FK_effectivityDateId = DateRange.id   " +
            " JOIN SegmentAccount ON a.FK_businessSegmentId = SegmentAccount.FK_businessSegmentId AND a.FK_accountId = SegmentAccount.FK_accountId  " +
            " JOIN BusinessSegment ON SegmentAccount.FK_businessSegmentId = BusinessSegment.id " +
            " WHERE a.FK_accountId = :accountId " +
            " AND DateRange.`end` = (SELECT MAX(DateRange.`end`) FROM AllocationFactor a JOIN DateRange ON a.FK_effectivityDateId = DateRange.id) " +
            " GROUP BY BusinessSegment.id, a.id", nativeQuery = true)
    public List<Object[]> findLatestAccountAllocatedSegments(@Param("accountId") Integer accountId);

    @Transactional(readOnly = true)
    @Query(value = "SELECT " +
            "DISTINCTROW a.id AS factorId,  " +
            "SegmentAccount.id as segmentAccountId, " +
            "BusinessSegment.id as segmentId,  " +
            "BusinessSegment.description, " +
            "a.percentage " +
            "FROM AllocationFactor a  " +
            "JOIN BusinessSegment ON a.FK_businessSegmentId = BusinessSegment.id " +
            "JOIN DateRange ON a.FK_effectivityDateId = DateRange.id " +
            "JOIN SegmentAccount ON a.FK_accountId = SegmentAccount.FK_accountId AND a.FK_businessSegmentId = SegmentAccount.FK_businessSegmentId " +
            "WHERE a.FK_accountId = :accountId " +
            " AND :voucherDate <= DateRange.`end` " +
            " AND :voucherDate >= DateRange.`start` " +
            "ORDER BY BusinessSegment.code ASC", nativeQuery = true)
    public List<Object[]> findByAccountAndDate(@Param("accountId") Integer accountId, @Param("voucherDate") String voucherDate);

}
