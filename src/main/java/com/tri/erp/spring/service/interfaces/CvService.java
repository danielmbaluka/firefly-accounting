package com.tri.erp.spring.service.interfaces;

import com.tri.erp.spring.model.CheckVoucherCheque;
import com.tri.erp.spring.model.ReleasedCheque;
import com.tri.erp.spring.response.*;
import com.tri.erp.spring.response.reports.CheckDto;
import org.springframework.context.MessageSource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Map;

public interface CvService extends VoucherService {

    @Transactional
    public List<CvListDto> findAll();

    @Transactional(readOnly = true)
    public CvDto findById(Integer id);

    @Transactional(readOnly = true)
    public ApvDto checkPrintingParams(Integer transId, Integer backAccountId);

    @Transactional
    public PostResponse updateCheckNumber(CheckVoucherCheque cheque, BindingResult bindingResult, MessageSource messageSource);

    @Transactional(readOnly = true)
    public CheckDto findForPrintCheckDetails(Integer transId, Integer bankAccountId);

    @Transactional(readOnly = true)
    public List<Map> findCvChecks(Integer transId);

    @Transactional(readOnly = true)
    public List<Map> findCheckVouchersForReleasing();


    @Transactional(readOnly = true)
    public List<Map> findChequeNumbersForCheckReleasing(Integer transId);
}
