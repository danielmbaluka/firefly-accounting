package com.tri.erp.spring.controller;

import com.tri.erp.spring.model.UnitMeasure;
import com.tri.erp.spring.response.PostResponse;
import com.tri.erp.spring.service.interfaces.UnitMeasureService;
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
@RequestMapping(value = "/unit")
public class UnitController {

    @Autowired
    MessageSource messageSource;

    @Autowired
    UnitMeasureService unitMeasureService;

    @RequestMapping(value = "/list")
    @ResponseBody
    public List<UnitMeasure> getUnits() {
        return unitMeasureService.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public UnitMeasure getUnit(@PathVariable Integer id) {
        return unitMeasureService.findById(id);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public PostResponse updateUnit(@Valid @RequestBody UnitMeasure unitMeasure, BindingResult bindingResult) {
        return unitMeasureService.processUpdate(unitMeasure, bindingResult, messageSource);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public PostResponse createUnit(@Valid @RequestBody UnitMeasure unitMeasure, BindingResult bindingResult) {
        return unitMeasureService.processCreate(unitMeasure, bindingResult, messageSource);
    }
}
