package com.tri.erp.spring.controller;

import com.tri.erp.spring.commons.Debug;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by TSI Admin on 9/9/2014.
 */
@Controller
@RequestMapping("/admin")
public class AdminDashboardController {

    @RequestMapping(value = {"/dashboard", "/"}, method = RequestMethod.GET)
    public String dashboard() {
        return "admin/dashboard";
    }

    @RequestMapping(value = {"/showcase"}, method = RequestMethod.GET)
    public String showcase() {
        return "admin/showcase";
    }
}
