package com.tri.erp.spring.validator;

import com.tri.erp.spring.commons.helpers.Checker;
import com.tri.erp.spring.model.Role;
import com.tri.erp.spring.model.User;
import com.tri.erp.spring.service.implementations.RoleServiceImpl;
import com.tri.erp.spring.service.implementations.UserServiceImpl;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by TSI Admin on 9/11/2014.
 */

@Component
public class RoleValidator implements Validator {

    private RoleServiceImpl service;

    @Override
    public boolean supports(Class<?> aClass) {
        return Role.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Role role = (Role) o;

        Role r = this.service.findByName(role.getName());
        // insert mode
        if (role.getId() == null || role.getId() == 0) {
            if (r != null) {
                errors.rejectValue("name", "role.name.taken");
            }
        } else {
            // update role
            if (r != null && r.getId() != role.getId()) { // diff role
                errors.rejectValue("name", "role.name.taken");
            }
        }
    }

    public void setService(RoleServiceImpl service) {
        this.service = service;
    }
}
