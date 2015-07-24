package com.tri.erp.spring.controller;

import com.tri.erp.spring.model.AccountType;
import com.tri.erp.spring.model.BusinessSegment;
import com.tri.erp.spring.service.interfaces.AccountService;
import com.tri.erp.spring.service.interfaces.AccountTypeService;
import com.tri.erp.spring.service.interfaces.BusinessSegmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by TSI Admin on 9/16/2014.
 */

@Controller
@RequestMapping("/bus-seg")
public class BusinessSegmentController {

    @Autowired
    BusinessSegmentService businessSegmentService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public List<BusinessSegment> getBusinessSegments() {
        return businessSegmentService.findAll();
    }

}
