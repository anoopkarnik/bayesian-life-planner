package com.bayesiansamaritan.lifeplanner.service;

import com.bayesiansamaritan.lifeplanner.model.Financial.Account;
import com.bayesiansamaritan.lifeplanner.response.AccountBalanceResponse;
import com.bayesiansamaritan.lifeplanner.response.AccountResponse;
import com.bayesiansamaritan.lifeplanner.response.TransactionResponse;

import java.util.List;

public interface AccountService {

    public List<AccountBalanceResponse>  getAllAccountBalances(Long userId);
    public Account createSubAccount(String accountName, Long balance, Boolean freeLiquidity, Boolean liquidity, String name, Long userId);
    public List<AccountResponse> getAccountByUserAndAccountType(Long userId, String accountTypeName);
    public List<Account> getSubAccountByUser(Long userId);
    public void updateBalance(Long id, Long cost);
    public void changeBalance(Long id, Long balance);

    public void deleteAccounts(Long id);
    public void modifyParams(AccountResponse accountResponse);


}
