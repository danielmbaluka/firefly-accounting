package com.tri.erp.spring.validator;

import com.tri.erp.spring.model.Role;
import com.tri.erp.spring.model.Supplier;
import com.tri.erp.spring.service.implementations.RoleServiceImpl;
import com.tri.erp.spring.service.implementations.SupplierServiceImpl;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by TSI Admin on 9/11/2014.
 */

@Component
public class SupplierValidator implements Validator {

    private SupplierServiceImpl service;

    @Override
    public boolean supports(Class<?> aClass) {
        return Supplier.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Supplier supplier = (Supplier) o;

        Supplier r = this.service.findByName(supplier.getName());
        // insert mode
        if (supplier.getId() == null || supplier.getId() == 0) {
            if (r != null) {
                errors.rejectValue("name", "supplier.name.taken");
            }
        } else {
            // update supplier
            if (r != null && r.getId() != supplier.getId()) { // diff supplier
                errors.rejectValue("name", "supplier.name.taken");
            }
        }
    }

    public void setService(SupplierServiceImpl service) {
        this.service = service;
    }
}
