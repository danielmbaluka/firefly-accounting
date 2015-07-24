package com.tri.erp.spring.controller;

import com.tri.erp.spring.model.Page;
import com.tri.erp.spring.model.Role;
import com.tri.erp.spring.response.PostResponse;
import com.tri.erp.spring.response.PageComponentDto;
import com.tri.erp.spring.service.interfaces.PageService;
import com.tri.erp.spring.service.interfaces.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by TSI Admin on 11/5/2014.
 */

@Controller
@RequestMapping("/role")
public class RoleController {

    @Autowired
    RoleService roleService;

    @Autowired
    PageService pageService;

    @Autowired
    MessageSource messageSource;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public List<Role> roles() {
        return roleService.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Role getRole(@PathVariable Integer id) {
        return roleService.findById(id);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public PostResponse createRole(@Valid @RequestBody Role role, BindingResult bindingResult) {
        return roleService.processCreate(role, bindingResult, messageSource);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public PostResponse updateUser(@Valid @RequestBody Role role, BindingResult bindingResult) {
        return roleService.processUpdate(role, bindingResult, messageSource);
    }

    @RequestMapping(value = "/pages/{roleId}", method = RequestMethod.GET)
    @ResponseBody
    public List<Page> getRolePages(@PathVariable Integer roleId) {
        return pageService.findAllAssigned(roleId);
    }

    @RequestMapping(value = "/page-components/{roleId}/{pageId}", method = RequestMethod.GET)
    @ResponseBody
    public List<PageComponentDto> getRolePageComponent(@PathVariable Integer  roleId, @PathVariable Integer  pageId) {
        return pageService.getPageComponents(roleId, pageId);
    }

}
