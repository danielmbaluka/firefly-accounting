package com.tri.erp.spring.repo;

import com.tri.erp.spring.model.CheckVoucher;
import com.tri.erp.spring.model.CheckVoucherCheque;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by TSI Admin on 5/13/2015.
 */
public interface CheckVoucherChequeRepo extends JpaRepository<CheckVoucherCheque, Integer> {
    public CheckVoucherCheque findOneByBankAccountIdAndTransactionId(Integer bankAccountId, Integer transId);
    public Long deleteByTransactionId(Integer transId);
    public Long deleteByBankAccountIdAndTransactionId(Integer bankAccountId, Integer transId);
    public List<CheckVoucherCheque> findByTransactionId(Integer transId);
    public List<CheckVoucherCheque> findByTransactionIdAndReleased(Integer transId, Boolean isReleased);
}
