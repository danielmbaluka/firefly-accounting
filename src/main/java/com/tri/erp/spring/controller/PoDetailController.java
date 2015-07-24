package com.tri.erp.spring.controller;

import com.tri.erp.spring.response.PoDetailDto;
import com.tri.erp.spring.service.interfaces.PoDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Personal on 5/15/2015.
 */
@Controller
@RequestMapping("/po_detail")
public class PoDetailController {

    @Autowired
    PoDetailService poDetailService;

    @RequestMapping(value = "/pod/{poId}", method = RequestMethod.GET)
    @ResponseBody
    public List<PoDetailDto> getCanvassDetails(@PathVariable Integer poId, HttpServletRequest request) {
        return poDetailService.getPoDetails(poId);
    }
}
