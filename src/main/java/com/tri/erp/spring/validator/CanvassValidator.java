package com.tri.erp.spring.validator;

import com.tri.erp.spring.model.Canvass;
import com.tri.erp.spring.service.interfaces.CanvassService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by Personal on 5/12/2015.
 */
@Component
public class CanvassValidator implements Validator {

    private CanvassService service;

    @Override
    public boolean supports(Class<?> aClass) {
        return Canvass.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Canvass rv = (Canvass) o;

    }

    public void setService(CanvassService service) {
        this.service = service;
    }
}
