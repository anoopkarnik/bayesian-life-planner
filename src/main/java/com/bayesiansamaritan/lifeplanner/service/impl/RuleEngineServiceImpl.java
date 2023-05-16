package com.bayesiansamaritan.lifeplanner.service.impl;

import com.bayesiansamaritan.lifeplanner.enums.*;
import com.bayesiansamaritan.lifeplanner.model.BadHabit.BadHabit;
import com.bayesiansamaritan.lifeplanner.model.BadHabit.BadHabitTransaction;
import com.bayesiansamaritan.lifeplanner.model.BadHabit.BadHabitType;
import com.bayesiansamaritan.lifeplanner.model.Habit.Habit;
import com.bayesiansamaritan.lifeplanner.model.Habit.HabitType;
import com.bayesiansamaritan.lifeplanner.model.Rule.*;
import com.bayesiansamaritan.lifeplanner.model.Skill.Skill;
import com.bayesiansamaritan.lifeplanner.model.Skill.SkillType;
import com.bayesiansamaritan.lifeplanner.model.Stats.Stats;
import com.bayesiansamaritan.lifeplanner.model.Stats.StatsTransaction;
import com.bayesiansamaritan.lifeplanner.model.Stats.StatsType;
import com.bayesiansamaritan.lifeplanner.model.Task.Task;
import com.bayesiansamaritan.lifeplanner.model.Task.TaskType;
import com.bayesiansamaritan.lifeplanner.repository.BadHabit.BadHabitRepository;
import com.bayesiansamaritan.lifeplanner.repository.BadHabit.BadHabitTransactionRepository;
import com.bayesiansamaritan.lifeplanner.repository.BadHabit.BadHabitTypeRepository;
import com.bayesiansamaritan.lifeplanner.repository.Goal.GoalRepository;
import com.bayesiansamaritan.lifeplanner.repository.Habit.HabitRepository;
import com.bayesiansamaritan.lifeplanner.repository.Habit.HabitTypeRepository;
import com.bayesiansamaritan.lifeplanner.repository.Rule.*;
import com.bayesiansamaritan.lifeplanner.repository.Skill.SkillRepository;
import com.bayesiansamaritan.lifeplanner.repository.Skill.SkillTypeRepository;
import com.bayesiansamaritan.lifeplanner.repository.Stats.StatsRepository;
import com.bayesiansamaritan.lifeplanner.repository.Stats.StatsTransactionRepository;
import com.bayesiansamaritan.lifeplanner.repository.Stats.StatsTypeRepository;
import com.bayesiansamaritan.lifeplanner.repository.Task.TaskRepository;
import com.bayesiansamaritan.lifeplanner.repository.Task.TaskTypeRepository;
import com.bayesiansamaritan.lifeplanner.repository.User.UserProfileRepository;
import com.bayesiansamaritan.lifeplanner.request.Rule.*;
import com.bayesiansamaritan.lifeplanner.response.*;
import com.bayesiansamaritan.lifeplanner.security.jwt.JwtUtils;
import com.bayesiansamaritan.lifeplanner.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Service
public class RuleEngineServiceImpl implements RuleEngineService {

    @Autowired
    private UserProfileRepository userProfileRepository;
    @Autowired
    JwtUtils jwtUtils;
    static final String HEADER_STRING = "Authorization";
    static final String TOKEN_PREFIX = "Bearer";
    @Autowired
    private TaskTypeRepository taskTypeRepository;
    @Autowired
    private StatsTypeRepository statsTypeRepository;
    @Autowired
    private SkillTypeRepository skillTypeRepository;
    @Autowired
    private BadHabitTypeRepository badHabitTypeRepository;
    @Autowired
    private HabitTypeRepository habitTypeRepository;
    @Autowired
    TaskService taskService;
    @Autowired
    HabitService habitService;
    @Autowired
    BadHabitService badHabitService;
    @Autowired
    SkillService skillService;
    @Autowired
    StatsService statsService;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private HabitRepository habitRepository;
    @Autowired
    private BadHabitRepository badHabitRepository;
    @Autowired
    private BadHabitTransactionRepository badHabitTransactionRepository;
    @Autowired
    private SkillRepository skillRepository;
    @Autowired
    private StatsRepository statsRepository;
    @Autowired
    private StatsTransactionRepository statsTransactionRepository;
    @Autowired
    private GoalRepository goalRepository;
    @Autowired
    private CriteriaRepository criteriaRepository;
    @Autowired
    private CriteriaSetRepository criteriaSetRepository;
    @Autowired
    private RuleRepository ruleRepository;
    @Autowired
    private RuleSetRepository ruleSetRepository;

