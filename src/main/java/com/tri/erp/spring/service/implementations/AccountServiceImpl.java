package com.tri.erp.spring.service.implementations;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tri.erp.spring.commons.Debug;
import com.tri.erp.spring.commons.facade.SettingFacade;
import com.tri.erp.spring.model.enums.SettingCode;
import com.tri.erp.spring.response.PostAccountResponse;
import com.tri.erp.spring.response.PostResponse;
import com.tri.erp.spring.commons.helpers.Checker;
import com.tri.erp.spring.commons.helpers.MessageFormatter;
import com.tri.erp.spring.commons.helpers.StringFormatter;
import com.tri.erp.spring.response.AccountDto;
import com.tri.erp.spring.model.*;
import com.tri.erp.spring.repo.*;
import com.tri.erp.spring.response.SegmentAccountDto;
import com.tri.erp.spring.service.interfaces.AccountService;
import com.tri.erp.spring.validator.AccountValidator;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.IOException;
import java.util.*;

/**
 * Created by TSI Admin on 9/9/2014.
 */
@Service
public class AccountServiceImpl implements AccountService {
    private List<AccountDto> accountsDtoList;

    @Resource
    private AccountRepo accountRepo;

    @Resource
    private SettingFacade settingFacade;

    @Resource
    private AccountTypeRepo accountTypeRepo;

    @Resource
    private AccountGroupRepo accountGroupRepo;

    @Resource
    private SegmentAccountRepo segmentAccountRepo;

    @Resource
    private BusinessSegmentRepo businessSegmentRepo;

    @PersistenceContext
    private EntityManager em;

    @Override
    public Account create(Account account) {
        return accountRepo.save(account);
    }

    @Override
    public Account delete(int id) {
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "accountsCache")
    public List<AccountDto> findAll() {
        this.accountsDtoList = new ArrayList<>();

        List<Account> accountList = accountRepo.findAllByOrderByTitleAsc();

        for(Account account : accountList) {
            AccountDto accountDto = new AccountDto();
            accountDto.setCode(account.getCode());
            accountDto.setParentAccountId(account.getParentAccountId());
            accountDto.setId(account.getId());
            accountDto.setTitle(account.getTitle());
            accountDto.setLevel(account.getLevel());
            if (account.getAccountType() != null) {
                accountDto.setAccountType(account.getAccountType());
            }
            accountsDtoList.add(accountDto);
        }
        return accountsDtoList;
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "accountsTreeCache")
    public List<AccountDto> findAllTree() {
        this.accountsDtoList = new ArrayList<>();

        List<Account> topLevelAccounts = accountRepo.findByParentAccountIdOrderByCodeAsc(0);// top level accounts
        for(Account account : topLevelAccounts) {
            AccountDto accountDto = new AccountDto();
            accountDto.setCode(account.getCode());
            accountDto.setId(account.getId());
            accountDto.setTitle(account.getTitle());
            if (account.getAccountType() != null) {
                accountDto.setAccountType(account.getAccountType());
            }
            this.accountsDtoList.add(accountDto);
            findDescendants(account);
        }
        return this.accountsDtoList;
    }

    @Override
    public List<SegmentAccountDto> findAllBySegment(String[] segmentIds) {
        List<SegmentAccountDto> list = new ArrayList<>();
        if (segmentIds != null && segmentIds.length > 0) {
            List<Object[]> result = accountRepo.findBySegmentIds(Arrays.asList(segmentIds));
            if (result != null) {
                for(Object[] objects : result) {
                    SegmentAccountDto a = new SegmentAccountDto();
                    a.setAccountId((Integer) objects[0]);
                    a.setTitle((String) objects[1]);
                    a.setSegmentAccountId((Integer) objects[2]);
                    a.setSegmentAccountCode((String) objects[3]);

                    AccountType at = new AccountType();
                    at.setId((Integer) objects[4]);
                    at.setDescription((String) objects[5]);
                    a.setAccountType(at);

                    list.add(a);
                }
            }
        }
        return list;
    }

    @Override
    public List<AccountDto> findAllWithSegment() {
        List<AccountDto> list = new ArrayList<>();

        List<Object[]> result = accountRepo.findAllWithSegment();
        if (result != null) {
            for(Object[] objects : result) {
                AccountDto a = new AccountDto();
                a.setId((Integer) objects[0]);
                a.setTitle((String) objects[1]);
                a.setCode((String) objects[2]);

                AccountType at = new AccountType();
                at.setId((Integer) objects[3]);
                at.setDescription((String) objects[4]);
                a.setAccountType(at);

                list.add(a);
            }
        }
        return list;
    }

    @Override
    public Account update(Account account) {
        return accountRepo.save(account);
    }

    @Override
    @Transactional(readOnly = true)
    public AccountDto findById(int id) {
        AccountDto accountDto = new AccountDto();

        Account account = accountRepo.findOne(id);
        if (account != null) {
            accountDto.setId(account.getId());
            accountDto.setTitle(account.getTitle());
            accountDto.setCode(account.getCode());
            if (account.getAccountType() != null) {
                accountDto.setAccountType(account.getAccountType());
            }
            accountDto.setLevel(account.getLevel());
            accountDto.setAuxAccount(account.getAuxiliaryAccount());
            accountDto.setGLAccount(account.getGLAccount());
            if (account.getAccountGroup() != null) {
                accountDto.setAccountGroup(account.getAccountGroup());
            }
            accountDto.hasSL(account.hasSL());
            accountDto.isActive(account.isActive());

            Account parentAccount = accountRepo.findOne(account.getParentAccountId());
            if (parentAccount != null) {
                accountDto.setParentAccount(parentAccount);
            }

            List<SegmentAccount> segmentAccounts = segmentAccountRepo.findByAccountId(account.getId());

            accountDto.setSegmentAccounts(segmentAccounts);
            accountDto.setSLAccount(account.getSLAccount());
            accountDto.setNormalBalance(account.getNormalBalance());
            accountDto.setIsHeader(account.getIsHeader());
        }
        return accountDto;
    }

