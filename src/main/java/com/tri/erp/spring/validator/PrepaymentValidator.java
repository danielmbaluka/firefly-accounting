package com.tri.erp.spring.validator;

import com.tri.erp.spring.model.Prepayment;
import com.tri.erp.spring.service.interfaces.PrepaymentService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by Personal on 6/3/2015.
 */
@Component
public class PrepaymentValidator implements Validator {

    private PrepaymentService service;

    @Override
    public boolean supports(Class<?> aClass) {
        return Prepayment.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Prepayment pp = (Prepayment) o;

    }

    public void setService(PrepaymentService service) {
        this.service = service;
    }
}
