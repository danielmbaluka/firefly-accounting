package com.tri.erp.spring.commons.facade;

import com.tri.erp.spring.model.Transaction;
import com.tri.erp.spring.response.GeneralLedgerLineDto2;
import com.tri.erp.spring.response.SubLedgerDto;

import java.util.List;
import java.util.Map;

/**
 * Created by TSI Admin on 5/1/2015.
 */
public interface SettingFacade {
    Map getByCode(String code);
}
