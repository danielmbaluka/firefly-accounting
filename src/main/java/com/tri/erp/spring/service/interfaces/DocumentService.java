package com.tri.erp.spring.service.interfaces;

import com.tri.erp.spring.model.Document;
import com.tri.erp.spring.response.PostResponse;
import com.tri.erp.spring.response.ProcessDocumentDto;
import org.springframework.context.MessageSource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

/**
 * Created by TSI Admin on 5/19/2015.
 */
public interface DocumentService {
    @Transactional
    public PostResponse process(ProcessDocumentDto postData, BindingResult bindingResult, MessageSource messageSource);

    @Transactional
    public PostResponse processUpdate(Document v, BindingResult bindingResult, MessageSource messageSource);

    @Transactional
    public PostResponse processCreate(Document v, BindingResult bindingResult, MessageSource messageSource);
}
