package com.tri.erp.spring.service.interfaces;

import com.tri.erp.spring.response.RvDetailDto;

import java.util.List;

/**
 * Created by Personal on 4/29/2015.
 */
public interface RvDetailService {
    public List<RvDetailDto> getRvDetails(Integer rvId);
    public List<RvDetailDto> getRvDetailsByStatus(Integer statusId);
}
