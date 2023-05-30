package com.bayesiansamaritan.lifeplanner.service.impl;

import com.bayesiansamaritan.lifeplanner.enums.*;
import com.bayesiansamaritan.lifeplanner.model.BadHabit.BadHabit;
import com.bayesiansamaritan.lifeplanner.model.BadHabit.BadHabitTransaction;
import com.bayesiansamaritan.lifeplanner.model.BadHabit.BadHabitType;
import com.bayesiansamaritan.lifeplanner.model.Financial.*;
import com.bayesiansamaritan.lifeplanner.model.Goal.Goal;
import com.bayesiansamaritan.lifeplanner.model.Habit.Habit;
import com.bayesiansamaritan.lifeplanner.model.Habit.HabitTransaction;
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
import com.bayesiansamaritan.lifeplanner.repository.Financial.AccountRepository;
import com.bayesiansamaritan.lifeplanner.repository.Financial.AccountTypeRepository;
import com.bayesiansamaritan.lifeplanner.repository.Financial.ExpenseTypeRepository;
import com.bayesiansamaritan.lifeplanner.repository.Financial.FundRepository;
import com.bayesiansamaritan.lifeplanner.repository.Goal.GoalRepository;
import com.bayesiansamaritan.lifeplanner.repository.Habit.HabitRepository;
import com.bayesiansamaritan.lifeplanner.repository.Habit.HabitTransactionRepository;
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

