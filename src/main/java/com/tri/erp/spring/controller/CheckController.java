package com.tri.erp.spring.controller;

import com.tri.erp.spring.model.CheckConfig;
import com.tri.erp.spring.response.PostResponse;
import com.tri.erp.spring.service.interfaces.CheckConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by TSI Admin on 12/11/2014.
 */

@Controller
@RequestMapping(value = "/check")
public class CheckController {

    @Autowired
    MessageSource messageSource;

    @Autowired
    CheckConfigService checkConfigService;

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public PostResponse updateCheckConfig(@Valid @RequestBody CheckConfig config, BindingResult bindingResult) {
        return checkConfigService.processUpdate(config, bindingResult, messageSource);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public PostResponse createCheckConfig(@Valid @RequestBody CheckConfig config, BindingResult bindingResult) {
        return checkConfigService.processCreate(config, bindingResult, messageSource);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public CheckConfig getCheck(@PathVariable Integer id) {
        return checkConfigService.findById(id);
    }


    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public List<CheckConfig> getCheckConfigs() {
        return checkConfigService.findAll();
    }
}
