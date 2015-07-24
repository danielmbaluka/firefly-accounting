package com.tri.erp.spring.service.interfaces;

import com.tri.erp.spring.model.Prepayment;
import com.tri.erp.spring.response.PrepaymentDto;
import com.tri.erp.spring.response.PrepaymentListDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Personal on 6/3/2015.
 */
public interface PrepaymentService extends VoucherService {
    public Prepayment findByDescription(String description);

    @Transactional(readOnly = true)
    public PrepaymentDto findById(Integer id);

    @Transactional
    public List<PrepaymentListDto> findAll();
}
