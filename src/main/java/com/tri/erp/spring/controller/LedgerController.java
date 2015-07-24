package com.tri.erp.spring.controller;

import com.tri.erp.spring.dtoers.LedgerDtoer;
import com.tri.erp.spring.response.GeneralLedgerLineDto;
import com.tri.erp.spring.response.GeneralLedgerLineDto2;
import com.tri.erp.spring.response.SubLedgerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by TSI Admin on 4/27/2015.
 */
@Controller
@RequestMapping("/ledger")
public class LedgerController {

    @Autowired
    LedgerDtoer ledgerDtoer;

    @RequestMapping(value = "/gl/{transId}", method = RequestMethod.GET)
    @ResponseBody
    public List<GeneralLedgerLineDto2> getGLEntries(@PathVariable Integer transId, HttpServletRequest request) {
        return ledgerDtoer.getGLAccountEntriesDtoByTrans(transId);
    }

    @RequestMapping(value = "/sl/{glId}", method = RequestMethod.GET)
    @ResponseBody
    public List<SubLedgerDto> getSLEntries(@PathVariable Integer glId, HttpServletRequest request) {
        return ledgerDtoer.getSLEntriesDtoByGl(glId);
    }

    @RequestMapping(value = "/sl/{transId}/{accountId}", method = RequestMethod.GET)
    @ResponseBody
    public List<SubLedgerDto> getSLEntriesGrouped(@PathVariable Integer transId, @PathVariable Integer accountId, HttpServletRequest request) {
        return ledgerDtoer.getSLEntriesDtoByTransAndAccount(transId, accountId);
    }

    @RequestMapping(value = "/gl/{transId}/{accountId}", method = RequestMethod.GET)
    @ResponseBody
    public List<Map> getGLEntriesByTransAndAccount(@PathVariable Integer transId, @PathVariable Integer accountId, HttpServletRequest request) {
        return ledgerDtoer.getGLEntriesDtoByTransAndAccount(transId, accountId);
    }

    @RequestMapping(value = "/temp/all", method = RequestMethod.GET)
    @ResponseBody
    public List<Map> getBatchTemp(HttpServletRequest request) {
        return ledgerDtoer.getAllTempBatches();
    }

    @RequestMapping(value = "/temp/gl/{tempBatchId}", method = RequestMethod.GET)
    @ResponseBody
    public List<GeneralLedgerLineDto2> getTempGLEntries(@PathVariable Integer tempBatchId, HttpServletRequest request) {
        return ledgerDtoer.getGLAccountEntriesDtoTempBatchId(tempBatchId);
    }

    @RequestMapping(value = "/temp/sl/{tempBatchId}/{accountId}", method = RequestMethod.GET)
    @ResponseBody
    public List<SubLedgerDto> getTempSLEntriesGrouped(@PathVariable Integer tempBatchId, @PathVariable Integer accountId, HttpServletRequest request) {
        return ledgerDtoer.getSLAccountEntriesDtoTempBatchId(tempBatchId, accountId);
    }
}
