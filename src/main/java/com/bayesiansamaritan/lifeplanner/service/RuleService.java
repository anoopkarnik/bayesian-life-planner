package com.bayesiansamaritan.lifeplanner.service;

import com.bayesiansamaritan.lifeplanner.request.Rule.RuleCreateRequest;
import com.bayesiansamaritan.lifeplanner.response.NamesResponse;
import com.bayesiansamaritan.lifeplanner.response.RuleResponse;
import com.bayesiansamaritan.lifeplanner.response.TypesResponse;

import java.util.List;

public interface RuleService {

    public List<RuleResponse> getAllCompletedRules(Long userId, Long goalId);
    public List<RuleResponse> getAllWorkRules(Long userId, Long goalId);
    public void createRule(Long userId, RuleCreateRequest ruleRequest);
    public List<TypesResponse> getAllTypes(Long userId, String type);
    public List<NamesResponse> getAllNames(Long userId, String type, String name);
    public Float getCompletedPercentage(Long userId, Long goalId);
    public Float getWorkPercentage(Long userId, Long goalId);
}
