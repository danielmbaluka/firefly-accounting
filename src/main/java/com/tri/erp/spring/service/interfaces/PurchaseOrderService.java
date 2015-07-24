package com.tri.erp.spring.service.interfaces;

import com.tri.erp.spring.model.PurchaseOrder;
import com.tri.erp.spring.response.PoDto;
import com.tri.erp.spring.response.PoListDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Personal on 5/15/2015.
 */
public interface PurchaseOrderService extends VoucherService {
    public PurchaseOrder findByCode(String code);

    @Transactional(readOnly = true)
    public PoDto findById(Integer canvassId);

    @Transactional
    public List<PoListDto> findAll();
}
