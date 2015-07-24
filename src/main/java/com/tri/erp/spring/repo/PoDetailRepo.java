package com.tri.erp.spring.repo;

import com.tri.erp.spring.model.PoDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Personal on 5/15/2015.
 */
public interface PoDetailRepo extends JpaRepository<PoDetail, Integer> {
    @Transactional
    @Query(value = "SELECT pod.*, rvd.quantity, i.code, i.description, u.code FROM poDetail pod " +
            "INNER JOIN rvDetail rvd ON pod.FK_rvDetailId = rvd.id " +
            "INNER JOIN item i ON rvd.FK_itemId = i.id " +
            "INNER JOIN unitMeasure u ON rvd.FK_unitId = u.id " +
            "WHERE pod.FK_purchaseOrderId = :poId", nativeQuery = true)
    public List<PoDetail> findByPurchaseOrderId(@Param("poId") Integer id);

    @Transactional
    public Long deleteByPurchaseOrderId(Integer transId);
}
