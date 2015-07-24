package com.tri.erp.spring.controller;

import com.tri.erp.spring.response.RvDetailDto;
import com.tri.erp.spring.service.interfaces.RvDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Personal on 4/29/2015.
 */
@Controller
@RequestMapping("/rv_detail")
public class RvDetailController {

    @Autowired
    RvDetailService rvDetailService;

    @RequestMapping(value = "/rvd/{rvId}", method = RequestMethod.GET)
    @ResponseBody
    public List<RvDetailDto> getRvDetails(@PathVariable Integer rvId, HttpServletRequest request) {
        return rvDetailService.getRvDetails(rvId);
    }

    @RequestMapping(value = "/rvd/status/{statusId}", method = RequestMethod.GET)
    @ResponseBody
    public List<RvDetailDto> getRvDetailsByStatus(@PathVariable Integer statusId, HttpServletRequest request) {
        return rvDetailService.getRvDetailsByStatus(statusId);
    }
}