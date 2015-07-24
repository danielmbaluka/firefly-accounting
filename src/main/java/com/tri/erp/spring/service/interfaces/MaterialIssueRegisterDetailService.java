package com.tri.erp.spring.service.interfaces;

import com.tri.erp.spring.response.MaterialIssueRegisterDetailDto;

import java.util.List;

/**
 * Created by nsutgio2015 on 4/30/2015.
 */
public interface MaterialIssueRegisterDetailService {

    public List<MaterialIssueRegisterDetailDto> findByMaterialIssueRegisterId(Integer id);
}
