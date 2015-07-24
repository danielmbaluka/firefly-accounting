package com.tri.erp.spring.controller;

import com.tri.erp.spring.model.AccountGroup;
import com.tri.erp.spring.service.interfaces.AccountGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by TSI Admin on 9/16/2014.
 */

@Controller
@RequestMapping("/account/group")
public class AccountGroupController {

    @Autowired
    AccountGroupService accountGroupService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public List<AccountGroup> getAccountGroups() {
        return accountGroupService.findAll();
    }

}
