package com.tri.erp.spring.service.interfaces;

import com.tri.erp.spring.model.MaterialIssueRegister;
import com.tri.erp.spring.response.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by nsutgio2015 on 4/27/2015.
 */
public interface SalesVoucherService extends VoucherService {

    @Transactional
    public List<SalesVoucherListDto> findAll();

    @Transactional(readOnly = true)
    public SalesVoucherDto findById(Integer id);

    @Transactional(readOnly = true)
    public  List<SalesVoucherListDto> findByStatusId(Integer id);
}
