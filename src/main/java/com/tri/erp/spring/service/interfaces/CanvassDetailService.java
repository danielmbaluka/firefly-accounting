package com.tri.erp.spring.service.interfaces;

import com.tri.erp.spring.response.CanvassDetailDto;

import java.util.List;

/**
 * Created by Personal on 5/14/2015.
 */
public interface CanvassDetailService {
    public List<CanvassDetailDto> getCanvassDetails(Integer canvassId);
}
