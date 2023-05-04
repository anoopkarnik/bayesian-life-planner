package com.bayesiansamaritan.lifeplanner.service.impl;

import com.bayesiansamaritan.lifeplanner.model.Financial.Account;
import com.bayesiansamaritan.lifeplanner.model.Financial.AccountType;
import com.bayesiansamaritan.lifeplanner.repository.Financial.AccountRepository;
import com.bayesiansamaritan.lifeplanner.repository.Financial.AccountTypeRepository;
import com.bayesiansamaritan.lifeplanner.repository.User.UserProfileRepository;
import com.bayesiansamaritan.lifeplanner.request.User.UserProfileRequest;
import com.bayesiansamaritan.lifeplanner.response.AccountBalanceResponse;
import com.bayesiansamaritan.lifeplanner.response.AccountResponse;
import com.bayesiansamaritan.lifeplanner.service.AccountService;
import com.bayesiansamaritan.lifeplanner.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProfileServiceImpl implements ProfileService {

    @Autowired
    UserProfileRepository userProfileRepository;

    public ProfileServiceImpl(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }

    public void modifyParams(UserProfileRequest userProfileRequest){

        userProfileRepository.modifyParams(userProfileRequest.getId(),userProfileRequest.getPanNo());
    }


}


