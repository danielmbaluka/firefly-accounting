/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tri.erp.spring.controller;

import com.tri.erp.spring.dtoers.WorkflowDtoer;
import com.tri.erp.spring.model.Page;
import com.tri.erp.spring.model.SlEntity;
import com.tri.erp.spring.model.TaxCode;
import com.tri.erp.spring.repo.TaxCodeRepo;
import com.tri.erp.spring.response.MenuDto;
import com.tri.erp.spring.response.PageComponentDto;
import com.tri.erp.spring.response.WorkflowActionsDto;
import com.tri.erp.spring.service.interfaces.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *
 * @author TSI Admin
 */

@Controller
@RequestMapping("/json")
public class AnyJsonController {

    @Autowired
    MenuService menuService;

    @Autowired
    PageService pageService;

    @Autowired
    SlEntityService slEntityService;

    @Autowired
    WorkflowDtoer workflowDtoer;

    @Autowired
    TaxCodeRepo taxCodeRepo;

    @RequestMapping(value = "/entities", method = RequestMethod.GET)
    @ResponseBody
    public List<SlEntity> getEntities(@RequestParam(value = "entityTypes", required = false) Integer[] entityTypes) {
        if (entityTypes != null && entityTypes.length > 0) {
            return slEntityService.findAllByType(entityTypes);
        } else {
            return slEntityService.findAll();
        }
    }

    @RequestMapping(value = "/menus", method = RequestMethod.GET)
    @ResponseBody
    public List<MenuDto> getMenus() {
        return menuService.findAll();
    }

    @RequestMapping(value = "/pages", method = RequestMethod.GET)
    @ResponseBody
    public List<Page> getPages() {
        return pageService.findAllWithComponents();
    }

    @RequestMapping(value = "/page-components/{pageId}", method = RequestMethod.GET)
    @ResponseBody
    public List<PageComponentDto> getPageComponents(@PathVariable Integer pageId) {
        return pageService.getPageComponents(pageId);
    }

    @RequestMapping(value = "/workflow-actions/{transId}", method = RequestMethod.GET)
    @ResponseBody
    public List<WorkflowActionsDto> getWorkflowActions(@PathVariable Integer transId) {
        return workflowDtoer.getWorkflowActionsDtoByWfId(transId);
    }

    @RequestMapping(value = "/tax-codes", method = RequestMethod.GET)
    @ResponseBody
    public List<TaxCode> getTaxCodes() {
        return taxCodeRepo.findAll();
    }
}
