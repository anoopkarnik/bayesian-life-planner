package com.bayesiansamaritan.lifeplanner.service.impl;

import com.bayesiansamaritan.lifeplanner.model.Financial.*;
import com.bayesiansamaritan.lifeplanner.repository.Financial.*;
import com.bayesiansamaritan.lifeplanner.response.TransactionResponse;
import com.bayesiansamaritan.lifeplanner.service.TransactionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionsServiceImpl implements TransactionsService {

    @Autowired
    TransactionsRepository transactionsRepository;

    @Autowired
    AccountTypeRepository accountTypeRepository;

    @Autowired
    CategoryTypeRepository categoryTypeRepository;

    @Autowired
    ExpenseTypeRepository expenseTypeRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    SubCategoryTypeRepository subCategoryTypeRepository;

    public TransactionsServiceImpl(TransactionsRepository transactionsRepository){
        this.transactionsRepository = transactionsRepository;
    }



    @Override
    public Transactions createTransactions(String name,Long cost, String expenseName, String categoryName, String subCategoryName, String accountName,
                                           String subAccountName, Long userId){


        AccountType accountType = accountTypeRepository.findByNameAndUserId(accountName,userId);
        Long accountTypeId = accountType.getId();

        ExpenseType expenseType = expenseTypeRepository.findByNameAndUserId(expenseName,userId);
        Long expenseTypeId = expenseType.getId();

        CategoryType categoryType = categoryTypeRepository.findByNameAndUserId(categoryName,userId);
        Long categoryTypeId = categoryType.getId();

        SubCategoryType subCategoryType = subCategoryTypeRepository.findByNameAndUserId(subCategoryName,userId);
        Long subCategoryTypeId = subCategoryType.getId();

        Account account = accountRepository.findByNameAndUserId(subAccountName,userId);
        Long subAccountTypeId = account.getId();

        Boolean active = true;

        Transactions transactions = transactionsRepository.save(new Transactions(name,cost,expenseTypeId,userId,accountTypeId,categoryTypeId,subAccountTypeId,subCategoryTypeId,active));
        accountRepository.updateBalance(subAccountTypeId,cost);
        return transactions;
    };

    public List<TransactionResponse> getTransactions(Long userId, List<String> expenseTypes, List<String> accountTypes, List<String> categoryTypes,
                                                     List<String> subCategoryTypes, String dateFrom, String dateTo){
        List<Long> expenseTypeIds = new ArrayList<>();
        List<Long> accountTypeIds = new ArrayList<>();
        List<Long> categoryTypeIds = new ArrayList<>();
        List<Long> subCategoryTypeIds = new ArrayList<>();

        for(String expenseName:expenseTypes){
            ExpenseType expenseType = expenseTypeRepository.findByNameAndUserId(expenseName,userId);
            expenseTypeIds.add(expenseType.getId());
        }
        for(String accountName:accountTypes){
            AccountType accountType = accountTypeRepository.findByNameAndUserId(accountName,userId);
            accountTypeIds.add(accountType.getId());
        }
        for(String categoryName:categoryTypes){
            CategoryType categoryType = categoryTypeRepository.findByNameAndUserId(categoryName,userId);
            categoryTypeIds.add(categoryType.getId());
        }
        for(String subCategoryName:subCategoryTypes){
            SubCategoryType subCategoryType = subCategoryTypeRepository.findByNameAndUserId(subCategoryName,userId);
            subCategoryTypeIds.add(subCategoryType.getId());
        }

        Date startDate = null;
        try {
            startDate = new SimpleDateFormat("yyyy-MM-dd").parse(dateFrom);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        Date endDate = null;
        try {
            endDate = new SimpleDateFormat("yyyy-MM-dd").parse(dateTo);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        List<Transactions> transactions = transactionsRepository.findByAll(userId,expenseTypeIds,accountTypeIds,categoryTypeIds,
                subCategoryTypeIds,startDate,endDate);
        List<TransactionResponse> transactionResponses = new ArrayList<>();
        for(Transactions transaction : transactions){
            Optional<ExpenseType> expenseType = expenseTypeRepository.findById(transaction.getExpenseTypeId());
            Optional<AccountType> accountType = accountTypeRepository.findById(transaction.getAccountTypeId());
            Optional<Account> account = accountRepository.findById(transaction.getAccountId());
            Optional<CategoryType> categoryType = categoryTypeRepository.findById(transaction.getCategoryId());
            Optional<SubCategoryType> subCategoryType = subCategoryTypeRepository.findById(transaction.getSubCategoryId());
            TransactionResponse transactionResponse = new TransactionResponse(transaction.getId(),transaction.getCreatedAt(),
                    transaction.getUpdatedAt(),transaction.getName(),transaction.getStartDate(),account.get().getName(),
                    accountType.get().getName(),categoryType.get().getName(),expenseType.get().getName(),subCategoryType.get().getName(),
                    transaction.getDescription(),transaction.getActive(),transaction.getHidden(),transaction.getCompleted(),
                    transaction.getCost(),transaction.getUserId());
            transactionResponses.add(transactionResponse);
        }

        return transactionResponses;
    };

    public void deleteTransactions(Long id){
        Transactions transactions = transactionsRepository.getReferenceById(id);
        transactionsRepository.deleteById(id);
        accountRepository.updateBalance(transactions.getAccountId(),-1*transactions.getCost());

    }

    public void modifyParams(TransactionResponse transactionResponse){
        ExpenseType expenseType = expenseTypeRepository.findByNameAndUserId(transactionResponse.getExpenseName(),
                transactionResponse.getUserId());
        AccountType accountType = accountTypeRepository.findByNameAndUserId(transactionResponse.getAccountTypeName(),
                transactionResponse.getUserId());
        Account account = accountRepository.findByNameAndUserId(transactionResponse.getAccountName(),
                transactionResponse.getUserId());
        CategoryType categoryType = categoryTypeRepository.findByNameAndUserId(transactionResponse.getCategoryName(),
                transactionResponse.getUserId());
        SubCategoryType subCategoryType = subCategoryTypeRepository.findByNameAndUserId(transactionResponse.getSubCategoryName(),
                transactionResponse.getUserId());

        transactionsRepository.modifyParams(transactionResponse.getId(),transactionResponse.getName(),transactionResponse.getStartDate(),
                transactionResponse.getDescription(),account.getId(),accountType.getId(),transactionResponse.getActive(),
                transactionResponse.getHidden(),transactionResponse.getCompleted(),expenseType.getId(),
                categoryType.getId(),subCategoryType.getId(),transactionResponse.getCost());
    }
}