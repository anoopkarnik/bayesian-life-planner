package com.bayesiansamaritan.lifeplanner.service.impl;

import com.bayesiansamaritan.lifeplanner.model.Financial.*;
import com.bayesiansamaritan.lifeplanner.repository.Financial.*;
import com.bayesiansamaritan.lifeplanner.response.BudgetPlanResponse;
import com.bayesiansamaritan.lifeplanner.response.BudgetResponse;
import com.bayesiansamaritan.lifeplanner.service.BudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
@Service
public class BudgetServiceImpl implements BudgetService {

    @Autowired
    MonthlyBudgetRepository monthlyBudgetRepository;
    @Autowired
    AccountTypeRepository accountTypeRepository;

    @Autowired
    TransactionsRepository transactionsRepository;
    @Autowired
    CategoryTypeRepository categoryTypeRepository;
    @Autowired
    ExpenseTypeRepository expenseTypeRepository;
    @Autowired
    SubCategoryTypeRepository subCategoryTypeRepository;

    @Autowired
    private final BudgetPlanRepository budgetPlanRepository;
    @Autowired
    IncomeRepository incomeRepository;


    public BudgetServiceImpl(MonthlyBudgetRepository monthlyBudgetRepository,
                             BudgetPlanRepository budgetPlanRepository){
        this.monthlyBudgetRepository=monthlyBudgetRepository;

        this.budgetPlanRepository = budgetPlanRepository;
    }

    @Override
    public MonthlyBudget createMonthlyBudget(Long cost, String expenseName, String categoryName, String subCategoryName, Long userId){

        ExpenseType expenseType = expenseTypeRepository.findByNameAndUserId(expenseName,userId);
        Long expenseTypeId = expenseType.getId();

        CategoryType categoryType = categoryTypeRepository.findByNameAndUserId(categoryName,userId);
        Long categoryTypeId = categoryType.getId();

        SubCategoryType subCategoryType = subCategoryTypeRepository.findByNameAndUserId(subCategoryName,userId);
        Long subCategoryTypeId = subCategoryType.getId();

        Boolean active = true;

        MonthlyBudget monthlyBudget = monthlyBudgetRepository.save(new MonthlyBudget(cost,expenseTypeId,active,categoryTypeId,subCategoryTypeId,userId));
        return monthlyBudget;
    };

    @Override
    public List<BudgetResponse> getMonthlyBudget(String expenseName, Long userId) throws ParseException {
        ExpenseType expenseType = expenseTypeRepository.findByNameAndUserId(expenseName,userId);
        Long expenseTypeId = expenseType.getId();

        List<BudgetResponse> monthlyBudgetResponses = new ArrayList<>();

        List<MonthlyBudget> monthlyBudgets = monthlyBudgetRepository.findByUserIdAndExpenseTypeId(userId,expenseTypeId);
        LocalDate todayDate = LocalDate.now();
        Date startDate =  new SimpleDateFormat("yyyy-MM-dd").parse(todayDate.with(TemporalAdjusters.firstDayOfMonth()).toString());
        Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse(todayDate.with(TemporalAdjusters.firstDayOfNextMonth()).toString());

        for (MonthlyBudget monthlyBudget:monthlyBudgets){
            Optional<CategoryType> categoryType = categoryTypeRepository.findById(monthlyBudget.getCategoryTypeId());
            Optional<SubCategoryType> subCategoryType = subCategoryTypeRepository.findById(monthlyBudget.getSubCategoryTypeId());
            List<Transactions> transactions = transactionsRepository.findBySome(userId,expenseTypeId,monthlyBudget.getCategoryTypeId(),
                    monthlyBudget.getSubCategoryTypeId(),startDate,endDate);
            Long amountSpent = 0L;
            for (Transactions transaction:transactions){
                amountSpent+=transaction.getCost();
            }
            BudgetResponse monthlyBudgetResponse = new BudgetResponse(monthlyBudget.getId(),monthlyBudget.getCreatedAt(),
                    monthlyBudget.getUpdatedAt(),monthlyBudget.getName(),monthlyBudget.getStartDate(),categoryType.get().getName(),
                    expenseName,subCategoryType.get().getName(),monthlyBudget.getDescription(),monthlyBudget.getActive(),
                    monthlyBudget.getHidden(),monthlyBudget.getCompleted(),monthlyBudget.getCost(),monthlyBudget.getUserId(),
                    monthlyBudget.getCost(),amountSpent);
            monthlyBudgetResponses.add(monthlyBudgetResponse);
        }
        return monthlyBudgetResponses;
    };


    @Override
    public BudgetPlan createBudgetPlan(String expenseName, Long planPercentage, Long userId){
        ExpenseType expenseType = expenseTypeRepository.findByNameAndUserId(expenseName,userId);
        Long expenseTypeId = expenseType.getId();

        BudgetPlan budgetPlan = budgetPlanRepository.save(new BudgetPlan(planPercentage,expenseTypeId,userId));
        return budgetPlan;
    };

