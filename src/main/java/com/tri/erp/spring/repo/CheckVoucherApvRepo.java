package com.tri.erp.spring.repo;

import com.tri.erp.spring.model.CheckVoucherApv;
import com.tri.erp.spring.model.CheckVoucherCheque;
import com.tri.erp.spring.model.SlEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by TSI Admin on 5/13/2015.
 */
public interface CheckVoucherApvRepo extends JpaRepository<CheckVoucherApv, Integer> {
    public Long deleteByCheckVoucherId(Integer cid);

    @Transactional(readOnly = true)
    @Query(value = "SELECT " +
            "FK_accountsPayableVoucherId, " +
            "FK_checkVoucherId " +
            "FROM CheckVoucherApv " +
            "WHERE FK_checkVoucherId = :FK_checkVoucherId LIMIT 1", nativeQuery = true) // take one only for now
    public List<Object[]> findByCheckVoucherId(@Param("FK_checkVoucherId") Integer cvId);

}
