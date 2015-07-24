package com.tri.erp.spring.service.implementations;

import com.tri.erp.spring.commons.Debug;
import com.tri.erp.spring.model.SlEntity;
import com.tri.erp.spring.repo.SlEntityRepo;
import com.tri.erp.spring.service.interfaces.SlEntityService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * Created by TSI Admin on 9/9/2014.
 */
@Service
public class SlEntityServiceImpl implements SlEntityService {
    @Resource
    SlEntityRepo slEntityRepo;

    @Override
    public List<SlEntity> findAll() {
        return slEntityRepo.findAllByOrderByNameAsc();
    }

    @Override
    public List<SlEntity> findAllByType(Integer[] entityTypes) {
        return slEntityRepo.findByMarkers(Arrays.asList(entityTypes));
    }
}