    @Override
    public List<BudgetPlanResponse> getBudgetPlans(Long userId) throws ParseException {

        List<ExpenseType> expenseTypes = expenseTypeRepository.findAll();
        List<AccountType> accountTypes = accountTypeRepository.findAll();
        List<CategoryType> categoryTypes = categoryTypeRepository.findAll();
        List<SubCategoryType> subCategoryTypes = subCategoryTypeRepository.findAll();

        List<Long> accountTypeIds = new ArrayList<>();
        List<Long> categoryTypeIds = new ArrayList<>();
        List<Long> subCategoryTypeIds = new ArrayList<>();

        for(AccountType accountType:accountTypes){
            accountTypeIds.add(accountType.getId());
        }
        for(CategoryType categoryType:categoryTypes){
            categoryTypeIds.add(categoryType.getId());
        }
        for(SubCategoryType subCategoryType:subCategoryTypes){
            subCategoryTypeIds.add(subCategoryType.getId());
        }

        LocalDate todayDate = LocalDate.now();
        Date startDate =  new SimpleDateFormat("yyyy-MM-dd").parse(todayDate.with(TemporalAdjusters.firstDayOfMonth()).toString());
        Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse(todayDate.with(TemporalAdjusters.firstDayOfNextMonth()).toString());

        List<Income> incomes = incomeRepository.findByUserId(userId);

        Long totalIncome = 0L;
        for(Income income:incomes){
            totalIncome+=income.getIncome();
        }


        List<BudgetPlanResponse> budgetPlanResponses = new ArrayList<>();
        for (ExpenseType expenseType:expenseTypes) {
            List<MonthlyBudget> monthlyBudgets = monthlyBudgetRepository.findByUserIdAndExpenseTypeId(userId,expenseType.getId());
            Long allottedTotal = 0L;
            for (MonthlyBudget monthlyBudget :monthlyBudgets){
                allottedTotal+=monthlyBudget.getCost();
            }
            List<Long> expenseTypeIds = new ArrayList<>();
            expenseTypeIds.add(expenseType.getId());
            List<Transactions> transactions = transactionsRepository.findByAll(userId,expenseTypeIds,accountTypeIds,categoryTypeIds,
                    subCategoryTypeIds,startDate,endDate);
            Long totalTransactions = 0L;
            for(Transactions transaction:transactions){
                totalTransactions+=transaction.getCost();
            }
            BudgetPlan budgetPlan = budgetPlanRepository.findByUserIdAndExpenseTypeId(userId, expenseType.getId());
            Long transactionPercentage = totalTransactions * 100 / totalIncome;
            BudgetPlanResponse budgetPlanResponse = new BudgetPlanResponse();
            try{
                budgetPlanResponse.setId(budgetPlan.getId());
                budgetPlanResponse.setExpenseName(expenseType.getName());
                budgetPlanResponse.setPlanPercentage(budgetPlan.getPlanPercentage());
                budgetPlanResponse.setTransactionTotal(totalTransactions);
                budgetPlanResponse.setTransactionPercentage(transactionPercentage);
                budgetPlanResponse.setPlanTotal(budgetPlan.getPlanPercentage() * totalIncome / 100);
                budgetPlanResponse.setAllottedTotal(allottedTotal);
                budgetPlanResponses.add(budgetPlanResponse);
            }
            catch(Exception e){
                budgetPlanResponse.setId(9999L);
                budgetPlanResponse.setExpenseName(expenseType.getName());
                budgetPlanResponse.setPlanPercentage(0L);
                budgetPlanResponse.setTransactionTotal(totalTransactions);
                budgetPlanResponse.setTransactionPercentage(transactionPercentage);
                budgetPlanResponse.setPlanTotal(0L* totalIncome / 100);
                budgetPlanResponse.setAllottedTotal(allottedTotal);
                budgetPlanResponses.add(budgetPlanResponse);
            }

        }
        return budgetPlanResponses;
    };

    public void modifyParams(BudgetResponse budgetResponse){
        ExpenseType expenseType = expenseTypeRepository.findByNameAndUserId(budgetResponse.getExpenseName(),
                budgetResponse.getUserId());
        CategoryType categoryType = categoryTypeRepository.findByNameAndUserId(budgetResponse.getCategoryName(),
                budgetResponse.getUserId());
        SubCategoryType subCategoryType = subCategoryTypeRepository.findByNameAndUserId(budgetResponse.getSubCategoryName(),
                budgetResponse.getUserId());

        monthlyBudgetRepository.modifyParams(budgetResponse.getId(),budgetResponse.getName(),budgetResponse.getStartDate(),
                budgetResponse.getDescription(),budgetResponse.getActive(),
                budgetResponse.getHidden(),budgetResponse.getCompleted(),expenseType.getId(),
                categoryType.getId(),subCategoryType.getId(),budgetResponse.getCost());
    }
}


