package com.tri.erp.spring.controller;

import com.tri.erp.spring.model.Canvass;
import com.tri.erp.spring.response.CanvassDto;
import com.tri.erp.spring.response.CanvassListDto;
import com.tri.erp.spring.response.PostResponse;
import com.tri.erp.spring.response.ProcessDocumentDto;
import com.tri.erp.spring.service.interfaces.CanvassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * Created by Personal on 5/12/2015.
 */
@Controller
@RequestMapping(value = "/canvass-rv")
public class CanvassController {
    @Autowired
    CanvassService canvassService;

    @Autowired
    MessageSource messageSource;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public List<CanvassListDto> canvassList() {
        return canvassService.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public CanvassDto getCanvass(@PathVariable Integer id, HttpServletRequest request) {
        return canvassService.findById(id);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public PostResponse createCanvass(@Valid @RequestBody Canvass canvass, BindingResult bindingResult) {
        return canvassService.processCreate(canvass, bindingResult, messageSource);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public PostResponse updateCanvass(@Valid @RequestBody Canvass canvass, BindingResult bindingResult, HttpServletRequest request) {
        return canvassService.processUpdate(canvass, bindingResult, messageSource);
    }

    @RequestMapping(value = "/process", method = RequestMethod.POST)
    @ResponseBody
    public PostResponse process(@RequestBody ProcessDocumentDto postData, BindingResult bindingResult) {
        return canvassService.process(postData, bindingResult, messageSource);
    }
}
