package com.tri.erp.spring.controller;

import com.tri.erp.spring.commons.facade.AuthenticationFacade;
import com.tri.erp.spring.model.SalesVoucher;
import com.tri.erp.spring.response.*;
import com.tri.erp.spring.service.interfaces.RoleService;
import com.tri.erp.spring.service.interfaces.SalesVoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * Created by TSI Admin on 9/16/2014.
 */

@Controller
@RequestMapping("/sales-voucher")
public class SalesVoucherController {

    @Autowired
    private AuthenticationFacade authenticationFacade;

    @Autowired
    private RoleService roleService;

    @Autowired
    MessageSource messageSource;

    @Autowired
    SalesVoucherService salesVService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public List<SalesVoucherListDto> salesVoucherList() {
        return salesVService.findAll();
    }

    @RequestMapping(value = "/list/{status}", method = RequestMethod.GET)
    @ResponseBody
    public List<SalesVoucherListDto> salesVoucherListByStatus(@PathVariable Integer status) {
        return salesVService.findByStatusId(status);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public PostResponse createSalesVoucher(@Valid @RequestBody SalesVoucher sv, BindingResult bindingResult) {
        return salesVService.processCreate(sv, bindingResult, messageSource);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public SalesVoucherDto getSalesVoucher(@PathVariable Integer id, HttpServletRequest request) {
        return salesVService.findById(id);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public PostResponse updateSalesVoucher(@Valid @RequestBody SalesVoucher sv, BindingResult bindingResult) {
        return salesVService.processUpdate(sv, bindingResult, messageSource);
    }

    @RequestMapping(value = "/process", method = RequestMethod.POST)
    @ResponseBody
    public PostResponse process(@RequestBody ProcessDocumentDto postData, BindingResult bindingResult) {
        return salesVService.process(postData, bindingResult, messageSource);
    }
}