    @Override
    public Map findWTax() {
        return settingFacade.getByCode(SettingCode.WTAX_ACCOUNT.toString());
    }

    @Transactional
    @CacheEvict(value =  {"accountsTreeCache", "accountsCache"}, allEntries = true)
    public PostResponse processCreate(Account account, BindingResult bindingResult, MessageSource messageSource) {
        PostResponse response = new PostAccountResponse();
        MessageFormatter messageFormatter = new MessageFormatter(bindingResult, messageSource, response);

        AccountValidator accountValidator = new AccountValidator();
        accountValidator.setService(this);
        accountValidator.validate(account, bindingResult);

        if (bindingResult.hasErrors()) {
            messageFormatter.buildErrorMessages();
            response = messageFormatter.getResponse();
            response.setSuccess(false);
        } else {

            AccountType accountType = accountTypeRepo.findOne(account.getAccountType().getId());
            AccountGroup accountGroup = accountGroupRepo.findOne(account.getAccountGroup().getId());

            account.setTitle(StringFormatter.ucFirst(account.getTitle()));
            account.setCode(StringFormatter
                    .buildAccountCode(
                            accountType.getCode(),
                            accountGroup.getCode(),
                            account.getGLAccount(),
                            account.getSLAccount(),
                            account.getAuxiliaryAccount()
                    )
            );

            if (account.getParentAccount() != null) {
                Account parentAccount = accountRepo.findOne(account.getParentAccount().getId());
                if (parentAccount != null) {
                    account.setLevel(parentAccount.getLevel() + 1);
                }
            } else {
                account.setParentAccountId(0);
            }

            if (account.getId() > 0) {  // update mode
                update(account);

                if(!Checker.collectionIsEmpty(account.getSegmentAccounts())) {
                    List<SegmentAccount> segmentAccountList = segmentAccountRepo.findByAccountId(account.getId());
                    if (Checker.collectionIsEmpty(segmentAccountList)) {
                        // fresh insert
                        persistSegmentAccounts(account);
                    } else { // add up
                        Set<SegmentAccount> segmentAccounts = account.getSegmentAccounts();
                        for(SegmentAccount segmentAccount : segmentAccounts) {
                            BusinessSegment businessSegment = businessSegmentRepo.findOne(segmentAccount.getBusinessSegment().getId());
                            String code = generateSegmentAccountCode(businessSegment, account);
                            segmentAccountRepo.saveWithExistenceCheck(businessSegment.getId(), account.getId(), code);
                        }
                    }
                }
            } else {
                account = create(account);
                persistSegmentAccounts(account);
            }

            response.setModelId(account.getId());
            response.setSuccessMessage("Account successfully saved!");
            response.setSuccess(true);
        }
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Account> findByTitle(String title) {
        return accountRepo.findByTitle(title);
    }

    @Override
    public List<Account> findByIdNotIn(Integer... accountId) {
        return accountRepo.findByIdNotInOrderByTitleAsc(accountId);
    }

    @CacheEvict(value =  {"accountsTreeCache", "accountsCache"}, allEntries = true)
    public PostResponse processUpdate(Account account, BindingResult bindingResult, MessageSource messageSource) {
        return processCreate(account, bindingResult, messageSource);
    }

    private String generateSegmentAccountCode(BusinessSegment businessSegment, Account account) {
        return businessSegment.getBusinessActivity().getCode() + businessSegment.getCode() + "-" + account.getCode();
    }

    private void persistSegmentAccounts(Account account) {
        if (!Checker.collectionIsEmpty(account.getSegmentAccounts())) {
            Set<SegmentAccount> segmentAccounts = account.getSegmentAccounts();

            for(SegmentAccount segmentAccount : segmentAccounts) {
                BusinessSegment businessSegment = businessSegmentRepo.findOne(segmentAccount.getBusinessSegment().getId());
                String code = generateSegmentAccountCode(businessSegment, account);
                segmentAccount.setAccount(account);
                segmentAccount.setAccountCode(code);
                segmentAccountRepo.save(segmentAccount);
            }
        }
    }

    private void findDescendants(Account currentAccount) {
        List<Account> children = accountRepo.findByParentAccountIdOrderByCodeAsc(currentAccount.getId());

        for (Account childAccount : children) {
            AccountDto accountDto = new AccountDto();

            accountDto.setCode(childAccount.getCode());
            accountDto.setId(childAccount.getId());
            accountDto.setTitle(childAccount.getTitle());
            accountDto.setParentAccountId(childAccount.getParentAccountId());

            if (childAccount.getAccountType() != null) {
                accountDto.setAccountType(childAccount.getAccountType());
            }

            this.accountsDtoList.add(accountDto);
            findDescendants(childAccount);
        }
    }
}
