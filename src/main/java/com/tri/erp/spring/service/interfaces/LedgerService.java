package com.tri.erp.spring.service.interfaces;

import com.tri.erp.spring.response.GeneralLedgerLineDto;
import com.tri.erp.spring.response.SubLedgerDto;

import java.util.List;

/**
 * Created by TSI Admin on 4/27/2015.
 */
public interface LedgerService {
    public List<GeneralLedgerLineDto> getGLEntries(Integer transId);
    public List<SubLedgerDto> getSLEntries(Integer glId);
}
