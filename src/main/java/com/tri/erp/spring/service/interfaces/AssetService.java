package com.tri.erp.spring.service.interfaces;

import com.tri.erp.spring.model.Asset;
import com.tri.erp.spring.response.PostResponse;
import org.springframework.context.MessageSource;
import org.springframework.validation.BindingResult;

import java.util.List;

/**
 * Created by TSI Admin.
 */
public interface AssetService {
    Asset findByRefNo(String refNo);

    Asset create(Asset asset);

    Asset findById(Integer id);

    PostResponse processUpdate(Asset asset, BindingResult bindingResult, MessageSource messageSource);

    List<Asset> findAll();

    PostResponse processCreate(Asset asset, BindingResult bindingResult, MessageSource messageSource);
}
