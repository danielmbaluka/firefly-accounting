package com.tri.erp.spring.commons.helpers;

import com.tri.erp.spring.commons.Debug;
import com.tri.erp.spring.repo.RouteRepo;
import com.tri.erp.spring.service.interfaces.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * Created by TSI Admin on 3/3/2015.
 */
public class ControllerHelper {

    @Autowired
    static RouteRepo routeRepo;

    public static ModelAndView getModelAndView(String viewName, RoleService roleService, Integer userId, String route) {
        ModelAndView modelAndView = new ModelAndView("403");

        Boolean authorized = roleService.isRouteAuthorized(userId, route);
        if (authorized) {
            Map<String, String> pageComponents = roleService.findPageComponentByRoute(userId, route);

            if (pageComponents != null && pageComponents.size() > 0) {
                modelAndView.addAllObjects(pageComponents);
            }
            modelAndView.setViewName(viewName);
        }

        return modelAndView;
    }
}
