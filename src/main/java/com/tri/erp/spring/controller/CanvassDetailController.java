package com.tri.erp.spring.controller;

import com.tri.erp.spring.response.CanvassDetailDto;
import com.tri.erp.spring.service.interfaces.CanvassDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Personal on 5/14/2015.
 */
@Controller
@RequestMapping("/canvass_detail")
public class CanvassDetailController {

    @Autowired
    CanvassDetailService canvassDetailService;

    @RequestMapping(value = "/cnvsd/{canvassId}", method = RequestMethod.GET)
    @ResponseBody
    public List<CanvassDetailDto> getCanvassDetails(@PathVariable Integer canvassId, HttpServletRequest request) {
        return canvassDetailService.getCanvassDetails(canvassId);
    }
}