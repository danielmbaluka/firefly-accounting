package com.tri.erp.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by TSI Admin on 11/11/2014.
 */

@Controller
@RequestMapping(value = "/admin/supplier")
public class SupplierManagementController {

    private final String BASE_PATH = "admin/supplier/partials/";

    // view providers
    @RequestMapping(method = RequestMethod.GET)
    public String index() {
        return "admin/supplier/main";
    }

    @RequestMapping(value = "/supplier-list-page", method = RequestMethod.GET)
    public String getSupplierListPage() {
        return BASE_PATH + "supplier-list";
    }

    @RequestMapping(value = "/new-supplier-page", method = RequestMethod.GET)
    public String newSupplier() {
        return BASE_PATH + "add-edit-supplier";
    }

    @RequestMapping(value = "/supplier-details-page", method = RequestMethod.GET)
    public String supplierDetails() {
        return BASE_PATH + "supplier-details";
    }

}
