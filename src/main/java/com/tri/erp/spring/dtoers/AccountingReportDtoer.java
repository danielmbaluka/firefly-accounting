package com.tri.erp.spring.dtoers;

import com.tri.erp.spring.response.reports.CommonRegisterDetail;
import com.tri.erp.spring.response.reports.RegisterRecapDetail;

import java.util.List;

/**
 * Created by TSI Admin on 5/3/2015.
 */
public interface AccountingReportDtoer {
    public List<CommonRegisterDetail> getForJVRegister(String from, String to);
    public List<RegisterRecapDetail> getForJVRegisterRecap(String from, String to);

    public List<CommonRegisterDetail> getForCVRegister(String from, String to);
    public List<RegisterRecapDetail> getForCVRegisterRecap(String from, String to);
}
