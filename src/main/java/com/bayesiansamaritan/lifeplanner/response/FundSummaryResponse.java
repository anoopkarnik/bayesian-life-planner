package com.bayesiansamaritan.lifeplanner.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FundSummaryResponse {

    private Long totalAmount;
    private Long emergencyAmountAvailable;
    private Long fundsAmountAvailable;
    private Long amountAllocated;

    private Long financialIndependenceAmount;
    private Long financialIndependencePercentage;
    private Long timeLeft;
}
