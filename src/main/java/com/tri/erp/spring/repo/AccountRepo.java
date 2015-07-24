package com.tri.erp.spring.repo;

import com.tri.erp.spring.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by TSI Admin on 9/9/2014.
 */
public interface AccountRepo extends JpaRepository<Account, Integer> {
    public List<Account> findByTitle(String title);
    public List<Account> findAllByOrderByTitleAsc();
    public List<Account> findByParentAccountIdOrderByCodeAsc(Integer accountId);
    public List<Account> findByIdNotIn(Integer... accountId);
    public List<Account> findByIdNotInOrderByTitleAsc(Integer... accountId);

    @Transactional(readOnly = true)
    @Query(value = "SELECT " +
            "Account.id as accountId, " +
            "Account.title, " +
            "SegmentAccount.id as segmentAccountId, " +
            "SegmentAccount.accountCode, " +
            "AccountType.id as accountTypeId, " +
            "AccountType.description " +
            "FROM Account " +
            "JOIN SegmentAccount ON Account.id = SegmentAccount.FK_accountId " +
            "LEFT JOIN AccountType ON Account.FK_accountTypeId = AccountType.id " +
            "WHERE SegmentAccount.FK_businessSegmentId IN (:segmentIds) " +
            "ORDER BY Account.code ASC, SegmentAccount.accountCode ASC", nativeQuery = true)
    public List<Object[]> findBySegmentIds(@Param("segmentIds") List<String> segmentIds);

    @Transactional(readOnly = true)
    @Query(value = "SELECT " +
            "Account.id as accountId, " +
            "Account.title, " +
            "Account.code, " +
            "AccountType.id as typeId, " +
            "AccountType.description " +
            "FROM Account " +
            "JOIN SegmentAccount ON Account.id = SegmentAccount.FK_accountId " +
            "LEFT JOIN AccountType ON Account.FK_accountTypeId = AccountType.id " +
            "GROUP BY Account.id " +
            "ORDER BY Account.code ASC, SegmentAccount.accountCode ASC", nativeQuery = true)
    public List<Object[]> findAllWithSegment();
}
