package com.tri.erp.spring.controller;

import com.tri.erp.spring.response.StatusResponse;
import com.tri.erp.spring.service.implementations.DownloadService;
import com.tri.erp.spring.service.implementations.JasperDatasourceService;
import com.tri.erp.spring.service.implementations.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by TSI Admin on 5/11/2015.
 */

@Controller
@RequestMapping("/download")
public class DownloadController {

    @Autowired
    private DownloadService downloadService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private JasperDatasourceService datasource;

    @RequestMapping(value="/progress")
    public @ResponseBody
    StatusResponse checkDownloadProgress(@RequestParam String token) {
        return new StatusResponse(true, tokenService.check(token));
    }

    @RequestMapping(value="/token")
    public @ResponseBody StatusResponse getDownloadToken() {
        return new StatusResponse(true, tokenService.generate());
    }
}
