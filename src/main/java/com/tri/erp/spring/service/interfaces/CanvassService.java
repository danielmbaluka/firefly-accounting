package com.tri.erp.spring.service.interfaces;

import com.tri.erp.spring.model.Canvass;
import com.tri.erp.spring.response.CanvassDto;
import com.tri.erp.spring.response.CanvassListDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Personal on 5/12/2015.
 */
public interface CanvassService extends VoucherService {
    public Canvass findByCode(String code);

    @Transactional(readOnly = true)
    public CanvassDto findById(Integer canvassId);

    @Transactional
    public List<CanvassListDto> findAll();
}
