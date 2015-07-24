package com.tri.erp.spring.service.implementations;

import com.tri.erp.spring.commons.GlobalConstant;
import com.tri.erp.spring.commons.helpers.ReportUtil;
import com.tri.erp.spring.dtoers.AccountingReportDtoer;
import com.tri.erp.spring.response.reports.CommonRegisterDetail;
import com.tri.erp.spring.response.reports.RegisterRecapDetail;
import com.tri.erp.spring.service.interfaces.ReportsService;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by TSI Admin on 7/22/2015.
 */

@Service(value = "reportsServiceImpl")
public class ReportsServiceImpl implements ReportsService {

    @Autowired
    AccountingReportDtoer accountingReportDtoer;
    private String registerType = null;

    private String formatDateRange(String from, String to) {
        DateFormat formatter = new SimpleDateFormat("yy-MM-dd");
        DateFormat humanFormat = new SimpleDateFormat("MMMM dd, yy");
        try {
            Date dateFrom = formatter.parse(from);
            Date dateTo = formatter.parse(to);

            return humanFormat.format(dateFrom) + " to " + humanFormat.format(dateTo);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public HashMap reportParametersForRegister(String registerType, String from, String to, HttpServletRequest request) {
        HashMap<String, Object> params = this.getRegisterCommonParams(from, to, request);
        List<RegisterRecapDetail> recap = new ArrayList<>();

        this.registerType = registerType;
        switch (this.registerType) {
            case "JV":
                recap = accountingReportDtoer.getForJVRegisterRecap(from, to);
                params.put("RECAP_DS", new JRBeanCollectionDataSource(recap));
                params.put("REPORT_TITLE", "Journal Voucher Register");

                break;
            case "CV":
                recap = accountingReportDtoer.getForCVRegisterRecap(from, to);
                params.put("RECAP_DS", new JRBeanCollectionDataSource(recap));
                params.put("REPORT_TITLE", "Check Voucher Register");

                break;
        }
        return params;
    }

    @Override
    public JRDataSource datasourceForRegister(String from, String to) {
        if (this.registerType != null) {
            switch (this.registerType) {
                case "JV":
                    return new JRBeanCollectionDataSource(accountingReportDtoer.getForJVRegister(from, to));
                case "CV":
                    return new JRBeanCollectionDataSource(accountingReportDtoer.getForCVRegister(from, to));
            }
        }
        return null;
    }

    // private methods here
    private HashMap getRegisterCommonParams(String from, String to, HttpServletRequest request) {
        HashMap<String, Object> params = ReportUtil.setupSharedReportHeaders(request);
        params.put("RANGE", this.formatDateRange(from, to));
        params.put("SUBREPORT_DIR", GlobalConstant.JASPER_BASE_PATH + "/registers/recaps/");

        return params;
    }
}
