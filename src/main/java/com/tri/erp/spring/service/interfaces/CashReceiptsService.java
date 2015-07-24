package com.tri.erp.spring.service.interfaces;

import com.tri.erp.spring.response.CashReceiptsDto;
import com.tri.erp.spring.response.CashReceiptsListDto;
import com.tri.erp.spring.response.SalesVoucherDto;
import com.tri.erp.spring.response.SalesVoucherListDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by nsutgio2015 on 4/27/2015.
 */
public interface CashReceiptsService extends VoucherService {

    @Transactional
    public List<CashReceiptsListDto> findAll();

    @Transactional(readOnly = true)
    public CashReceiptsDto findById(Integer id);

    @Transactional(readOnly = true)
    public  List<CashReceiptsListDto> findByStatusId(Integer id);
}
