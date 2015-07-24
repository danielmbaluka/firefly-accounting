package com.tri.erp.spring.service.interfaces;

import com.tri.erp.spring.model.RequisitionVoucher;
import com.tri.erp.spring.response.RvDto;
import com.tri.erp.spring.response.RvListDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Personal on 3/20/2015.
 */
public interface RequisitionVoucherService extends VoucherService {
    public RequisitionVoucher findByCode(String code);

    @Transactional(readOnly = true)
    public RvDto findByRvId(Integer rvId);

    @Transactional
    public List<RvListDto> findAll();
}