    @Override
    public List<CriteriaResponse> getAllCriteria(Long userId, CriteriaEnum criteriaType){
        List<Criteria> criteriaList = criteriaRepository.findCriteriaByUserIdAndCriteriaType(userId,criteriaType);
        List<CriteriaResponse> criteriaResponses = new ArrayList<>();
        for(Criteria criteria : criteriaList){
            CriteriaResponse criteriaResponse = new CriteriaResponse(criteria.getId(),criteria.getCreatedAt(),
                    criteria.getUpdatedAt(),criteria.getName(),criteria.getCondition(),criteria.getCategory(),
                    criteria.getCriteriaType(),criteria.getActive(),criteria.getValue(),
                    criteria.getCategoryName(),criteria.getWeightage());
            criteriaResponses.add(criteriaResponse);
        }
        return criteriaResponses;
    }

    @Override
    public List<CriteriaSet> getAllCriteriaSet(Long userId){
        List<CriteriaSet> criteriaSetList = criteriaSetRepository.findCriteriaSetByUserId(userId);
        return criteriaSetList;
    }
    @Override
    public List<Rule> getAllRules(Long userId){
        List<Rule> ruleList = ruleRepository.findRuleByUserId(userId);
        return ruleList;
    }
    @Override
    public List<RuleSet> getAllRuleSets(Long userId){
        List<RuleSet> ruleSetList = ruleSetRepository.findRuleSetByUserId(userId);
        return ruleSetList;
    }
    @Override
    public void createCriteria(CriteriaRequest criteriaRequest){
        criteriaRepository.save(new Criteria(criteriaRequest.getUserId(),criteriaRequest.getCriteriaType(),
                criteriaRequest.getCondition(),criteriaRequest.getCategory(),
                criteriaRequest.getName(),criteriaRequest.getWeightage(),criteriaRequest.getValue(),
                criteriaRequest.getCategoryName()));
    }
    @Override
    public void createCriteriaSet(CriteriaSetRequest criteriaSetRequest){
        CriteriaSet criteriaSet = new CriteriaSet();
        criteriaSet.setName(criteriaSetRequest.getName());
        criteriaSet.setUserId(criteriaSetRequest.getUserId());
        for(Long criteriaId : criteriaSetRequest.getCriteriaIds()){
            Optional<Criteria> criteria = criteriaRepository.findById(criteriaId);
            criteriaSet.getCriteriaList().add(criteria.get());
        }
        criteriaSetRepository.save(criteriaSet);
    }
    @Override
    public void createRule(RuleRequest ruleRequest){
        Rule rule = new Rule();
        rule.setName(ruleRequest.getName());
        rule.setUserId(ruleRequest.getUserId());
        for(Long criteriaSetId : ruleRequest.getCriteriaSetIds()){
            Optional<CriteriaSet> criteriaSet = criteriaSetRepository.findById(criteriaSetId);
            rule.getCriteriaSetList().add(criteriaSet.get());
        }
        ruleRepository.save(rule);

    }
    @Override
    public void createRuleSet(RuleSetRequest ruleSetRequest){
        RuleSet ruleSet = new RuleSet();
        ruleSet.setName(ruleSetRequest.getName());
        ruleSet.setUserId(ruleSetRequest.getUserId());
        for(Long ruleId : ruleSetRequest.getRuleIds()){
            Optional<Rule> rule = ruleRepository.findById(ruleId);
            ruleSet.getRules().add(rule.get());
        }
        ruleSetRepository.save(ruleSet);

    }
}
