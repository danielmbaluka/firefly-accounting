package com.tri.erp.spring.controller;

import com.tri.erp.spring.commons.facade.AuthenticationFacade;
import com.tri.erp.spring.model.AccountsPayableVoucher;
import com.tri.erp.spring.response.ApvDto;
import com.tri.erp.spring.response.ApvListDto;
import com.tri.erp.spring.response.PostResponse;
import com.tri.erp.spring.response.ProcessDocumentDto;
import com.tri.erp.spring.service.interfaces.ApvService;
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
@RequestMapping("/apv")
public class AccountsPayableVoucherController {

    @Autowired
    private AuthenticationFacade authenticationFacade;

    @Autowired
    private RoleService roleService;

    @Autowired
    MessageSource messageSource;

    @Autowired
    ApvService apvService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public List<ApvListDto> apvList() {
        return apvService.findAll();
    }

    @RequestMapping(value = "/list/{status}", method = RequestMethod.GET)
    @ResponseBody
    public List<ApvListDto> apvListByStatus(@PathVariable Integer status) {
        return apvService.findByStatusId(status);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public PostResponse createApv(@Valid @RequestBody AccountsPayableVoucher apv, BindingResult bindingResult) {
        return apvService.processCreate(apv, bindingResult, messageSource);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ApvDto getApv(@PathVariable Integer id, HttpServletRequest request) {
        return apvService.findById(id);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public PostResponse updateApv(@Valid @RequestBody AccountsPayableVoucher apv, BindingResult bindingResult) {
        return apvService.processUpdate(apv, bindingResult, messageSource);
    }

    @RequestMapping(value = "/process", method = RequestMethod.POST)
    @ResponseBody
    public PostResponse process(@RequestBody ProcessDocumentDto postData, BindingResult bindingResult) {
        return apvService.process(postData, bindingResult, messageSource);
    }
}
