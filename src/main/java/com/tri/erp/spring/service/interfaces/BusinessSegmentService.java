package com.tri.erp.spring.service.interfaces;

import com.tri.erp.spring.model.BusinessSegment;

import java.util.List;

public interface BusinessSegmentService {
    public BusinessSegment create(BusinessSegment bs);
    public BusinessSegment delete(Integer id);
    public List<BusinessSegment> findAll();
    public BusinessSegment update(BusinessSegment bs);
    public BusinessSegment findById(Integer id);
}
