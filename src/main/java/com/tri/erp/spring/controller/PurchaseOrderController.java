package com.tri.erp.spring.controller;

import com.tri.erp.spring.model.PurchaseOrder;
import com.tri.erp.spring.response.PoDto;
import com.tri.erp.spring.response.PoListDto;
import com.tri.erp.spring.response.PostResponse;
import com.tri.erp.spring.response.ProcessDocumentDto;
import com.tri.erp.spring.service.interfaces.PurchaseOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * Created by Personal on 5/15/2015.
 */
@Controller
@RequestMapping(value = "/purchase-order")
public class PurchaseOrderController {
    @Autowired
    PurchaseOrderService purchaseOrderService;

    @Autowired
    MessageSource messageSource;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public List<PoListDto> canvassList() {
        return purchaseOrderService.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public PoDto getCanvass(@PathVariable Integer id, HttpServletRequest request) {
        return purchaseOrderService.findById(id);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public PostResponse createCanvass(@Valid @RequestBody PurchaseOrder purchaseOrder, BindingResult bindingResult) {
        return purchaseOrderService.processCreate(purchaseOrder, bindingResult, messageSource);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public PostResponse updateCanvass(@Valid @RequestBody PurchaseOrder purchaseOrder, BindingResult bindingResult, HttpServletRequest request) {
        return purchaseOrderService.processUpdate(purchaseOrder, bindingResult, messageSource);
    }

    @RequestMapping(value = "/process", method = RequestMethod.POST)
    @ResponseBody
    public PostResponse process(@RequestBody ProcessDocumentDto postData, BindingResult bindingResult) {
        return purchaseOrderService.process(postData, bindingResult, messageSource);
    }
}
