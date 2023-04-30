package com.bayesiansamaritan.lifeplanner.service.impl;

import com.bayesiansamaritan.lifeplanner.model.Financial.*;
import com.bayesiansamaritan.lifeplanner.repository.Financial.AccountTypeRepository;
import com.bayesiansamaritan.lifeplanner.repository.Financial.AccountRepository;
import com.bayesiansamaritan.lifeplanner.response.AccountBalanceResponse;
import com.bayesiansamaritan.lifeplanner.response.AccountResponse;
import com.bayesiansamaritan.lifeplanner.response.TransactionResponse;
import com.bayesiansamaritan.lifeplanner.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    AccountRepository accountRepository;


    @Autowired
    AccountTypeRepository accountTypeRepository;


    public AccountServiceImpl(AccountRepository accountRepository, AccountTypeRepository accountTypeRepository){
        this.accountRepository = accountRepository;
        this.accountTypeRepository=accountTypeRepository;

    }

    @Override
    public Account createSubAccount(String accountName, Long balance, Boolean freeLiquidity, Boolean liquidity, String name, Long userId){


        AccountType accountType = accountTypeRepository.findByNameAndUserId(accountName,userId);
        Long accountTypeId = accountType.getId();
        Account account = accountRepository.save(new Account(name,balance,accountTypeId,userId,freeLiquidity,liquidity));
        return account;
    };

    @Override
    public List<AccountResponse> getAccountByUserAndAccountType(Long userId, String accountTypeName){
        AccountType accountType = accountTypeRepository.findByNameAndUserId(accountTypeName,userId);
        Long accountTypeId = accountType.getId();

        List<Account> accountList = accountRepository.findByUserIdAndAccountTypeId(userId,accountTypeId);
        List<AccountResponse> accountResponses = new ArrayList<>();
        for(Account account:accountList){
            AccountResponse accountResponse = new AccountResponse(account.getId(),account.getCreatedAt(),account.getUpdatedAt(),
                    account.getName(),account.getStartDate(),accountTypeName,account.getBalance(),account.getLiquidity(),
                    account.getFreeLiquidity(),account.getDescription(),account.getActive(),account.getHidden(),account.getCompleted(),
                    userId);
            accountResponses.add(accountResponse);
        }
        return accountResponses;

    };

    @Override
    public List<Account> getSubAccountByUser(Long userId){
        List<Account> accountList = accountRepository.findByUserId(userId);
        return accountList;

    };

    @Override
    public void updateBalance(Long id, Long cost){
        Optional<Account> subAccountType = accountRepository.findById(id);
        accountRepository.updateBalance(id,cost);
    };

    @Override
    public void changeBalance(Long id, Long balance){
        Optional<Account> subAccountType = accountRepository.findById(id);
        accountRepository.changeBalance(id,balance);
    };

    @Override
    public void deleteAccounts(Long id){
        accountRepository.deleteById(id);
    }

    @Override
    public List<AccountBalanceResponse> getAllAccountBalances(Long userId){
        List<AccountType> accountTypes = new ArrayList<>();
        accountTypeRepository.findByUserId(userId).forEach(accountTypes::add);
        List<AccountBalanceResponse> accountBalances = new ArrayList<>();
        for (AccountType accountType:accountTypes){
            List<Account> accounts = accountRepository.findByUserIdAndAccountTypeId(userId,accountType.getId());
            Long balance = 0L;
            for(Account account : accounts){
                balance+= account.getBalance();
            }
            AccountBalanceResponse accountBalanceResponse = new AccountBalanceResponse();
            accountBalanceResponse.setId(accountType.getId());
            accountBalanceResponse.setName(accountType.getName());
            accountBalanceResponse.setBalance(balance);
            accountBalances.add(accountBalanceResponse);
        }
        return accountBalances;
    }

    public void modifyParams(AccountResponse accountResponse){
        AccountType accountType = accountTypeRepository.findByNameAndUserId(accountResponse.getAccountTypeName(),
                accountResponse.getUserId());
        accountRepository.modifyParams(accountResponse.getId(),accountResponse.getName(),accountResponse.getStartDate(),
                accountResponse.getDescription(),accountType.getId(),accountResponse.getActive(),accountResponse.getHidden(),
                accountResponse.getCompleted(),accountResponse.getBalance(),accountResponse.getLiquidity(),accountResponse.getFreeLiquidity());
    }


}


