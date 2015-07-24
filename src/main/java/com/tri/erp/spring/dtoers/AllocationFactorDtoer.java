package com.tri.erp.spring.dtoers;

import com.tri.erp.spring.response.AllocationFactorDto;
import com.tri.erp.spring.response.GeneralLedgerLineDto;
import com.tri.erp.spring.response.SubLedgerDto;

import java.util.List;
import java.util.Map;

/**
 * Created by TSI Admin on 5/3/2015.
 */
public interface AllocationFactorDtoer {
    public List<AllocationFactorDto> findAll();
    public AllocationFactorDto findOne(Integer accountId, Integer effectId);
    public List<Map> findLatestOne(Integer accountId);
}
