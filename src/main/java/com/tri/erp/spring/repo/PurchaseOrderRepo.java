package com.tri.erp.spring.repo;

import com.tri.erp.spring.model.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Personal on 5/15/2015.
 */
public interface PurchaseOrderRepo extends JpaRepository<PurchaseOrder, Integer> {
    public List<PurchaseOrder> findByCode(String code);
    @Query(value = "SELECT e.code FROM PurchaseOrder e WHERE year = :year ORDER BY id DESC LIMIT 1", nativeQuery = true)
    public Object findLatestPoCodeByYear(@Param("year") Integer year);
}
