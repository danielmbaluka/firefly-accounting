package com.tri.erp.spring.commons.facade;

import com.tri.erp.spring.model.Transaction;
import com.tri.erp.spring.response.GeneralLedgerLineDto;
import com.tri.erp.spring.response.GeneralLedgerLineDto2;
import com.tri.erp.spring.response.SubLedgerDto;

import java.util.List;

/**
 * Created by TSI Admin on 5/1/2015.
 */
public interface LedgerFacade {
    Long postGeneralLedger(Transaction transaction, List<GeneralLedgerLineDto2> generalLedgerLines, List<SubLedgerDto> subLedgerLines);
}
