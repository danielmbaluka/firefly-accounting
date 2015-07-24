package com.tri.erp.spring.commons.facade;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tri.erp.spring.commons.helpers.Checker;
import com.tri.erp.spring.model.*;
import com.tri.erp.spring.repo.*;
import com.tri.erp.spring.response.GeneralLedgerLineDto2;
import com.tri.erp.spring.response.SubLedgerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by TSI Admin on 5/1/2015.
 */

@Component
public class SettingFacadeImpl implements SettingFacade {

    @Resource
    private SettingRepo settingRepo;

    @Override
    public Map getByCode(String code) {
        Setting wTaxAccount = settingRepo.findOneByCode(code);
        String json = wTaxAccount.getValue();

        try {
            JsonFactory factory = new JsonFactory();
            ObjectMapper mapper = new ObjectMapper(factory);

            TypeReference<HashMap<String,Object>> typeRef = new TypeReference<HashMap<String,Object>>() {};
            HashMap<String,Object> o = mapper.readValue(json, typeRef);

            return o;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
