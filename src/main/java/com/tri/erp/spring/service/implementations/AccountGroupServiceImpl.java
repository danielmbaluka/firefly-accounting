package com.tri.erp.spring.service.implementations;

import com.tri.erp.spring.model.AccountGroup;
import com.tri.erp.spring.model.AccountType;
import com.tri.erp.spring.repo.AccountGroupRepo;
import com.tri.erp.spring.repo.AccountTypeRepo;
import com.tri.erp.spring.service.interfaces.AccountGroupService;
import com.tri.erp.spring.service.interfaces.AccountTypeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by TSI Admin on 9/9/2014.
 */
@Service
public class AccountGroupServiceImpl implements AccountGroupService {
    @Resource
    AccountGroupRepo accountGroupRepo;

    @Override
    public AccountGroup create(AccountGroup o) {
        return null;
    }

    @Override
    public AccountGroup delete(Integer id) {
        return null;
    }

    @Override
    public List<AccountGroup> findAll() {
        return accountGroupRepo.findAll();
    }

    @Override
    public AccountGroup update(AccountType o) {
        return null;
    }

    @Override
    public AccountGroup findById(Integer id) {
        return null;
    }
}
