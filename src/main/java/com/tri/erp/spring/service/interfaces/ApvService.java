package com.tri.erp.spring.service.interfaces;

import com.tri.erp.spring.response.ApvDto;
import com.tri.erp.spring.response.ApvListDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ApvService extends VoucherService {

    @Transactional
    public List<ApvListDto> findAll();

    @Transactional(readOnly = true)
    public ApvDto findById(Integer id);

    @Transactional(readOnly = true)
    public  List<ApvListDto> findByStatusId(Integer id);
}
