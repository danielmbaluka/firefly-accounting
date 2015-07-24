package com.tri.erp.spring.repo;

import com.tri.erp.spring.model.CheckConfig;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by TSI Admin on 12/8/2014.
 */

public interface CheckConfigRepo extends JpaRepository<CheckConfig, Integer> {
    public CheckConfig findOneByCode(String codes);
    public CheckConfig findOneByBankSegmentAccountId(Integer segmentAccountId);
}
