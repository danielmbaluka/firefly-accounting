package com.tri.erp.spring.repo;

import com.tri.erp.spring.model.RequisitionVoucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Personal on 3/20/2015.
 */
public interface RequisitionVoucherRepo extends JpaRepository<RequisitionVoucher, Integer> {
    public List<RequisitionVoucher> findByCode(String code);
    @Query(value = "SELECT e.code FROM RequisitionVoucher e WHERE year = :year ORDER BY id DESC LIMIT 1", nativeQuery = true)
    public Object findLatestRvCodeByYear(@Param("year") Integer year);
}
