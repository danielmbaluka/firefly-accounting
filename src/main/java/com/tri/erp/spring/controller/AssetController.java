package com.tri.erp.spring.controller;

import com.tri.erp.spring.model.Asset;
import com.tri.erp.spring.response.PostResponse;
import com.tri.erp.spring.service.interfaces.AssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by TSI Admin.
 */
@Controller
@RequestMapping("/cpr")
public class AssetController {

    @Autowired
    MessageSource messageSource;

    @Autowired
    AssetService assetService;

    @RequestMapping(value = "/list")
    @ResponseBody
    public List<Asset> getAssets() {
        return assetService.findAll();
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public PostResponse createAsset(@Valid @RequestBody Asset asset, BindingResult bindingResult) {
        return assetService.processCreate(asset, bindingResult, messageSource);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Asset getAsset(@PathVariable Integer id) {
        return assetService.findById(id);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public PostResponse updateAsset(@Valid @RequestBody Asset asset, BindingResult bindingResult) {
        return assetService.processUpdate(asset, bindingResult, messageSource);
    }
}
