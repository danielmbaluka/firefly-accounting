package com.tri.erp.spring.service.interfaces;


import com.tri.erp.spring.model.UnitMeasure;
import com.tri.erp.spring.response.PostResponse;
import org.springframework.context.MessageSource;
import org.springframework.validation.BindingResult;

import java.util.List;

/**
 * Created by TSI Admin on 10/9/2014.
 */
public interface UnitMeasureService {
    public List<UnitMeasure> findAll();
    public UnitMeasure findById(Integer id);
    public PostResponse processUpdate(UnitMeasure unit, BindingResult bindingResult, MessageSource messageSource);
    public PostResponse processCreate(UnitMeasure unit, BindingResult bindingResult, MessageSource messageSource);
    public UnitMeasure findByCode(String str);
    public UnitMeasure findByDescription(String str);
}
