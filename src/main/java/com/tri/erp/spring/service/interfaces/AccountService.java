package com.tri.erp.spring.service.interfaces;

import com.tri.erp.spring.response.PostResponse;
import com.tri.erp.spring.response.AccountDto;
import com.tri.erp.spring.model.Account;
import com.tri.erp.spring.response.SegmentAccountDto;
import org.springframework.context.MessageSource;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Map;

public interface AccountService {
    public Account create(Account account);
    public Account delete(int id);
    public List<AccountDto> findAll();
    public Account update(Account account);
    public AccountDto findById(int id);
    public Map findWTax();
    public PostResponse processCreate(Account account, BindingResult bindingResult, MessageSource messageSource);
    public PostResponse processUpdate(Account account, BindingResult bindingResult, MessageSource messageSource);
    public List<Account> findByTitle(String title);
    public List<Account> findByIdNotIn(Integer... accountId);
    public List<AccountDto> findAllTree();
    public List<SegmentAccountDto> findAllBySegment(String[] segmentIds);
    public List<AccountDto> findAllWithSegment();
}
