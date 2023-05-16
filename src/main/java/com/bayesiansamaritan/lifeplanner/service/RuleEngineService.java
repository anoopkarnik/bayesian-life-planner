package com.bayesiansamaritan.lifeplanner.service;

import com.bayesiansamaritan.lifeplanner.enums.CriteriaEnum;
import com.bayesiansamaritan.lifeplanner.model.Rule.CriteriaSet;
import com.bayesiansamaritan.lifeplanner.model.Rule.Rule;
import com.bayesiansamaritan.lifeplanner.model.Rule.RuleSet;
import com.bayesiansamaritan.lifeplanner.request.Rule.*;
import com.bayesiansamaritan.lifeplanner.response.*;

import java.util.List;

public interface RuleEngineService {

    public List<CriteriaResponse> getAllCriteria(Long userId, CriteriaEnum criteriaType);
    public List<CriteriaSet> getAllCriteriaSet(Long userId);
    public List<Rule> getAllRules(Long userId);
    public List<RuleSet> getAllRuleSets(Long userId);
    public void createCriteria(CriteriaRequest criteriaRequest);
    public void createCriteriaSet(CriteriaSetRequest criteriaSetRequest);
    public void createRule(RuleRequest ruleRequest);
    public void createRuleSet(RuleSetRequest ruleSetRequest);


}
