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
 * Created by TSI Admin on 11/13/2014.
 */

@Controller
@RequestMapping(value = "/admin/allocation-factor")
public class AllocationFactorManagementController {

    @Autowired
    private AuthenticationFacade authenticationFacade;

    @Autowired
    private RoleService roleService;

    private final String BASE_PATH = "admin/allfact/partials/";
    private final String MAIN = "admin/allfact/main";

    // view providers
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView index(HttpServletRequest request) {
        return ControllerHelper.getModelAndView(MAIN, roleService, authenticationFacade.getLoggedIn().getId(),request.getRequestURI());
    }

    @RequestMapping(value = "/factors-list-page", method = RequestMethod.GET)
    public String getFactorListPage() {
        return BASE_PATH + "factor-list";
    }

    @RequestMapping(value = "/new-factor-page", method = RequestMethod.GET)
    public String newAllocationFactor(HttpServletRequest request) {
        Boolean hasPermissionOnMethod = roleService.isRouteAuthorized(authenticationFacade.getLoggedIn().getId(), request.getRequestURI());
        return hasPermissionOnMethod ? (BASE_PATH + "add-edit") : "403";
    }

    @RequestMapping(value = "/factor-details-page", method = RequestMethod.GET)
    public ModelAndView factorDetails(HttpServletRequest request) {
        return ControllerHelper.getModelAndView(BASE_PATH + "details", roleService, authenticationFacade.getLoggedIn().getId(), request.getRequestURI());
    }
}
