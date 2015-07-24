package com.tri.erp.spring.controller;

import com.tri.erp.spring.commons.facade.AuthenticationFacade;
import com.tri.erp.spring.model.CashReceipts;
import com.tri.erp.spring.model.SalesVoucher;
import com.tri.erp.spring.response.*;
import com.tri.erp.spring.service.interfaces.CashReceiptsService;
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
@RequestMapping("/cash-receipts")
public class CashReceiptsController {

    @Autowired
    private AuthenticationFacade authenticationFacade;

    @Autowired
    private RoleService roleService;

    @Autowired
    MessageSource messageSource;

    @Autowired
    CashReceiptsService cashReceiptsService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public List<CashReceiptsListDto> salesVoucherList() {
        return cashReceiptsService.findAll();
    }

    @RequestMapping(value = "/list/{status}", method = RequestMethod.GET)
    @ResponseBody
    public List<CashReceiptsListDto> cashReceiptsListByStatus(@PathVariable Integer status) {
        return cashReceiptsService.findByStatusId(status);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public PostResponse createCashReceipts(@Valid @RequestBody CashReceipts cr, BindingResult bindingResult) {
        return cashReceiptsService.processCreate(cr, bindingResult, messageSource);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public CashReceiptsDto getCashReceipts(@PathVariable Integer id, HttpServletRequest request) {
        return cashReceiptsService.findById(id);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public PostResponse updateCashReceipts(@Valid @RequestBody CashReceipts cr, BindingResult bindingResult) {
        return cashReceiptsService.processUpdate(cr, bindingResult, messageSource);
    }

    @RequestMapping(value = "/process", method = RequestMethod.POST)
    @ResponseBody
    public PostResponse process(@RequestBody ProcessDocumentDto postData, BindingResult bindingResult) {
        return cashReceiptsService.process(postData, bindingResult, messageSource);
    }
}
