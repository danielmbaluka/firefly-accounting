package com.tri.erp.spring.controller;

import com.tri.erp.spring.model.Prepayment;
import com.tri.erp.spring.response.PostResponse;
import com.tri.erp.spring.response.PrepaymentDto;
import com.tri.erp.spring.response.PrepaymentListDto;
import com.tri.erp.spring.response.ProcessDocumentDto;
import com.tri.erp.spring.service.interfaces.PrepaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * Created by Personal on 6/3/2015.
 */
@Controller
@RequestMapping(value = "/pre-payment")
public class PrepaymentController {
    @Autowired
    PrepaymentService prepaymentService;

    @Autowired
    MessageSource messageSource;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public List<PrepaymentListDto> canvassList() {
        return prepaymentService.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public PrepaymentDto getPrepayment(@PathVariable Integer id, HttpServletRequest request) {
        return prepaymentService.findById(id);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public PostResponse createPrepayment(@Valid @RequestBody Prepayment pp, BindingResult bindingResult) {
        return prepaymentService.processCreate(pp, bindingResult, messageSource);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public PostResponse updatePrepayment(@Valid @RequestBody Prepayment pp, BindingResult bindingResult, HttpServletRequest request) {
        return prepaymentService.processUpdate(pp, bindingResult, messageSource);
    }

    @RequestMapping(value = "/process", method = RequestMethod.POST)
    @ResponseBody
    public PostResponse process(@RequestBody ProcessDocumentDto postData, BindingResult bindingResult) {
        return prepaymentService.process(postData, bindingResult, messageSource);
    }
}
