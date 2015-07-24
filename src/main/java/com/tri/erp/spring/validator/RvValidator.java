package com.tri.erp.spring.validator;

import com.tri.erp.spring.model.RequisitionVoucher;
import com.tri.erp.spring.service.interfaces.RequisitionVoucherService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by Personal on 4/22/2015.
 */

@Component
public class RvValidator implements Validator {

    private RequisitionVoucherService service;

    @Override
    public boolean supports(Class<?> aClass) {
        return RequisitionVoucher.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        RequisitionVoucher rv = (RequisitionVoucher) o;

    }

    public void setService(RequisitionVoucherService service) {
        this.service = service;
    }
}
