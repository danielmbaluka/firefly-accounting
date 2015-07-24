package com.tri.erp.spring.repo;

import com.tri.erp.spring.model.CashReceipts;
import com.tri.erp.spring.model.SalesVoucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by TSI Admin on 4/21/2015.
 */
public interface CashReceiptsRepo extends JpaRepository<CashReceipts, Integer> {
    @Query(value = "SELECT e.code FROM CashReceipts e WHERE year = :year ORDER BY id DESC LIMIT 1", nativeQuery = true)
    public Object findLatestCrCodeByYear(@Param("year") Integer year);
    public List<CashReceipts> findByDocumentStatusId(Integer statusId);
}
