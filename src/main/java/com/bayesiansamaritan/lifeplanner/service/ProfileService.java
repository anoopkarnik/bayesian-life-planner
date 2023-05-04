package com.bayesiansamaritan.lifeplanner.service;

import com.bayesiansamaritan.lifeplanner.model.Financial.Account;
import com.bayesiansamaritan.lifeplanner.request.User.UserProfileRequest;
import com.bayesiansamaritan.lifeplanner.response.AccountBalanceResponse;
import com.bayesiansamaritan.lifeplanner.response.AccountResponse;

import java.util.List;

public interface ProfileService {

    public void modifyParams(UserProfileRequest userProfileRequest);

}
