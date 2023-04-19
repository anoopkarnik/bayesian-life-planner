package com.bayesiansamaritan.lifeplanner.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BudgetPlanResponse {

    private Long id;
    private String expenseName;
    private Long planPercentage;
    private Long transactionPercentage;
    private Long planTotal;
    private Long allottedTotal;
    private Long transactionTotal;


}
