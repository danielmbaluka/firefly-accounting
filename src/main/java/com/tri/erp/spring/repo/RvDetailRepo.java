package com.tri.erp.spring.repo;

import com.tri.erp.spring.model.RvDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Personal on 4/29/2015.
 */
public interface RvDetailRepo extends JpaRepository<RvDetail, Integer> {
    public List<RvDetail> findByRequisitionVoucherId(Integer transId);

    public Long deleteByRequisitionVoucherId(Integer transId);

    @Transactional
    @Query(value = "SELECT * FROM RvDetail rvd " +
            "INNER JOIN RequisitionVoucher e ON rvd.FK_requisitionVoucherId = e.id " +
            "WHERE e.FK_documentStatusId = :statusId AND (rvd.quantity - rvd.poQuantity) != 0", nativeQuery = true)
    public List<RvDetail> findRvDetailsByRequisitionVoucherDocumentStatusId(@Param("statusId")Integer statusId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE RvDetail SET poQuantity = :poQty WHERE id = :rvdId", nativeQuery = true)
    public int updatePoQuantityById(@Param("rvdId") Integer id, @Param("poQty") BigDecimal poQuantity);
}
