package com.tri.erp.spring.controller;

import com.tri.erp.spring.model.Supplier;
import com.tri.erp.spring.response.PostResponse;
import com.tri.erp.spring.service.interfaces.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by TSI Admin on 11/11/2014.
 */

@Controller
@RequestMapping(value = "/supplier")
public class SupplierController {

    @Autowired
    MessageSource messageSource;

    @Autowired
    SupplierService supplierService;

    @RequestMapping(value = "/list")
    @ResponseBody
    public List<Supplier> getSuppliers() {
        return supplierService.findAll();
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public PostResponse createSupplier(@Valid @RequestBody Supplier supplier, BindingResult bindingResult) {
        return supplierService.processCreate(supplier, bindingResult, messageSource);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Supplier getSupplier(@PathVariable Integer id) {
        return supplierService.findById(id);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public PostResponse updateSupplier(@Valid @RequestBody Supplier supplier, BindingResult bindingResult) {
        return supplierService.processUpdate(supplier, bindingResult, messageSource);
    }
}
