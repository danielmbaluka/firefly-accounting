package com.tri.erp.spring.controller;

import com.tri.erp.spring.model.CheckConfig;
import com.tri.erp.spring.service.interfaces.CheckConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by TSI Admin on 12/8/2014.
 */

@Controller
@RequestMapping(value = "/admin/unit-measures")
public class UnitOfMeasureManagementController {

    private final String BASE_PATH = "admin/unit/partials/";

    // view providers
    @RequestMapping(method = RequestMethod.GET)
    public String index() {
        return "admin/unit/main";
    }

    @RequestMapping(value = "/unit-details-page", method = RequestMethod.GET)
    public String unitDetails() {
        return BASE_PATH + "unit-details";
    }

    @RequestMapping(value = "/new-unit-page", method = RequestMethod.GET)
    public String newUnit() {
        return BASE_PATH + "add-edit-unit";
    }
}
