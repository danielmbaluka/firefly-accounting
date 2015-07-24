package com.tri.erp.spring.service.implementations;

import com.tri.erp.spring.response.GeneralLedgerLineDto;
import com.tri.erp.spring.response.SubLedgerDto;
import com.tri.erp.spring.service.interfaces.LedgerService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by TSI Admin on 4/27/2015.
 */

@Service
public class LedgerServiceImpl implements LedgerService {

    @Override
    public List<GeneralLedgerLineDto> getGLEntries(Integer transId) {
        return null;
    }

    @Override
    public List<SubLedgerDto> getSLEntries(Integer glId) {
        return null;
    }
}
