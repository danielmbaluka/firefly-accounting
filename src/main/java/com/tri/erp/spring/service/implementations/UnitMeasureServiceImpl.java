package com.tri.erp.spring.service.implementations;

import com.tri.erp.spring.commons.helpers.MessageFormatter;
import com.tri.erp.spring.model.UnitMeasure;
import com.tri.erp.spring.repo.UnitMeasureRepo;
import com.tri.erp.spring.response.PostResponse;
import com.tri.erp.spring.service.interfaces.UnitMeasureService;
import com.tri.erp.spring.validator.UnitMeasureValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import java.util.List;

/**
 * Created by TSI Admin on 11/11/2014.
 */

@Service
public class UnitMeasureServiceImpl implements UnitMeasureService {

    @Autowired
    UnitMeasureRepo unitMeasureRepo;

    @Override
    public List<UnitMeasure> findAll() {
        return unitMeasureRepo.findAll();
    }

    @Override
    public UnitMeasure findById(Integer id) {
        return unitMeasureRepo.findOne(id);
    }

    private UnitMeasure create(UnitMeasure unitMeasure) {
        return unitMeasureRepo.save(unitMeasure);
    }

    @Override
    public PostResponse processUpdate(UnitMeasure unit, BindingResult bindingResult, MessageSource messageSource) {
        return this.processCreate(unit, bindingResult, messageSource);
    }

    @Override
    @Transactional
    public PostResponse processCreate(UnitMeasure unit, BindingResult bindingResult, MessageSource messageSource) {
        PostResponse response = new PostResponse();
        MessageFormatter messageFormatter = new MessageFormatter(bindingResult, messageSource, response);

        UnitMeasureValidator validator = new UnitMeasureValidator();
        validator.setService(this);
        validator.validate(unit, bindingResult);

        if (bindingResult.hasErrors()) {
            messageFormatter.buildErrorMessages();
            response = messageFormatter.getResponse();
            response.setSuccess(false);
        } else {
            UnitMeasure newUnitMeasure = this.create(unit);
            response.setModelId(newUnitMeasure.getId());
            response.setSuccessMessage("Unit successfully saved!");
            response.setSuccess(true);
        }
        return response;
    }

    @Override
    public UnitMeasure findByCode(String str) {
        return unitMeasureRepo.findOneByCode(str);
    }

    @Override
    public UnitMeasure findByDescription(String str) {
        return unitMeasureRepo.findOneByDescription(str);
    }
}
