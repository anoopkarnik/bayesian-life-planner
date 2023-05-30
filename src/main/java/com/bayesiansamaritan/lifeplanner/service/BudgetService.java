package com.bayesiansamaritan.lifeplanner.service;

import com.bayesiansamaritan.lifeplanner.model.Financial.BudgetPlan;
import com.bayesiansamaritan.lifeplanner.model.Financial.MonthlyBudget;
import com.bayesiansamaritan.lifeplanner.response.BudgetPlanResponse;
import com.bayesiansamaritan.lifeplanner.response.BudgetResponse;

import java.text.ParseException;
import java.util.List;

public interface BudgetService {

    public MonthlyBudget createMonthlyBudget(Long cost, String expenseName, String categoryName, String SubCategoryName, Long userId);

    public List<BudgetResponse> getMonthlyBudgets(String expenseName, Long userId) throws ParseException;
    public BudgetResponse getMonthlyBudget(Long id) throws ParseException;

    public BudgetPlan createBudgetPlan(String expenseName, Long planPercentage, Long userId);

    public List<BudgetPlanResponse> getBudgetPlans(Long userId) throws ParseException;

    public void modifyParams(BudgetResponse budgetResponse);

}
