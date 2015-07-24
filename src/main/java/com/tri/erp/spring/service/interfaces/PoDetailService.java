package com.tri.erp.spring.service.interfaces;

import com.tri.erp.spring.response.PoDetailDto;

import java.util.List;

/**
 * Created by Personal on 5/15/2015.
 */
public interface PoDetailService {
    public List<PoDetailDto> getPoDetails(Integer poId);
}