import java.text.ParseException;
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
    private HabitTransactionRepository habitTransactionRepository;
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
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private AccountTypeRepository accountTypeRepository;
    @Autowired
    private FundRepository fundRepository;
    @Autowired
    private ExpenseTypeRepository expenseTypeRepository;
    @Autowired
    private BudgetService budgetService;
    @Autowired
    private AccountService accountService;


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
        criteriaSetRepository.save(criteriaSet);
    }
    @Override
    public void createRule(RuleRequest ruleRequest){
        Rule rule = new Rule();
        rule.setName(ruleRequest.getName());
        rule.setUserId(ruleRequest.getUserId());
        ruleRepository.save(rule);

    }
    @Override
    public void createRuleSet(RuleSetRequest ruleSetRequest){
        RuleSet ruleSet = new RuleSet();
        ruleSet.setName(ruleSetRequest.getName());
        ruleSet.setUserId(ruleSetRequest.getUserId());
        ruleSetRepository.save(ruleSet);
    }

    @Override
    public void modifyCriteriaSet(CriteriaSetModifyRequest criteriaSetModifyRequest){
        Optional<CriteriaSet> criteriaSet = criteriaSetRepository.findById(criteriaSetModifyRequest.getId());
        criteriaSetRepository.deleteById(criteriaSet.get().getId());
        CriteriaSet criteriaSet1 = new CriteriaSet();
        criteriaSet1.setUserId(criteriaSetModifyRequest.getUserId());
        criteriaSet1.setName(criteriaSetModifyRequest.getName());
        Set<Criteria> criteriaList = new HashSet<>();
        for (Long criteriaId : criteriaSetModifyRequest.getCriteriaIds()){
            Optional<Criteria> criteria = criteriaRepository.findById(criteriaId);
            criteriaList.add(criteria.get());
        }
        criteriaSet1.setCriteriaList(criteriaList);
        criteriaSetRepository.save(criteriaSet1);
    }

    @Override
    public void modifyRule(RulesModifyRequest ruleModifyRequest){
        Optional<Rule> rule = ruleRepository.findById(ruleModifyRequest.getId());
        ruleRepository.deleteById(ruleModifyRequest.getId());
        Rule rule1 = new Rule();
        rule1.setUserId(ruleModifyRequest.getUserId());
        rule1.setName(ruleModifyRequest.getName());
        Set<CriteriaSet> criteriaSets = new HashSet<>();
        for (Long criteriaSetId : ruleModifyRequest.getCriteriaSetIds()){
            Optional<CriteriaSet> criteriaSet  = criteriaSetRepository.findById(criteriaSetId);
            criteriaSets.add(criteriaSet.get());
        }
        rule1.setCriteriaSetList(criteriaSets);
        ruleRepository.save(rule1);
    }

    @Override
    public void modifyRuleSet(RuleSetModifyRequest ruleSetModifyRequest){
        Optional<RuleSet> ruleSet = ruleSetRepository.findById(ruleSetModifyRequest.getId());
        ruleSetRepository.deleteById(ruleSet.get().getId());
        RuleSet ruleSet1 = new RuleSet();
        ruleSet1.setUserId(ruleSetModifyRequest.getUserId());
        ruleSet1.setName(ruleSetModifyRequest.getName());
        Set<Rule> ruleList = new HashSet<>();
        for (Long ruleId : ruleSetModifyRequest.getRuleIds()){
            Optional<Rule> rule = ruleRepository.findById(ruleId);
            ruleList.add(rule.get());
        }
        ruleSet1.setRules(ruleList);
        ruleSetRepository.save(ruleSet1);
    }

    @Override
    public List<TypesResponse> getAllTypes(Long userId, String type){
        List<TypesResponse> typesResponses = new ArrayList<>();
        if (type.equals("TASK")){
            List<TaskType> list = taskTypeRepository.findByUserId(userId);
            for (TaskType type1 : list){
                TypesResponse typesResponse = new TypesResponse();
                typesResponse.setLabel(type1.getName());
                typesResponse.setValue(type1.getName());
                typesResponses.add(typesResponse);
            }
        }
        else if(type.equals("HABIT")){
            List<HabitType> list = habitTypeRepository.findByUserId(userId);
            for (HabitType type1 : list){
                TypesResponse typesResponse = new TypesResponse();
                typesResponse.setLabel(type1.getName());
                typesResponse.setValue(type1.getName());
                typesResponses.add(typesResponse);
            }
        }
        else if(type.equals("BAD_HABIT")){
            List<BadHabitType> list = badHabitTypeRepository.findByUserId(userId);
            for (BadHabitType type1 : list){
                TypesResponse typesResponse = new TypesResponse();
                typesResponse.setLabel(type1.getName());
                typesResponse.setValue(type1.getName());
                typesResponses.add(typesResponse);
            }
        }
        else if(type.equals("SKILL")){
            List<SkillType> list = skillTypeRepository.findByUserId(userId);
            for (SkillType type1 : list){
                TypesResponse typesResponse = new TypesResponse();
                typesResponse.setLabel(type1.getName());
                typesResponse.setValue(type1.getName());
                typesResponses.add(typesResponse);
            }
        }
        else if(type.equals("STAT")){
            List<StatsType> list = statsTypeRepository.findByUserId(userId);
            for (StatsType type1 : list){
                TypesResponse typesResponse = new TypesResponse();
                typesResponse.setLabel(type1.getName());
                typesResponse.setValue(type1.getName());
                typesResponses.add(typesResponse);
            }
        }
        else if(type.equals("ACCOUNT")){
            List<AccountType> list = accountTypeRepository.findByUserId(userId);
            for (AccountType type1 :list){
                TypesResponse typesResponse = new TypesResponse();
                typesResponse.setLabel(type1.getName());
                typesResponse.setValue(type1.getName());
                typesResponses.add(typesResponse);
            }
        }
        else if(type.equals("FUND")){
            List<Fund> list = fundRepository.findByUserId(userId);
            for (Fund type1 : list){
                TypesResponse typesResponse = new TypesResponse();
                typesResponse.setLabel(type1.getName());
                typesResponse.setValue(type1.getName());
                typesResponses.add(typesResponse);
            }
        }
        else if(type.equals("BUDGET")){
            List<ExpenseType> list = expenseTypeRepository.findByUserId(userId);
            for (ExpenseType type1 : list){
                TypesResponse typesResponse = new TypesResponse();
                typesResponse.setLabel(type1.getName());
                typesResponse.setValue(type1.getName());
                typesResponses.add(typesResponse);
            }
        }
        return typesResponses;
    }

    @Override
    public List<NamesResponse> getAllNames(Long userId, String type, String name) throws ParseException {

        List<NamesResponse> namesResponses = new ArrayList<>();
        if (type.equals("TASK")){
            List<TaskResponse> list = taskService.getAllTasksAndSubTasks(userId,true,name);
            for (TaskResponse type1 : list){
                NamesResponse namesResponse = new NamesResponse(type1.getId(),type1.getName());
                namesResponses.add(namesResponse);
            }
        }
        else if(type.equals("HABIT")){
            List<HabitResponse> list = habitService.getAllHabitsAndSubHabits(userId,true,name);
            for (HabitResponse type1 : list){
                NamesResponse namesResponse = new NamesResponse(type1.getId(),type1.getName());
                namesResponses.add(namesResponse);
            }
        }
        else if(type.equals("BAD_HABIT")){
            List<BadHabitResponse> list = badHabitService.getAllBadHabitsAndSubBadHabits(userId,true,name);
            for (BadHabitResponse type1 : list){
                NamesResponse namesResponse = new NamesResponse(type1.getId(),type1.getName());
                namesResponses.add(namesResponse);
            }
        }
        else if(type.equals("SKILL")){
            List<SkillResponse> list = skillService.getAllSkillsAndSubSkills(userId,true,name);
            for (SkillResponse type1 : list){
                NamesResponse namesResponse = new NamesResponse(type1.getId(),type1.getName());
                namesResponses.add(namesResponse);
            }
        }
        else if(type.equals("STAT")){
            List<StatsResponse> list = statsService.getAllStatsAndSubStats(userId,name);
            for (StatsResponse type1 : list){
                NamesResponse namesResponse = new NamesResponse(type1.getId(),type1.getName());
                namesResponses.add(namesResponse);
            }
        }
        else if(type.equals("BUDGET")){
            List<BudgetResponse> list = budgetService.getMonthlyBudgets(name,userId);
            for (BudgetResponse type1 : list){
                NamesResponse namesResponse = new NamesResponse(type1.getId(),type1.getCategoryName()+'-'+type1.getSubCategoryName());
                namesResponses.add(namesResponse);
            }
        }
        return namesResponses;

    }

    @Override
    public Float getCompletedPercentage(Long userId, Long goalId) throws ParseException {
        Optional<Goal> goal = goalRepository.findById(goalId);
        String reference = goal.get().getCompletedRuleEngineReference();
        return getRuleEnginePercentage(userId,reference);
    }
    @Override
    public Float getWorkPercentage(Long userId, Long goalId) throws ParseException {
        Optional<Goal> goal = goalRepository.findById(goalId);
        String reference = goal.get().getWorkRuleEngineReference();
        return getRuleEnginePercentage(userId,reference);
    }

    public Float getRuleEnginePercentage(Long userId, String reference) throws ParseException {
        if(reference==null){
            return 0F;
        }
        String[] referenceList = reference.split("/");
        String referenceType = referenceList[0];
        String referenceId = referenceList[1];

        if(referenceType.equals("Criteria")){
            Criteria criteria = criteriaRepository.findById(Long.valueOf(referenceId)).get();
            return getPercentage(userId,criteria);
        }
        else if(referenceType.equals("Criteria Set")) {
            CriteriaSet criteriaSet = criteriaSetRepository.findById(Long.valueOf(referenceId)).get();
            Float currentMaxPercentage = 0F;
            Float currentWeightage = 0F;
            for (Criteria criteria : criteriaSet.getCriteriaList()){
                Float currentPercentage = getPercentage(userId,criteria);
                if(currentPercentage>=currentMaxPercentage){
                    currentMaxPercentage = currentPercentage;
                    currentWeightage = criteria.getWeightage();
                }
            }
            return currentWeightage*currentMaxPercentage;
        }
        else if(referenceType.equals("Rule")){
            Rule rule = ruleRepository.findById(Long.valueOf(referenceId)).get();
            Float currentPercentage = 0F;
            Float totalWeightage = 0F;
            for (CriteriaSet criteriaSet : rule.getCriteriaSetList()){
                Float currentMaxPercentage = 0F;
                Float currentWeightage = 0F;
                for (Criteria criteria : criteriaSet.getCriteriaList()){
                    Float currentChildPercentage = getPercentage(userId,criteria);
                    if(currentChildPercentage>=currentMaxPercentage){
                        currentMaxPercentage = currentChildPercentage;
                        currentWeightage = criteria.getWeightage();
                    }
                }
                totalWeightage+=currentWeightage;
                currentPercentage+=currentWeightage*currentMaxPercentage;
            }
            return currentPercentage/totalWeightage;
        }
        else if(referenceType.equals("RuleSet")){
            RuleSet ruleSet = ruleSetRepository.findById(Long.valueOf(referenceId)).get();
            Float currentParentPercentage = 0F;
            Float totalParentWeightage = 0F;
            for (Rule rule : ruleSet.getRules()){
                Float currentPercentage = 1F;
                Float totalWeightage = 0F;
                for (CriteriaSet criteriaSet : rule.getCriteriaSetList()){
                    Float currentMaxPercentage = 0F;
                    Float currentWeightage = 0F;
                    for (Criteria criteria : criteriaSet.getCriteriaList()){
                        Float currentChildPercentage = getPercentage(userId,criteria);
                        if(currentChildPercentage>=currentMaxPercentage){
                            currentMaxPercentage = currentChildPercentage;
                            currentWeightage = criteria.getWeightage();
                        }
                    }
                    totalWeightage+=currentWeightage;
                    currentPercentage+=currentWeightage*currentMaxPercentage;
                }
                currentParentPercentage+=currentPercentage/totalWeightage;
                totalParentWeightage+=1;
            }
            return currentParentPercentage/totalParentWeightage;
        }

        return 0F;
    }

    public Float getPercentage(Long userId,Criteria criteriaResponse) throws ParseException {
        CriteriaEnum criteriaEnum = criteriaResponse.getCriteriaType();
        Float weightage = criteriaResponse.getWeightage();
        if(criteriaEnum.equals(CriteriaEnum.TASK)){
            TaskEnum taskEnum = TaskEnum.valueOf(criteriaResponse.getCondition());
            Task task = taskRepository.findByUserIdAndName(userId,criteriaResponse.getCategoryName());
            if(taskEnum.equals(TaskEnum.TASK_COMPLETED)){
                if(task.getCompleted().equals(true)){
                    return 100F;
                }
                else{
                    return 0F;
                }
            }
        }
        else if(criteriaEnum.equals(CriteriaEnum.HABIT)){
            Long value = criteriaResponse.getValue();
            HabitEnum habitEnum = HabitEnum.valueOf(criteriaResponse.getCondition());
            Habit habit = habitRepository.findById(Long.valueOf(criteriaResponse.getCategoryName())).get();
            if(habitEnum.equals(HabitEnum.HABIT_STREAK)){
                return (float) (habit.getStreak()*100/value);
            }
            else if (habitEnum.equals(HabitEnum.HABIT_TOTAL_TIMES)){
                return (float) (habit.getTotalTimes()*100/value);
            }
            else if (habitEnum.equals(HabitEnum.HABIT_TOTAL_TIME_SPENT)){
                return (float) (habit.getTotalTimeSpent()*100/value);
            }
            else if (habitEnum.equals(HabitEnum.HABIT_TOTAL_TIMES_WEEKLY)){
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.DATE,-7);
                List<HabitTransaction> habitTransactions = habitTransactionRepository.findByHabitIdAndCreatedAt(habit.getId(), cal.getTime());
                return (float) (habitTransactions.size()*100/value);
            }
            else if (habitEnum.equals(HabitEnum.HABIT_TOTAL_TIMES_MONTHLY)){
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.DATE,-30);
                List<HabitTransaction> habitTransactions = habitTransactionRepository.findByHabitIdAndCreatedAt(habit.getId(), cal.getTime());
                return (float) (habitTransactions.size()*100/value);
            }
        }
        else if(criteriaEnum.equals(CriteriaEnum.BAD_HABIT)){
            Long value = criteriaResponse.getValue();
            BadHabitEnum badHabitEnum = BadHabitEnum.valueOf(criteriaResponse.getCondition());
            BadHabit badHabit = badHabitRepository.findByUserIdAndName(userId,criteriaResponse.getCategoryName());
            if(badHabitEnum.equals(BadHabitEnum.BAD_HABIT_LAST_TIME)){
                Date date = new Date();
                Long date_difference = date.getTime()-badHabit.getUpdatedAt().getTime();
                Long days_difference = (date_difference/(1000*60*60*24))%365;
                return (float) (days_difference*100/value);
            }
            else if(badHabitEnum.equals(BadHabitEnum.BAD_HABIT_WEEKLY)){
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.DATE,-7);
                List<BadHabitTransaction> badHabitTransactions = badHabitTransactionRepository.getBadHabitTransactionsByHabitIdAndUpdatedAt(badHabit.getId(),cal.getTime());
                return (float) ((value-badHabitTransactions.size())*100/value);
            }
            else if(badHabitEnum.equals(BadHabitEnum.BAD_HABIT_MONTHLY)){
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.DATE,-30);
                List<BadHabitTransaction> badHabitTransactions = badHabitTransactionRepository.getBadHabitTransactionsByHabitIdAndUpdatedAt(badHabit.getId(),cal.getTime());
                return (float) ((value-badHabitTransactions.size())*100/value);
            }
        }
        else if(criteriaEnum.equals(CriteriaEnum.SKILL)){
            Long value = criteriaResponse.getValue();
            SkillEnum skillEnum = SkillEnum.valueOf(criteriaResponse.getCondition());
            Skill skill = skillRepository.findByUserIdAndName(userId,criteriaResponse.getCategoryName());
            if(skillEnum.equals(SkillEnum.SKILL_COMPLETED)){
                if(skill.getCompleted().equals(true)){
                    return 100F;
                }
                else{
                    return 0F;
                }
            }
            else if(skillEnum.equals(SkillEnum.SKILL_TOTAL_TIME_SPENT)){
                return ((float) skill.getTimeTaken()*100/value);
            }
        }
        else if(criteriaEnum.equals(CriteriaEnum.STAT)){
            Long value = criteriaResponse.getValue();
            StatEnum statEnum = StatEnum.valueOf(criteriaResponse.getCondition());
            Stats stat = statsRepository.findById(Long.valueOf(criteriaResponse.getCategoryName())).get();
            StatsTransaction statsTransaction = statsTransactionRepository.findByStatId(stat.getId()).get(0);
            if(statEnum.equals(StatEnum.STAT_HIGHER_PREFERRED)){
                Float totalProgress = value - statsTransaction.getValue();
                Float currentProgress = stat.getValue() - statsTransaction.getValue();
                return currentProgress*100/totalProgress;
            }
            else if (statEnum.equals(StatEnum.STAT_LOWER_PREFERRED)){
                Float totalProgress = statsTransaction.getValue() - value;
                Float currentProgress = statsTransaction.getValue() - stat.getValue();
                return currentProgress*100/totalProgress;
            }
        }
        else if(criteriaEnum.equals(CriteriaEnum.ACCOUNT)){
            Long value = criteriaResponse.getValue();
            Long balance = 0L;
            AccountEnum accountEnum = AccountEnum.valueOf(criteriaResponse.getCondition());
            List<AccountBalanceResponse> accountBalances = accountService.getAllAccountBalances(userId);
            for (AccountBalanceResponse accountBalanceResponse :accountBalances){
                if (accountBalanceResponse.getName().equals(criteriaResponse.getCategory())){
                    balance = accountBalanceResponse.getBalance();
                }
            }
            if(accountEnum.equals(AccountEnum.ACCOUNT_REACHED)){
                return ((float) balance)*100/value;
            }
        }
        else if(criteriaEnum.equals(CriteriaEnum.FUND)){
            FundEnum fundEnum = FundEnum.valueOf(criteriaResponse.getCondition());
            Fund fund = fundRepository.findByUserIdAndName(userId,criteriaResponse.getCategory());
            if(fundEnum.equals(FundEnum.FUND_REACHED)){
                return ((float) fund.getAmountAllocated()*100/fund.getAmountNeeded());
            }
        }
        else if(criteriaEnum.equals(CriteriaEnum.BUDGET)){
            BudgetEnum budgetEnum = BudgetEnum.valueOf(criteriaResponse.getCondition());
            BudgetResponse budgetResponse = budgetService.getMonthlyBudget(Long.valueOf(criteriaResponse.getCategoryName()));
            if(budgetEnum.equals(BudgetEnum.BUDGET_HIGHER_PREFERRED)){
                return ((float) budgetResponse.getAmountSpent()*100/budgetResponse.getBudgetAmount());
            }
            else if(budgetEnum.equals(BudgetEnum.BUDGET_LOWER_PREFERRED)){
                return ((float) (budgetResponse.getBudgetAmount()-budgetResponse.getAmountSpent())*100/budgetResponse.getBudgetAmount());
            }
        }
        return 0F;
    };
}
