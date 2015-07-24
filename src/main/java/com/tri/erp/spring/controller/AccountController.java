package com.tri.erp.spring.controller;

import com.tri.erp.spring.response.PostResponse;
import com.tri.erp.spring.response.AccountDto;
import com.tri.erp.spring.model.Account;
import com.tri.erp.spring.response.SegmentAccountDto;
import com.tri.erp.spring.service.interfaces.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * Created by TSI Admin on 9/16/2014.
 */

@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    AccountService accountService;

    @Autowired
    MessageSource messageSource;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public AccountDto getAccount(@PathVariable Integer id) {
        AccountDto account = accountService.findById(id);
        return account;
    }

    @RequestMapping(value = "/{id}/except", method = RequestMethod.GET)
    @ResponseBody
    public List<Account> getAccountsExcept(@PathVariable Integer id) {
        return accountService.findByIdNotIn(id);
    }

    // create account
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public PostResponse createAccount(@Valid @RequestBody Account account, BindingResult bindingResult) {
        return accountService.processCreate(account, bindingResult, messageSource);
    }

    // update account
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public PostResponse updateAccount(@Valid @RequestBody Account account, BindingResult bindingResult) {
        return accountService.processUpdate(account, bindingResult, messageSource);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public List<AccountDto> accountList() {
        List<AccountDto> accountList = accountService.findAll();
        return accountList;
    }

    @RequestMapping(value = "/list/tree", method = RequestMethod.GET)
    @ResponseBody
    public List<AccountDto> accountListTree() {
        List<AccountDto> accountList = accountService.findAllTree();
        return accountList;
    }

    @RequestMapping(value = "/by-segment", method = RequestMethod.GET)
    @ResponseBody
    public List<SegmentAccountDto> getAccountBySegments(@RequestParam(value = "segmentIds") String[] segmentIds) {
        return accountService.findAllBySegment(segmentIds);
    }

    @RequestMapping(value = "/with-segment", method = RequestMethod.GET)
    @ResponseBody
    public List<AccountDto> getAccountWithSegments() {
        return accountService.findAllWithSegment();
    }


    @RequestMapping(value = "/wtax", method = RequestMethod.GET)
    @ResponseBody
    public Map getWTaxAccount() {
        return accountService.findWTax();
    }
}
