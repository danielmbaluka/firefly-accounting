package com.tri.erp.spring.controller;

import com.tri.erp.spring.commons.facade.AuthenticationFacade;
import com.tri.erp.spring.model.BankDeposit;
import com.tri.erp.spring.model.SalesVoucher;
import com.tri.erp.spring.response.*;
import com.tri.erp.spring.service.interfaces.BankDepositService;
import com.tri.erp.spring.service.interfaces.RoleService;
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
@RequestMapping("/bank-deposit")
public class BankDepositController {

    @Autowired
    private AuthenticationFacade authenticationFacade;

    @Autowired
    private RoleService roleService;

    @Autowired
    MessageSource messageSource;

    @Autowired
    BankDepositService bankDepositService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public List<BankDepositListDto> bankDepositList() {
        return bankDepositService.findAll();
    }

    @RequestMapping(value = "/list/{status}", method = RequestMethod.GET)
    @ResponseBody
    public List<BankDepositListDto> bankDepositListByStatus(@PathVariable Integer status) {
        return bankDepositService.findByStatusId(status);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public PostResponse createBankDeposit(@Valid @RequestBody BankDeposit bankDeposit, BindingResult bindingResult) {
        return bankDepositService.processCreate(bankDeposit, bindingResult, messageSource);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public BankDepositDto getBankDeposit(@PathVariable Integer id, HttpServletRequest request) {
        return bankDepositService.findById(id);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public PostResponse updateBankDeposit(@Valid @RequestBody BankDeposit bankDeposit, BindingResult bindingResult) {
        return bankDepositService.processUpdate(bankDeposit, bindingResult, messageSource);
    }

    @RequestMapping(value = "/process", method = RequestMethod.POST)
    @ResponseBody
    public PostResponse process(@RequestBody ProcessDocumentDto postData, BindingResult bindingResult) {
        return bankDepositService.process(postData, bindingResult, messageSource);
    }
}
