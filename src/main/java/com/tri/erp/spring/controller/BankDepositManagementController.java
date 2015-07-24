package com.tri.erp.spring.controller;

import com.tri.erp.spring.commons.GlobalConstant;
import com.tri.erp.spring.commons.facade.AuthenticationFacade;
import com.tri.erp.spring.commons.helpers.ControllerHelper;
import com.tri.erp.spring.model.BankDeposit;
import com.tri.erp.spring.service.implementations.DownloadService;
import com.tri.erp.spring.service.interfaces.ApvService;
import com.tri.erp.spring.service.interfaces.BankDepositService;
import com.tri.erp.spring.service.interfaces.PrintableVoucher;
import com.tri.erp.spring.service.interfaces.RoleService;
import net.sf.jasperreports.engine.JRDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
 * Created by nsutgio2015 on 4/13/2015.
 */
@Controller
@RequestMapping(value = "bank-deposit-mgt")
public class BankDepositManagementController {

    @Autowired
    private AuthenticationFacade authenticationFacade;

    @Autowired
    BankDepositService bankDepositService;

    @Autowired
    @Qualifier("bankDepositServiceImpl")
    PrintableVoucher printableVoucher;

    @Autowired
    private RoleService roleService;

    @Autowired
    private DownloadService downloadService;

    private final String BASE_PATH = "bank-deposit/partials/";
    private final String MAIN = "bank-deposit/main";

    // view providers
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView index(HttpServletRequest request) {
        return ControllerHelper.getModelAndView(MAIN, roleService, authenticationFacade.getLoggedIn().getId(), request.getRequestURI());
    }

    @RequestMapping(value = "/new-bank-deposit-page", method = RequestMethod.GET)
    public String newBankDeposit(HttpServletRequest request) {
        Boolean hasPermissionOnMethod = roleService.isRouteAuthorized(authenticationFacade.getLoggedIn().getId(), request.getRequestURI());
        return hasPermissionOnMethod ? (BASE_PATH + "add-edit") : "403";
    }

    @RequestMapping(value = "/bank-deposit-details-page", method = RequestMethod.GET)
    public String details(HttpServletRequest request) {
        return BASE_PATH + "details";
    }

    @RequestMapping(value="/export/{id}")
    public void exportToPdf(@PathVariable Integer id,
                            @RequestParam(value = "type") String type,
                            @RequestParam(value = "token") String token,
                            HttpServletResponse response, HttpServletRequest request) {


        HashMap params = printableVoucher.reportParameters(id, request);
        JRDataSource dataSource = printableVoucher.datasource(id);

        String template = GlobalConstant.JASPER_BASE_PATH + "/vouchers/BankDeposit.jrxml";
        downloadService.download(type, token, response, params, template, dataSource);
    }
}
