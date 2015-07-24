package com.tri.erp.spring.controller;

import com.tri.erp.spring.model.CheckVoucherCheque;
import com.tri.erp.spring.model.ReleasedCheque;
import com.tri.erp.spring.response.PostResponse;
import com.tri.erp.spring.service.interfaces.CheckReleasingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by TSI Admin on 6/19/2015.
 */


@Controller
@RequestMapping(value = "/check-releasing")
public class CheckReleasingController {

    @Autowired
    MessageSource messageSource;

    @Autowired
    CheckReleasingService checkReleasingService;

    private final String BASE_PATH = "check-releasing/partials/";
    private final String MAIN = "check-releasing/main";

    // views
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(HttpServletRequest request) {
        return MAIN;
    }

    @RequestMapping(value = "/release-page", method = RequestMethod.GET)
    public String releasePage(HttpServletRequest request) {
        return BASE_PATH + "release";
    }

    // actions
    @RequestMapping(value = "/release", method = RequestMethod.POST)
    @ResponseBody
    public PostResponse releaseCheck(@RequestBody ReleasedCheque cheque, BindingResult bindingResult) {
        return checkReleasingService.releaseCheck(cheque, bindingResult, messageSource);
    }
}
