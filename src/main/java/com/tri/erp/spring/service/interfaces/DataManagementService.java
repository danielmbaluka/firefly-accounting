package com.tri.erp.spring.service.interfaces;

import com.tri.erp.spring.model.Document;
import com.tri.erp.spring.response.PostResponse;
import org.springframework.context.MessageSource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

/**
 * Created by TSI Admin on 5/19/2015.
 */
public interface DataManagementService {
    @Transactional
    public PostResponse processUpdate(Object entity, BindingResult bindingResult, MessageSource messageSource);

    @Transactional
    public PostResponse processCreate(Object entity, BindingResult bindingResult, MessageSource messageSource);
}
