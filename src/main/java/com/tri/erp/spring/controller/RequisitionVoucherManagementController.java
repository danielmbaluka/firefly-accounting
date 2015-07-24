package com.tri.erp.spring.controller;

import com.tri.erp.spring.commons.GlobalConstant;
import com.tri.erp.spring.commons.facade.AuthenticationFacade;
import com.tri.erp.spring.commons.helpers.ControllerHelper;
import com.tri.erp.spring.model.RequisitionVoucher;
import com.tri.erp.spring.service.implementations.DownloadService;
import com.tri.erp.spring.service.interfaces.PrintableVoucher;
import com.tri.erp.spring.service.interfaces.RequisitionVoucherService;
import com.tri.erp.spring.service.interfaces.RoleService;
import net.sf.jasperreports.engine.JRDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;

/**
 * Created by TSI Admin on 11/13/2014.
 */

@Controller
@RequestMapping(value = "/requisition-voucher")
public class RequisitionVoucherManagementController {

    @Autowired
    private AuthenticationFacade authenticationFacade;

    @Autowired
    private RoleService roleService;

    @Autowired
    @Qualifier("rvServiceImpl")
    PrintableVoucher printableVoucher;

    @Autowired
    private DownloadService downloadService;

    private final String BASE_PATH = "rv/partials/";
    private final String MAIN = "rv/main";

    // view providers
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView index(HttpServletRequest request) {
        return ControllerHelper.getModelAndView(MAIN, roleService, authenticationFacade.getLoggedIn().getId(), request.getRequestURI());
    }

    @RequestMapping(value = "/new-rvPO-page", method = RequestMethod.GET)
    public String newRvPO(HttpServletRequest request) {
        Boolean hasPermissionOnMethod = roleService.isRouteAuthorized(authenticationFacade.getLoggedIn().getId(), request.getRequestURI());
        return hasPermissionOnMethod ? (BASE_PATH + "add-edit-for-PO") : "403";
    }

    @RequestMapping(value = "/new-rvIT-page", method = RequestMethod.GET)
    public String newRvIT(HttpServletRequest request) {
        Boolean hasPermissionOnMethod = roleService.isRouteAuthorized(authenticationFacade.getLoggedIn().getId(), request.getRequestURI());
        return hasPermissionOnMethod ? (BASE_PATH + "add-edit-for-IT") : "403";
    }

    @RequestMapping(value = "/new-rvRep-page", method = RequestMethod.GET)
    public String newRvRep(HttpServletRequest request) {
        Boolean hasPermissionOnMethod = roleService.isRouteAuthorized(authenticationFacade.getLoggedIn().getId(), request.getRequestURI());
        return hasPermissionOnMethod ? (BASE_PATH + "add-edit-for-Rep") : "403";
    }

    @RequestMapping(value = "/new-rvLab-page", method = RequestMethod.GET)
    public String newRvLab(HttpServletRequest request) {
        Boolean hasPermissionOnMethod = roleService.isRouteAuthorized(authenticationFacade.getLoggedIn().getId(), request.getRequestURI());
        return hasPermissionOnMethod ? (BASE_PATH + "add-edit-for-Lab") : "403";
    }

    @RequestMapping(value = "/rv-details-page", method = RequestMethod.GET)
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

        String template = GlobalConstant.JASPER_BASE_PATH + "/vouchers/RequisitionVoucher.jrxml";
        downloadService.download(type, token, response, params, template, dataSource);
    }

    @RequestMapping(value="/export1/{id}")
    public void exportToPdf1(@PathVariable Integer id,
                             @RequestParam(value = "type") String type,
                             @RequestParam(value = "token") String token,
                             HttpServletResponse response, HttpServletRequest request) {


        HashMap params = printableVoucher.reportParameters(id, request);
        JRDataSource dataSource = printableVoucher.datasource(id);

        String template = GlobalConstant.JASPER_BASE_PATH + "/vouchers/RequisitionVoucherIT.jrxml";
        downloadService.download(type, token, response, params, template, dataSource);
    }

    @RequestMapping(value="/export2/{id}")
    public void exportToPdf2(@PathVariable Integer id,
                            @RequestParam(value = "type") String type,
                            @RequestParam(value = "token") String token,
                            HttpServletResponse response, HttpServletRequest request) {


        HashMap params = printableVoucher.reportParameters(id, request);
        JRDataSource dataSource = printableVoucher.datasource(id);

        String template = GlobalConstant.JASPER_BASE_PATH + "/vouchers/RequisitionVoucherRep.jrxml";
        downloadService.download(type, token, response, params, template, dataSource);
    }

    @RequestMapping(value="/export3/{id}")
    public void exportToPdf3(@PathVariable Integer id,
                             @RequestParam(value = "type") String type,
                             @RequestParam(value = "token") String token,
                             HttpServletResponse response, HttpServletRequest request) {


        HashMap params = printableVoucher.reportParameters(id, request);
        JRDataSource dataSource = printableVoucher.datasource(id);

        String template = GlobalConstant.JASPER_BASE_PATH + "/vouchers/RequisitionVoucherLab.jrxml";
        downloadService.download(type, token, response, params, template, dataSource);
    }
}
