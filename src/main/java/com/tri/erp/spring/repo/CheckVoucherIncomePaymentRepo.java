package com.tri.erp.spring.repo;

import com.tri.erp.spring.model.CheckVoucherIncomePayment;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by TSI Admin on 6/25/2015.
 */
public interface CheckVoucherIncomePaymentRepo extends JpaRepository<CheckVoucherIncomePayment, Integer> {
    public Long deleteByTransactionId(Integer transId);
    public CheckVoucherIncomePayment findOneByTransactionId(Integer transId);
}
