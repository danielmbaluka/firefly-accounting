package com.tri.erp.spring.validator;

import com.tri.erp.spring.commons.helpers.Checker;
import com.tri.erp.spring.model.User;
import com.tri.erp.spring.repo.UserRepo;
import com.tri.erp.spring.service.implementations.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.Validator;

import java.util.List;

/**
 * Created by TSI Admin on 9/11/2014.
 */

@Component
public class UserValidator implements Validator {

    private UserServiceImpl service;

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;

        // insert mode
        if (user.getId() == null || user.getId() == 0) {
            errors = this.validatePasswordMatching(errors, user.getPassword(), user.getRetypePassword());
        } else {
            // has new password when updating user
            if (!Checker.isStringNullAndEmpty(user.getPassword()) || !Checker.isStringNullAndEmpty(user.getRetypePassword())) {
                errors = this.validatePasswordMatching(errors, user.getPassword(), user.getRetypePassword());
            }
        }

        // for email
        User u = this.service.findByEmail(user.getEmail());
        if (u != null && u.getId() != user.getId()) { // diff user
            errors.rejectValue("email", "user.email.taken");
        }

        // for username
        u = this.service.findByUsername(user.getUsername());
        if (u != null && u.getId() != user.getId()) { // diff user
            errors.rejectValue("username", "user.username.taken");
        }
    }

    private Errors validatePasswordMatching(Errors errors, String password, String retypePassword) {
        if (Checker.isStringNullAndEmpty(password)) {
            errors.rejectValue("password", "user.password.empty");
        } else if (password.length() < 10) {
            errors.rejectValue("password", "user.password.length.short");
        } else if (password.length() > 50) {
            errors.rejectValue("password", "user.password.length.long");
        } else {
            if (!(password.equals(retypePassword))) {
                errors.rejectValue("retypePassword", "user.password.mismatch");
            }
        }
        return errors;
    }

    public void setService(UserServiceImpl service) {
        this.service = service;
    }
}
