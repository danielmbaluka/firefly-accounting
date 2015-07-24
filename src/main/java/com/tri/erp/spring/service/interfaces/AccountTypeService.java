package com.tri.erp.spring.service.interfaces;

import com.tri.erp.spring.model.AccountType;
import com.tri.erp.spring.model.BusinessSegment;

import java.util.List;

public interface AccountTypeService {
    public AccountType create(AccountType o);
    public AccountType delete(Integer id);
    public List<AccountType> findAll();
    public AccountType update(AccountType o);
    public AccountType findById(Integer id);
}
