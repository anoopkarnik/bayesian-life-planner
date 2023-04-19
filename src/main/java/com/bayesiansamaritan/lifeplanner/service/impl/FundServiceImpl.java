package com.bayesiansamaritan.lifeplanner.service.impl;

import com.bayesiansamaritan.lifeplanner.model.Financial.*;
import com.bayesiansamaritan.lifeplanner.repository.Financial.*;
import com.bayesiansamaritan.lifeplanner.response.AccountResponse;
import com.bayesiansamaritan.lifeplanner.response.FundResponse;
import com.bayesiansamaritan.lifeplanner.response.FundSummaryResponse;
import com.bayesiansamaritan.lifeplanner.service.FundService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FundServiceImpl implements FundService {
    private final AccountTypeRepository accountTypeRepository;
    private final AccountRepository accountRepository;
    private final FundRepository fundRepository;
    private final BudgetPlanRepository budgetPlanRepository;
    private final ExpenseTypeRepository expenseTypeRepository;
    private final IncomeRepository incomeRepository;

    public FundServiceImpl(AccountTypeRepository accountTypeRepository,
                           AccountRepository accountRepository,
                           FundRepository fundRepository,
                           BudgetPlanRepository budgetPlanRepository,
                           ExpenseTypeRepository expenseTypeRepository,
                           IncomeRepository incomeRepository) {
        this.accountTypeRepository = accountTypeRepository;
        this.accountRepository = accountRepository;
        this.fundRepository = fundRepository;
        this.budgetPlanRepository = budgetPlanRepository;
        this.expenseTypeRepository = expenseTypeRepository;
        this.incomeRepository = incomeRepository;
    }

    @Override
    public FundSummaryResponse getFundSummary(Long userId){
        String accountName = "investments";
        AccountType accountType = accountTypeRepository.findByNameAndUserId(accountName,userId);
        List<Account> accounts = accountRepository.findByUserIdAndAccountTypeId(userId,accountType.getId());
        Long totalAmount = 0L;
        Long amountAvailable = 0L;
        Long amountAllocated = 0L;
        Long amountNeeded = 0L;

        for(Account account : accounts){
            totalAmount+= account.getBalance();
            if(account.getFreeLiquidity() || account.getLiquidity()){
                amountAvailable+= account.getBalance();
            }
        }

        List<Fund> funds = fundRepository.findByUserId(userId);
        for(Fund fund: funds){
            amountAllocated+=fund.getAmountAllocated();
            amountNeeded+=fund.getAmountNeeded();
        }

        String expenseName = "savings";
        ExpenseType expenseType = expenseTypeRepository.findByNameAndUserId(expenseName,userId);
        BudgetPlan budgetPlan = budgetPlanRepository.findByUserIdAndExpenseTypeId(userId,expenseType.getId());
        List<Income> incomes = incomeRepository.findByUserId(userId);
        Long totalIncome = 0L;
        for(Income income: incomes){
            totalIncome+=income.getIncome();
        }
        Long spending = (100-budgetPlan.getPlanPercentage())*totalIncome/100;
        Long financialIndependenceAmount = spending*12*30+amountNeeded;
        Long financialIndependencePercentage = (totalAmount-amountAllocated)*100/financialIndependenceAmount;
        Long savingsPerMonth = budgetPlan.getPlanPercentage()*totalIncome/100;
        Long financialIndependenceAmountLeft = (100-financialIndependencePercentage)*financialIndependenceAmount/100;
        Long timeLeft = financialIndependenceAmountLeft/savingsPerMonth/12;
        FundSummaryResponse fundSummaryResponse = new FundSummaryResponse();
        fundSummaryResponse.setAmountAllocated(amountAllocated);
        fundSummaryResponse.setAmountAvailable(amountAvailable);
        fundSummaryResponse.setTotalAmount(totalAmount);
        fundSummaryResponse.setFinancialIndependenceAmount(financialIndependenceAmount);
        fundSummaryResponse.setFinancialIndependencePercentage(financialIndependencePercentage);
        fundSummaryResponse.setTimeLeft(timeLeft);
        return fundSummaryResponse;
    };

    public void modifyParams(FundResponse fundResponse){
        List<Fund> fund = fundRepository.findByUserId(fundResponse.getUserId());
        fundRepository.modifyParams(fundResponse.getId(),fundResponse.getName(),fundResponse.getStartDate(),
                fundResponse.getDescription(),fundResponse.getActive(),fundResponse.getHidden(),
                fundResponse.getCompleted(),fundResponse.getAmountAllocated(),fundResponse.getAmountNeeded());
    }



}


