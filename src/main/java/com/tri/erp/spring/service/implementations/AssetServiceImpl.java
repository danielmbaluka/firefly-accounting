package com.tri.erp.spring.service.implementations;

import com.tri.erp.spring.commons.facade.AuthenticationFacade;
import com.tri.erp.spring.commons.facade.GeneratorFacade;
import com.tri.erp.spring.commons.helpers.Checker;
import com.tri.erp.spring.commons.helpers.MessageFormatter;
import com.tri.erp.spring.model.Asset;
import com.tri.erp.spring.model.User;
import com.tri.erp.spring.repo.AssetRepo;
import com.tri.erp.spring.response.PostResponse;
import com.tri.erp.spring.service.interfaces.AssetService;
import com.tri.erp.spring.validator.AssetValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.List;

/**
 * Created by TSI Admin.
 */
@Service
public class AssetServiceImpl implements AssetService {

    @Autowired
    private AuthenticationFacade authenticationFacade;

    @Autowired
    AssetRepo assetRepo;

    @Autowired
    GeneratorFacade generatorFacade;

    @Override
    public Asset findByRefNo(String refNo) {
        List<Asset> assets = assetRepo.findByRefNo(refNo);

        if (!Checker.collectionIsEmpty(assets)) {
            return assets.get(0);
        } else return null;
    }

    @Override
    public Asset create(Asset asset) {
        return assetRepo.save(asset);
    }

    @Override
    public Asset findById(Integer id) {
        return assetRepo.findOne(id);
    }

    @Override
    public PostResponse processUpdate(Asset asset, BindingResult bindingResult, MessageSource messageSource) {
        return processCreate(asset, bindingResult, messageSource);
    }

    @Override
    public List<Asset> findAll() {
        return assetRepo.findAll();
    }

    @Override
    public PostResponse processCreate(Asset asset, BindingResult bindingResult, MessageSource messageSource) {
        PostResponse response = new PostResponse();
        MessageFormatter messageFormatter = new MessageFormatter(bindingResult, messageSource, response);
        AssetValidator validator = new AssetValidator();

        validator.setService(this);
        validator.validate(asset, bindingResult);

        if (bindingResult.hasErrors()) {
            messageFormatter.buildErrorMessages();

            response = messageFormatter.getResponse();

            response.setSuccess(false);
        } else {
            if (asset.getId() == null || asset.getId() == 0) {
                // When inserting.
                User createdBy = authenticationFacade.getLoggedIn();

                asset.setFkCreatedBy(createdBy);

                asset.setFkAccountNo(generatorFacade.entityAccountNumber());
            }

            Asset newAsset = this.create(asset);

            response.setModelId(newAsset.getId());
            response.setSuccessMessage("Continuing Property Record successfully saved!");
            response.setSuccess(true);
        }

        return response;
    }
}
