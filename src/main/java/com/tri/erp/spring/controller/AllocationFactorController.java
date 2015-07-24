package com.tri.erp.spring.controller;

import com.tri.erp.spring.commons.Debug;
import com.tri.erp.spring.model.AllocationFactor;
import com.tri.erp.spring.response.AllocationFactorDto;
import com.tri.erp.spring.response.CvDto;
import com.tri.erp.spring.response.PostResponse;
import com.tri.erp.spring.service.interfaces.AllocationFactorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by TSI Admin on 5/19/2015.
 */
@Controller
@RequestMapping(value = "/allocation-factor")
public class AllocationFactorController {

    @Autowired
    MessageSource messageSource;

    @Autowired
    AllocationFactorService allocationFactorService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public List<AllocationFactorDto> allFactorList() {
        return allocationFactorService.findAll();
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public PostResponse createAllFactor(@RequestBody AllocationFactorDto af, BindingResult bindingResult) {
        return allocationFactorService.processCreate(af, bindingResult, messageSource);
    }

    @RequestMapping(value = "/{accountId}/{effectId}", method = RequestMethod.GET)
    @ResponseBody
    public AllocationFactorDto getFactor(@PathVariable Integer accountId, @PathVariable Integer effectId, HttpServletRequest request) {
        return allocationFactorService.findByAccountAndEffectivityId(accountId, effectId);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public PostResponse updateAllFactor(@Valid @RequestBody AllocationFactorDto af, BindingResult bindingResult) {
        return allocationFactorService.processUpdate(af, bindingResult, messageSource);
    }

    @RequestMapping(value = "/date-ranges", method = RequestMethod.GET)
    @ResponseBody
    public List<Map> allDateRanges() {
        return allocationFactorService.findDateRanges();
    }

    @RequestMapping(value = "/{accountId}", method = RequestMethod.GET)
    @ResponseBody
    public List<Map> getLatestAccountFactor(@PathVariable Integer accountId, HttpServletRequest request) {
        return allocationFactorService.findLatestByAccount(accountId);
    }

    @RequestMapping(value = "/{accountId}/date/{voucherDate}", method = RequestMethod.GET)
    @ResponseBody
    public List<Map> getAccountFactorByLongDate(@PathVariable("accountId") Integer accountId, @PathVariable("voucherDate") String voucherDate, HttpServletRequest request) {
        return allocationFactorService.findAccountAndEffectDate(accountId, voucherDate);
    }

    @RequestMapping(value = "/test-auto-allocation", method = RequestMethod.POST)
    @ResponseBody
    public Map test(@RequestBody Map postData) {
        return allocationFactorService.testAutoAllocation(postData);
    }
}
