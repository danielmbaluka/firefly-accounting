package com.tri.erp.spring.service.interfaces;

import com.tri.erp.spring.model.BankDeposit;
import com.tri.erp.spring.response.BankDepositDto;
import com.tri.erp.spring.response.BankDepositListDto;
import com.tri.erp.spring.response.SalesVoucherDto;
import com.tri.erp.spring.response.SalesVoucherListDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by nsutgio2015 on 4/27/2015.
 */
public interface BankDepositService extends VoucherService {

    @Transactional
    public List<BankDepositListDto> findAll();

    @Transactional(readOnly = true)
    public BankDepositDto findById(Integer id);

    @Transactional(readOnly = true)
    public  List<BankDepositListDto> findByStatusId(Integer id);
}
