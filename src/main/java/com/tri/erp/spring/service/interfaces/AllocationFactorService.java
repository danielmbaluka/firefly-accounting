package com.tri.erp.spring.service.interfaces;

import com.tri.erp.spring.response.AllocationFactorDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by TSI Admin on 5/19/2015.
 */
public interface AllocationFactorService extends DataManagementService {
    public List<AllocationFactorDto> findAll();
    public AllocationFactorDto findByAccountAndEffectivityId(Integer accountId, Integer effectId);
    public List<Map> findLatestByAccount(Integer accountId);
    public List<Map> findAccountAndEffectDate(Integer accountId, String voucherDate);
    public List<Map> findDateRanges();
    public Map testAutoAllocation(Map postData);
}
