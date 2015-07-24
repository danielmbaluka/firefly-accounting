package com.tri.erp.spring.service.implementations;

import com.tri.erp.spring.model.*;
import com.tri.erp.spring.repo.*;
import com.tri.erp.spring.service.interfaces.BusinessSegmentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by TSI Admin on 9/9/2014.
 */
@Service
public class BusinessSegmentServiceImpl implements BusinessSegmentService {

    @Resource
    private BusinessSegmentRepo businessSegmentRepo;

    @Override
    public BusinessSegment create(BusinessSegment bs) {
        return null;
    }

    @Override
    public BusinessSegment delete(Integer id) {
        return null;
    }

    @Override
    public List<BusinessSegment> findAll() {
        return businessSegmentRepo.findAll();
    }

    @Override
    public BusinessSegment update(BusinessSegment bs) {
        return null;
    }

    @Override
    public BusinessSegment findById(Integer id) {
        return null;
    }
}
