package com.tri.erp.spring.controller;

import com.tri.erp.spring.model.CheckConfig; 
import com.tri.erp.spring.response.reports.CheckDto;
import com.tri.erp.spring.service.interfaces.CheckConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by TSI Admin on 12/8/2014.
 */

@Controller
@RequestMapping(value = "/admin/check")
public class CheckConfigController {

    @Autowired
    CheckConfigService checkConfigService;

    private final String BASE_PATH = "admin/check/partials/";

    // view providers
    @RequestMapping(method = RequestMethod.GET)
    public String index() {
        return "admin/check/main";
    }

    @RequestMapping(value = "/check-details-page", method = RequestMethod.GET)
    public String getCheckDetailsPage() {
        return BASE_PATH + "check-details";
    }

    @RequestMapping(value = "/edit-check-page", method = RequestMethod.GET)
    public String getAddCheckPage() {
        return BASE_PATH + "add-edit-check";
    }

    @RequestMapping(value = "/print-check-page/{checkId}", method = RequestMethod.GET)
    public ModelAndView getPrintCheckPage(@PathVariable Integer checkId) {

        ModelAndView modelAndView = new ModelAndView(BASE_PATH + "print-preview-check");

        CheckDto check = new CheckDto();
        check.setCheckNumber("xxxxxx");
        check.setAlphaAmount("One million six hundred fifty three thousand five hundred thirty four and 54/100");
        check.setDate("May 15, 2015");
        check.setNumericAmount("1,653,534.54");
        check.setPayee("Justin Bieber");
        check.setSig1("John Doe");
        check.setSig2("Foo Bar");

        CheckConfig checkConfig = checkConfigService.findById(checkId);
        modelAndView.addObject("checkConfig", checkConfig);
        modelAndView.addObject("check", check);

        return modelAndView;
    }

}
