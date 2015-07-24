package com.tri.erp.spring.validator;

import com.tri.erp.spring.model.CheckConfig;
import com.tri.erp.spring.model.Item;
import com.tri.erp.spring.service.implementations.CheckConfigServiceImpl;
import com.tri.erp.spring.service.implementations.ItemServiceImpl;
import com.tri.erp.spring.service.interfaces.CheckConfigService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by TSI Admin on 9/11/2014.
 */

@Component
public class CheckConfigValidator implements Validator {

    private CheckConfigServiceImpl service;

    @Override
    public boolean supports(Class<?> aClass) {
        return CheckConfig.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        CheckConfig checkConfig = (CheckConfig) o;

        if (checkConfig.getWithSigner() == null) checkConfig.setWithSigner(true);
        if (checkConfig.getShowDesignation() == null) checkConfig.setShowDesignation(true);

        CheckConfig i = this.service.findByCode(checkConfig.getCode());
        // insert mode
        if (checkConfig.getId() == null || checkConfig.getId() == 0) {
            if (i != null) {
                errors.rejectValue("code", "check.code.taken");
            }
        } else {
            // update role
            if (i != null && i.getId() != checkConfig.getId()) { // diff check
                errors.rejectValue("code", "check.code.taken");
            }
        }
    }

    public void setService(CheckConfigServiceImpl service) {
        this.service = service;
    }
}
