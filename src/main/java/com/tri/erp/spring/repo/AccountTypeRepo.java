package com.tri.erp.spring.repo;

import com.tri.erp.spring.model.Account;
import com.tri.erp.spring.model.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by TSI Admin on 9/9/2014.
 */
public interface AccountTypeRepo extends JpaRepository<AccountType, Integer> {

}
