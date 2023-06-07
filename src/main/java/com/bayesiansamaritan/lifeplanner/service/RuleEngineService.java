package com.bayesiansamaritan.lifeplanner.service;

import com.bayesiansamaritan.lifeplanner.enums.CriteriaEnum;
import com.bayesiansamaritan.lifeplanner.model.Rule.CriteriaSet;
import com.bayesiansamaritan.lifeplanner.model.Rule.Rule;
import com.bayesiansamaritan.lifeplanner.model.Rule.RuleSet;
import com.bayesiansamaritan.lifeplanner.request.Rule.*;
import com.bayesiansamaritan.lifeplanner.response.*;

import java.text.ParseException;
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
    public void deleteCriteriaInCriteriaSet(Long criteriaSetId, Long criteriaId);
    public void deleteCriteriaSetInRule(Long ruleId, Long criteriaSetId);
    public void deleteRuleInRuleSet(Long ruleSetId, Long ruleId);
    public void addCriteriaInCriteriaSet(Long criteriaSetId, Long criteriaId);
    public void addCriteriaSetInRule(Long ruleId, Long criteriaSetId);
    public void addRuleInRuleSet(Long ruleSetId, Long ruleId);

    public List<TypesResponse> getAllTypes(Long userId, String type);
    public List<NamesResponse> getAllNames(Long userId, String type, String name) throws ParseException;
    public Float getCompletedPercentage(Long userId, Long goalId) throws ParseException;
    public Float getWorkPercentage(Long userId, Long goalId) throws ParseException;


}
