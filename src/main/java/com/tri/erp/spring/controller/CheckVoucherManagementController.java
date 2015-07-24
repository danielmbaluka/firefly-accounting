package com.tri.erp.spring.controller;

import com.tri.erp.spring.commons.GlobalConstant;
import com.tri.erp.spring.commons.facade.AuthenticationFacade;
import com.tri.erp.spring.commons.helpers.ControllerHelper;
import com.tri.erp.spring.model.CheckConfig;
import com.tri.erp.spring.repo.CheckConfigRepo;
import com.tri.erp.spring.response.reports.CheckDto;
import com.tri.erp.spring.service.implementations.DownloadService;
import com.tri.erp.spring.service.interfaces.*;
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
 * Created by TSI Admin on 11/13/2014.
 */

@Controller
@RequestMapping(value = "/check-voucher")
public class CheckVoucherManagementController {

    @Autowired
    private AuthenticationFacade authenticationFacade;

    @Autowired
    private RoleService roleService;

    @Autowired
    @Qualifier("cvServiceImpl")
    PrintableVoucher printableVoucher;

    @Autowired
    @Qualifier("cvServiceImpl")
    PrintableCheque printableCheque;


    @Autowired
    @Qualifier("cvServiceImpl")
    Bir2307 bir2307;

    @Autowired
    private DownloadService downloadService;

    @Autowired
    CheckConfigRepo checkConfigRepo;

    @Autowired
    CvService cvService;

    private final String BASE_PATH = "cv/partials/";
    private final String MAIN = "cv/main";

    // view providers
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView index(HttpServletRequest request) {
        return ControllerHelper.getModelAndView(MAIN, roleService, authenticationFacade.getLoggedIn().getId(), request.getRequestURI());
    }

    @RequestMapping(value = "/new-cv-page", method = RequestMethod.GET)
    public String newCv(HttpServletRequest request) {
        Boolean hasPermissionOnMethod = roleService.isRouteAuthorized(authenticationFacade.getLoggedIn().getId(), request.getRequestURI());
        return hasPermissionOnMethod ? (BASE_PATH + "add-edit") : "403";
    }

    @RequestMapping(value = "/cv-details-page", method = RequestMethod.GET)
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

        String template = GlobalConstant.JASPER_BASE_PATH + "/vouchers/CheckVoucher.jrxml";
        downloadService.download(type, token, response, params, template, dataSource);
    }


    @RequestMapping(value = "/assign-check-modal", method = RequestMethod.GET)
    public String assignCheckModal(HttpServletRequest request) {
        return BASE_PATH + "assign-check-modal2";
    }

    @RequestMapping(value="/print-check-jas/{transId}/{bankAccountId}")
    public void printCheckJasper(@PathVariable Integer transId, @PathVariable Integer bankAccountId,
                           @RequestParam(value = "type") String type,
                           @RequestParam(value = "token") String token,
                           HttpServletResponse response, HttpServletRequest request) {


        HashMap params = printableCheque.reportParameters(transId, bankAccountId);

        String template = GlobalConstant.JASPER_BASE_PATH + "/vouchers/CheckForPrint.jrxml";
        downloadService.download(type, token, response, params, template, null);
    }

    @RequestMapping(value="/print-check/{transId}/{bankAccountId}")
    public ModelAndView printCheckHtml(@PathVariable Integer transId, @PathVariable Integer bankAccountId) {
        ModelAndView modelAndView = new ModelAndView(BASE_PATH + "print-check");
        CheckDto checkDto = cvService.findForPrintCheckDetails(transId, bankAccountId);

        if (checkDto != null) {
            CheckConfig config = checkConfigRepo.findOneByBankSegmentAccountId(bankAccountId);
            modelAndView.addObject("checkConfig", config);
            modelAndView.addObject("check", checkDto);
        } else {
            modelAndView.addObject("message", "Forbidden!");
        }
        return modelAndView;
    }

    @RequestMapping(value="/print-2307/{accountNo}/{transId}")
    public void print2307(@PathVariable Integer transId, @PathVariable Integer accountNo,
                          @RequestParam(value = "token") String token,
                          HttpServletResponse response, HttpServletRequest request) {

        bir2307.fillPdf(transId, accountNo, token, response);
    }
}
