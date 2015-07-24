package com.tri.erp.spring.controller;

import com.tri.erp.spring.commons.facade.AuthenticationFacade;
import com.tri.erp.spring.response.MaterialIssueRegisterDto;
import com.tri.erp.spring.response.PostResponse;
import com.tri.erp.spring.response.ProcessDocumentDto;
import com.tri.erp.spring.service.interfaces.MaterialIssueRegisterService;
import com.tri.erp.spring.service.interfaces.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by nsutgio2015 on 3/16/2015.
 */
@Controller
@RequestMapping(value = "/mir")
public class MaterialIssueRegisterController {

    @Autowired
    private AuthenticationFacade authenticationFacade;

    @Autowired
    private RoleService roleService;

    @Autowired
    MessageSource messageSource;

    @Autowired
    MaterialIssueRegisterService mirService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public List<MaterialIssueRegisterDto> mirList() {
        List<MaterialIssueRegisterDto> ret = mirService.getAllMaterialIssueRegister();
        return ret;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public MaterialIssueRegisterDto getMIR(@PathVariable Integer id) {
        MaterialIssueRegisterDto ret = mirService.findById(id);
        return ret;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public PostResponse postToMIR(@Valid @RequestBody MaterialIssueRegisterDto mirDto, BindingResult bindingResult) {
        return  mirService.processCreate(mirDto.toModel(), bindingResult, messageSource);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public PostResponse updateMir(@Valid @RequestBody MaterialIssueRegisterDto mirDto, BindingResult bindingResult) {
        return  mirService.processUpdate(mirDto.toModel(), bindingResult, messageSource);
    }

    @RequestMapping(value = "/process", method = RequestMethod.POST)
    @ResponseBody
    public PostResponse process(@RequestBody ProcessDocumentDto postData, BindingResult bindingResult) {
        return mirService.process(postData, bindingResult, messageSource);
    }
}
