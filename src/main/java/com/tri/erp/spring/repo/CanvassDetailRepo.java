package com.tri.erp.spring.repo;

import com.tri.erp.spring.model.CanvassDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Personal on 5/12/2015.
 */
public interface CanvassDetailRepo extends JpaRepository<CanvassDetail, Integer> {
    @Transactional
    @Query(value = "SELECT cnvsd.*, rvd.quantity, i.code, i.description, u.code, rv.code FROM canvassDetail cnvsd " +
            "INNER JOIN rvDetail rvd ON cnvsd.FK_rvDetailId = rvd.id " +
            "INNER JOIN item i ON rvd.FK_itemId = i.id " +
            "INNER JOIN unitMeasure u ON rvd.FK_unitId = u.id " +
            "INNER JOIN requisitionVoucher rv ON rvd.FK_requisitionVoucherId = rv.id " +
            "WHERE cnvsd.FK_canvassId = :canvassId", nativeQuery = true)
    public List<CanvassDetail> findByCanvassId(@Param("canvassId")Integer id);

    public Long deleteByCanvassId(Integer transId);
}
