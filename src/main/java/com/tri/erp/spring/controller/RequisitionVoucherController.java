package com.tri.erp.spring.controller;

import com.tri.erp.spring.commons.facade.AuthenticationFacade;
import com.tri.erp.spring.model.RequisitionVoucher;
import com.tri.erp.spring.response.PostResponse;
import com.tri.erp.spring.response.ProcessDocumentDto;
import com.tri.erp.spring.response.RvDto;
import com.tri.erp.spring.response.RvListDto;
import com.tri.erp.spring.service.interfaces.RequisitionVoucherService;
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
 * Created by Personal on 3/20/2015.
 */
@Controller
@RequestMapping(value = "/requisition-voucher")
public class RequisitionVoucherController {

    @Autowired
    private AuthenticationFacade authenticationFacade;

    @Autowired
    RequisitionVoucherService requisitionVoucherService;

    @Autowired
    private RoleService roleService;

    @Autowired
    MessageSource messageSource;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public List<RvListDto> rvList() {
        return requisitionVoucherService.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public RvDto getRequisitionVoucher(@PathVariable Integer id, HttpServletRequest request) {
        return requisitionVoucherService.findByRvId(id);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public PostResponse createRv(@Valid @RequestBody RequisitionVoucher rv, BindingResult bindingResult) {
        return requisitionVoucherService.processCreate(rv, bindingResult, messageSource);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public PostResponse updateRv(@Valid @RequestBody RequisitionVoucher rv, BindingResult bindingResult, HttpServletRequest request) {
        return requisitionVoucherService.processUpdate(rv, bindingResult, messageSource);
    }

    @RequestMapping(value = "/process", method = RequestMethod.POST)
    @ResponseBody
    public PostResponse process(@RequestBody ProcessDocumentDto postData, BindingResult bindingResult) {
        return requisitionVoucherService.process(postData, bindingResult, messageSource);
    }
}
