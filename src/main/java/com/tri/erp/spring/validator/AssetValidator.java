package com.tri.erp.spring.validator;

import com.tri.erp.spring.model.Asset;
import com.tri.erp.spring.service.implementations.AssetServiceImpl;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by TSI Admin.
 */
public class AssetValidator implements Validator {

    private AssetServiceImpl service;

    @Override
    public boolean supports(Class<?> aClass) {
        return Asset.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Asset asset = (Asset) o;
    }

    public void setService(AssetServiceImpl service) {
        this.service = service;
    }
}
