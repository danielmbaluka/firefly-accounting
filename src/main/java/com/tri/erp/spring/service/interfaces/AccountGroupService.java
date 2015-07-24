package com.tri.erp.spring.service.interfaces;

import com.tri.erp.spring.model.AccountGroup;
import com.tri.erp.spring.model.AccountType;

import java.util.List;

public interface AccountGroupService {
    public AccountGroup create(AccountGroup o);
    public AccountGroup delete(Integer id);
    public List<AccountGroup> findAll();
    public AccountGroup update(AccountType o);
    public AccountGroup findById(Integer id);
}
