package com.tri.erp.spring.validator;

import com.tri.erp.spring.model.AccountsPayableVoucher;
import com.tri.erp.spring.model.MaterialIssueRegister;
import com.tri.erp.spring.service.interfaces.ApvService;
import com.tri.erp.spring.service.interfaces.MaterialIssueRegisterService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by TSI Admin on 9/11/2014.
 */

@Component
public class MaterialIssueRegisterValidator implements Validator {

    private MaterialIssueRegisterService service;

    @Override
    public boolean supports(Class<?> aClass) {
        return MaterialIssueRegister.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        MaterialIssueRegister mir = (MaterialIssueRegister) o;
        CommonValidator.validateJournal(errors, mir.getTransaction(), mir.getGeneralLedgerLines(), mir.getSubLedgerLines());
    }

    public void setService(MaterialIssueRegisterService service) {
        this.service = service;
    }
}
