package com.tri.erp.spring.service.implementations;

import com.tri.erp.spring.commons.helpers.MessageFormatter;
import com.tri.erp.spring.model.CheckConfig;
import com.tri.erp.spring.repo.CheckConfigRepo;
import com.tri.erp.spring.response.PostResponse;
import com.tri.erp.spring.response.PostRoleResponse;
import com.tri.erp.spring.service.interfaces.CheckConfigService;
import com.tri.erp.spring.validator.CheckConfigValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import java.util.List;

/**
 * Created by TSI Admin on 12/8/2014.
 */

@Service
public class CheckConfigServiceImpl implements CheckConfigService {

    @Autowired
    CheckConfigRepo checkConfigRepo;

    @Override
    @Transactional(readOnly = true)
    public List<CheckConfig> findAll() {
        return checkConfigRepo.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public CheckConfig findById(Integer id) {
        return checkConfigRepo.findOne(id);
    }

    @Override
    public CheckConfig findByCode(String code) {
        return checkConfigRepo.findOneByCode(code);
    }

    @Override
    @Transactional
    public PostResponse processUpdate(CheckConfig config, BindingResult bindingResult, MessageSource messageSource) {
        return processCreate(config, bindingResult, messageSource);
    }

    @Override
    @Transactional
    public PostResponse processCreate(CheckConfig config, BindingResult bindingResult, MessageSource messageSource) {
        PostResponse response = new PostRoleResponse();
        MessageFormatter messageFormatter = new MessageFormatter(bindingResult, messageSource, response);

        CheckConfigValidator validator = new CheckConfigValidator();
        validator.setService(this);
        validator.validate(config, bindingResult);

        if (bindingResult.hasErrors()) {
            messageFormatter.buildErrorMessages();
            response = messageFormatter.getResponse();
            response.setSuccess(false);
        } else {
            config.setUpdatedAt(null);
            CheckConfig newCheck = checkConfigRepo.save(config);

            response.setModelId(newCheck.getId());
            response.setSuccessMessage("Check config successfully saved!");
            response.setSuccess(true);
        }
        return response;
    }
}
