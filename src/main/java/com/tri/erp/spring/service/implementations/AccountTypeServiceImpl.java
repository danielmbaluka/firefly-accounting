package com.tri.erp.spring.service.implementations;

import com.tri.erp.spring.model.AccountType;
import com.tri.erp.spring.model.BusinessSegment;
import com.tri.erp.spring.repo.AccountTypeRepo;
import com.tri.erp.spring.repo.BusinessSegmentRepo;
import com.tri.erp.spring.service.interfaces.AccountTypeService;
import com.tri.erp.spring.service.interfaces.BusinessSegmentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by TSI Admin on 9/9/2014.
 */
@Service
public class AccountTypeServiceImpl implements AccountTypeService {
    @Resource
    AccountTypeRepo accountTypeRepo;

    @Override
    public AccountType create(AccountType o) {
        return null;
    }

    @Override
    public AccountType delete(Integer id) {
        return null;
    }

    @Override
    public List<AccountType> findAll() {
        return accountTypeRepo.findAll();
    }

    @Override
    public AccountType update(AccountType o) {
        return null;
    }

    @Override
    public AccountType findById(Integer id) {
        return null;
    }
}
