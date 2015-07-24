package com.tri.erp.spring.service.interfaces;

import com.tri.erp.spring.response.ApvDto;
import com.tri.erp.spring.response.ApvListDto;
import com.tri.erp.spring.service.interfaces.VoucherService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

public interface JvService extends VoucherService {

    public List<Map> findAll();
    public Map findById(Integer id);
}
