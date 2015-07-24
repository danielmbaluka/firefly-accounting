package com.tri.erp.spring.service.interfaces;

import com.tri.erp.spring.model.MaterialIssueRegister;
import com.tri.erp.spring.response.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by nsutgio2015 on 4/27/2015.
 */
public interface MaterialIssueRegisterService extends VoucherService {

    @Transactional
    public List<MaterialIssueRegisterDto> findAll();

    @Transactional(readOnly = true)
    public MaterialIssueRegisterDto findById(Integer id);

    @Transactional
    public  List<MaterialIssueRegisterDetailDto> getDetails(Integer id);

    @Transactional
    public  List<MaterialIssueRegisterDto> getAllMaterialIssueRegister();
}
