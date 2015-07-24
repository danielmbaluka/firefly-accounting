package com.tri.erp.spring.controller;

import com.tri.erp.spring.commons.facade.AuthenticationFacade;
import com.tri.erp.spring.commons.helpers.ControllerHelper;
import com.tri.erp.spring.service.interfaces.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by TSI Admin on 3/30/2015.
 */
@Controller
@RequestMapping(value = "/petty-cash-voucher")
public class PettyCashVoucherManagementController {

    private final String BASE_PATH = "pcv/partials/";
    private final String MAIN = "pcv/main";
    @Autowired
    private AuthenticationFacade authenticationFacade;
    @Autowired
    private RoleService roleService;

    // view providers
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView index(HttpServletRequest request) {
        return ControllerHelper.getModelAndView(MAIN, roleService, authenticationFacade.getLoggedIn().getId(), request.getRequestURI());
    }

    @RequestMapping(value = "/new-pcv-page", method = RequestMethod.GET)
    public String newPcv(HttpServletRequest request) {
        Boolean hasPermissionOnMethod = roleService.isRouteAuthorized(authenticationFacade.getLoggedIn().getId(), request.getRequestURI());
        return hasPermissionOnMethod ? (BASE_PATH + "add-edit") : "403";
    }
}
