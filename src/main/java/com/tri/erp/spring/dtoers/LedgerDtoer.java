package com.tri.erp.spring.dtoers;

import com.tri.erp.spring.response.GeneralLedgerLineDto;
import com.tri.erp.spring.response.GeneralLedgerLineDto2;
import com.tri.erp.spring.response.SubLedgerDto;
import com.tri.erp.spring.response.reports.CommonLedgerDetail;

import java.util.List;
import java.util.Map;

/**
 * Created by TSI Admin on 5/3/2015.
 */
public interface LedgerDtoer {
    public List<SubLedgerDto> getSLEntriesDtoByGl(Integer glId);
    public List<SubLedgerDto> getSLEntriesDtoByTrans(Integer transId);
    public List<SubLedgerDto> getSLEntityDistributionByTrans(Integer transId);
    List<GeneralLedgerLineDto> getGLEntriesDtoByTrans(Integer transId);
    List<GeneralLedgerLineDto2> getGLAccountEntriesDtoByTrans(Integer accountId);
    public List<SubLedgerDto> getSLEntriesDtoByTransAndAccount(Integer transId, Integer accountId);
    public List<Map> getGLEntriesDtoByTransAndAccount(Integer transId, Integer accountId);
    public  List<CommonLedgerDetail> getVoucherLedgerLines(Integer transId);

    // from temps
    public List<Map> getAllTempBatches();
    List<GeneralLedgerLineDto2> getGLAccountEntriesDtoTempBatchId(Integer tempBatchId);
    List<SubLedgerDto> getSLAccountEntriesDtoTempBatchId(Integer tempBatchId, Integer accountId);
}
