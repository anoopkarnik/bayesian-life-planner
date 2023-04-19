package com.bayesiansamaritan.lifeplanner.service;

import com.bayesiansamaritan.lifeplanner.model.Financial.Transactions;
import com.bayesiansamaritan.lifeplanner.response.TransactionResponse;

import java.util.List;

public interface TransactionsService {


    public Transactions createTransactions(String name,Long cost, String expenseName, String categoryName, String SubCategoryName, String AccountName,
                                           String SubAccountName, Long userId);

    public List<TransactionResponse> getTransactions(Long userId, List<String> expenseTypes, List<String> accountTypes, List<String> categoryTypes,
                                                     List<String> subCategoryTypes, String dateFrom, String dateTo);

    public void deleteTransactions(Long id);

    public void modifyParams(TransactionResponse transactionResponse);

}
