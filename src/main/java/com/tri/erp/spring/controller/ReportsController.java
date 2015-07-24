package com.tri.erp.spring.controller;

import com.tri.erp.spring.commons.GlobalConstant;
import com.tri.erp.spring.commons.facade.AuthenticationFacade;
import com.tri.erp.spring.commons.helpers.ControllerHelper;
import com.tri.erp.spring.service.implementations.DownloadService;
import com.tri.erp.spring.service.interfaces.ReportsService;
import com.tri.erp.spring.service.interfaces.RoleService;
import net.sf.jasperreports.engine.JRDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

/**
 * Created by TSI Admin on 11/13/2014.
 */

@Controller
@RequestMapping(value = "/reports")
public class ReportsController {

    @Autowired
    private AuthenticationFacade authenticationFacade;

    @Autowired
    private RoleService roleService;

    @Autowired
    DownloadService downloadService;

    @Autowired
    ReportsService reportsService;

    private final String BASE_PATH = "reports/partials/";
    private final String MAIN = "reports/";
    private final String REPORT_PATH = "/registers/CommonVoucherRegister.jrxml";

    // accounting reports view providers
    @RequestMapping(value = "/accounting", method = RequestMethod.GET)
    public ModelAndView indexAccounting(HttpServletRequest request) {
        return ControllerHelper.getModelAndView(MAIN + "accounting", roleService, authenticationFacade.getLoggedIn().getId(), request.getRequestURI());
    }

    @RequestMapping(value = "/accounting/trial-balance", method = RequestMethod.GET)
    public String getTrialBalance(HttpServletRequest request) {
        return BASE_PATH + "trial-balance";
    }

    // JV registers
    @RequestMapping(value = "/accounting/jv-register", method = RequestMethod.GET)
    public String geJVRegister(HttpServletRequest request) {
        return BASE_PATH + "jv-register";
    }

    @RequestMapping(value="/export/jv-register/{from}/{to}")
    public void exportJvRegister(@PathVariable String from, @PathVariable String to,
                            @RequestParam(value = "type") String type,
                            @RequestParam(value = "token") String token,
                            HttpServletResponse response, HttpServletRequest request) {

        HashMap params = reportsService.reportParametersForRegister("JV", from, to, request);
        JRDataSource dataSource = reportsService.datasourceForRegister(from, to);

        String template = GlobalConstant.JASPER_BASE_PATH + REPORT_PATH;
        downloadService.download(type, token, response, params, template, dataSource);
    } // end: JV registers


    // CV registers
    @RequestMapping(value = "/accounting/cv-register", method = RequestMethod.GET)
    public String geCVRegister(HttpServletRequest request) {
        return BASE_PATH + "cv-register";
    }

    @RequestMapping(value="/export/cv-register/{from}/{to}")
    public void exportCvRegister(@PathVariable String from, @PathVariable String to,
                            @RequestParam(value = "type") String type,
                            @RequestParam(value = "token") String token,
                            HttpServletResponse response, HttpServletRequest request) {

        HashMap params = reportsService.reportParametersForRegister("CV", from, to, request);
        JRDataSource dataSource = reportsService.datasourceForRegister(from, to);

        String template = GlobalConstant.JASPER_BASE_PATH + REPORT_PATH;
        downloadService.download(type, token, response, params, template, dataSource);
    } // end: CV registers

}
