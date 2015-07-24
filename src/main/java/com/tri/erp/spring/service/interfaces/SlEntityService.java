package com.tri.erp.spring.service.interfaces;

import com.tri.erp.spring.model.SlEntity;

import java.util.List;

public interface SlEntityService {
    public List<SlEntity> findAll();
    public List<SlEntity> findAllByType(Integer[] entityTypes);
}
