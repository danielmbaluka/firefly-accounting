package com.tri.erp.spring.validator;

import com.tri.erp.spring.model.PurchaseOrder;
import com.tri.erp.spring.service.interfaces.PurchaseOrderService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by Personal on 5/15/2015.
 */
@Component
public class PoValidator implements Validator {

    private PurchaseOrderService service;

    @Override
    public boolean supports(Class<?> aClass) {
        return PurchaseOrder.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        PurchaseOrder po = (PurchaseOrder) o;

    }

    public void setService(PurchaseOrderService service) {
        this.service = service;
    }
}
