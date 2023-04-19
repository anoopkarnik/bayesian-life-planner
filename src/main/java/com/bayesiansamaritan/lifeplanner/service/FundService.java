package com.bayesiansamaritan.lifeplanner.service;

import com.bayesiansamaritan.lifeplanner.response.AccountResponse;
import com.bayesiansamaritan.lifeplanner.response.FundResponse;
import com.bayesiansamaritan.lifeplanner.response.FundSummaryResponse;

public interface FundService {

    public FundSummaryResponse getFundSummary(Long userId);

    public void modifyParams(FundResponse fundResponse);

}
