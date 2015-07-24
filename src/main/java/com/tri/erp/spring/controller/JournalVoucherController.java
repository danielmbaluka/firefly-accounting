package com.tri.erp.spring.controller;

import com.tri.erp.spring.dtoers.AccountingReportDtoer;
import com.tri.erp.spring.model.JournalVoucher;
import com.tri.erp.spring.response.PostResponse;
import com.tri.erp.spring.response.ProcessDocumentDto;
import com.tri.erp.spring.response.reports.CommonRegisterDetail;
import com.tri.erp.spring.service.interfaces.JvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * Created by TSI Admin on 6/30/2015.
 */
@Controller
@RequestMapping(value = "/jv")
public class JournalVoucherController {

    @Autowired
    MessageSource messageSource;

    @Autowired
    JvService jvService;

    @Autowired
    AccountingReportDtoer accountingReportDtoer;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public List<Map> jvList() {
        return jvService.findAll();
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public PostResponse createJv(@Valid @RequestBody JournalVoucher jv, BindingResult bindingResult) {
        return jvService.processCreate(jv, bindingResult, messageSource);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Map getJv(@PathVariable Integer id, HttpServletRequest request) {
        return jvService.findById(id);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public PostResponse updateJv(@Valid @RequestBody JournalVoucher jv, BindingResult bindingResult) {
        return jvService.processUpdate(jv, bindingResult, messageSource);
    }

    @RequestMapping(value = "/process", method = RequestMethod.POST)
    @ResponseBody
    public PostResponse process(@RequestBody ProcessDocumentDto postData, BindingResult bindingResult) {
        return jvService.process(postData, bindingResult, messageSource);
    }

    @RequestMapping(value = "/register/{from}/{to}", method = RequestMethod.GET)
    @ResponseBody
    public List<CommonRegisterDetail> register(@PathVariable String from, @PathVariable String to) {
        return accountingReportDtoer.getForJVRegister(from, to);
    }
}
