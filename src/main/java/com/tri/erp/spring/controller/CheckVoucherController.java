package com.tri.erp.spring.controller;

import com.tri.erp.spring.dtoers.AccountingReportDtoer;
import com.tri.erp.spring.model.CheckVoucher;
import com.tri.erp.spring.model.CheckVoucherCheque;
import com.tri.erp.spring.response.*;
import com.tri.erp.spring.response.reports.CommonRegisterDetail;
import com.tri.erp.spring.service.interfaces.CvService;
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
@RequestMapping("/cv")
public class CheckVoucherController {
    @Autowired
    MessageSource messageSource;

    @Autowired
    CvService cvService;

    @Autowired
    AccountingReportDtoer accountingReportDtoer;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public List<CvListDto> apvList() {
        return cvService.findAll();
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public PostResponse createCv(@Valid @RequestBody CheckVoucher cv, BindingResult bindingResult) {
        return cvService.processCreate(cv, bindingResult, messageSource);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public CvDto getCv(@PathVariable Integer id, HttpServletRequest request) {
        return cvService.findById(id);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public PostResponse updateCv(@Valid @RequestBody CheckVoucher cv, BindingResult bindingResult) {
        return cvService.processUpdate(cv, bindingResult, messageSource);
    }

    @RequestMapping(value = "/process", method = RequestMethod.POST)
    @ResponseBody
    public PostResponse process(@RequestBody ProcessDocumentDto postData, BindingResult bindingResult) {
        return cvService.process(postData, bindingResult, messageSource);
    }

    @RequestMapping(value = "/update-check", method = RequestMethod.POST)
    @ResponseBody
    public PostResponse updateCheck(@RequestBody CheckVoucherCheque cheque, BindingResult bindingResult) {
        return cvService.updateCheckNumber(cheque, bindingResult, messageSource);
    }

    @RequestMapping(value = "/checks/{transId}", method = RequestMethod.GET)
    @ResponseBody
    public List<java.util.Map> getChecks(@PathVariable Integer transId) {
        return cvService.findCvChecks(transId);
    }

    @RequestMapping(value = "/for-check-releasing", method = RequestMethod.GET)
    @ResponseBody
    public List<java.util.Map> getForCheckReleasing() {
        return cvService.findCheckVouchersForReleasing();
    }

    @RequestMapping(value = "/checks-for-releasing/{transId}", method = RequestMethod.GET)
    @ResponseBody
    public List<java.util.Map> getChecksForReleasing(@PathVariable Integer transId) {
        return cvService.findChequeNumbersForCheckReleasing(transId);
    }

    @RequestMapping(value = "/register/{from}/{to}", method = RequestMethod.GET)
    @ResponseBody
    public List<CommonRegisterDetail> register(@PathVariable String from, @PathVariable String to) {
        return accountingReportDtoer.getForCVRegister(from, to);
    }
}
