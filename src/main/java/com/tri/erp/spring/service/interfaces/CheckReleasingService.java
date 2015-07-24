package com.tri.erp.spring.service.interfaces;

import com.tri.erp.spring.model.ReleasedCheque;
import com.tri.erp.spring.response.PostResponse;
import org.springframework.context.MessageSource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

/**
 * Created by TSI Admin on 6/19/2015.
 */
public interface CheckReleasingService {
    @Transactional
    public PostResponse releaseCheck(ReleasedCheque chequeToRelease, BindingResult bindingResult, MessageSource messageSource);
}
