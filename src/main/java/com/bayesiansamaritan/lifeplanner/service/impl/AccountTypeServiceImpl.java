package com.bayesiansamaritan.lifeplanner.service.impl;

import com.bayesiansamaritan.lifeplanner.repository.Financial.AccountTypeRepository;
import com.bayesiansamaritan.lifeplanner.repository.User.UserProfileRepository;
import com.bayesiansamaritan.lifeplanner.request.Financial.AccountTypeRequest;
import com.bayesiansamaritan.lifeplanner.request.User.UserProfileRequest;
import com.bayesiansamaritan.lifeplanner.service.AccountTypeService;
import com.bayesiansamaritan.lifeplanner.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountTypeServiceImpl implements AccountTypeService {

    @Autowired
    AccountTypeRepository accountTypeRepository;

    public AccountTypeServiceImpl(AccountTypeRepository accountTypeRepository) {
        this.accountTypeRepository=accountTypeRepository;
    }

    public void modifyParams(AccountTypeRequest accountTypeRequest){

        accountTypeRepository.modifyParams(accountTypeRequest.getId(),accountTypeRequest.getDescription());
    }


}


