package com.bayesiansamaritan.lifeplanner.service.impl;

import com.bayesiansamaritan.lifeplanner.enums.BadHabitEnum;
import com.bayesiansamaritan.lifeplanner.enums.HabitEnum;
import com.bayesiansamaritan.lifeplanner.enums.StatEnum;
import com.bayesiansamaritan.lifeplanner.enums.TaskEnum;
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
import com.bayesiansamaritan.lifeplanner.repository.Goal.*;
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
import com.bayesiansamaritan.lifeplanner.request.Rule.RuleCreateRequest;
import com.bayesiansamaritan.lifeplanner.response.*;
import com.bayesiansamaritan.lifeplanner.security.jwt.JwtUtils;
import com.bayesiansamaritan.lifeplanner.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class RuleServiceImpl implements RuleService {

    @Autowired
    private BadHabitRuleRepository badHabitRuleRepository;
    @Autowired
    private HabitRuleRepository habitRuleRepository;
    @Autowired
    private TaskRuleRepository taskRuleRepository;
    @Autowired
    private SkillRuleRepository skillRuleRepository;
    @Autowired
    private StatRuleRepository statRuleRepository;

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

    public List<RuleResponse> getAllCompletedRules(Long userId, Long goalId){

        List<RuleResponse> ruleResponses = new ArrayList<>();
        List<TaskRule> taskRules = taskRuleRepository.findCompletedRulesByUserIdAndGoalIdAndActive(userId,goalId,true);
        List<HabitRule> habitRules = habitRuleRepository.findCompletedRulesByUserIdAndGoalIdAndActive(userId,goalId,true);
        List<BadHabitRule> badHabitRules = badHabitRuleRepository.findCompletedRulesByUserIdAndGoalIdAndActive(userId,goalId,true);
        List<SkillRule> skillRules = skillRuleRepository.findCompletedRulesByUserIdAndGoalIdAndActive(userId,goalId,true);
        List<StatRule> statRules = statRuleRepository.findCompletedRulesByUserIdAndGoalIdAndActive(userId,goalId,true);
        for (TaskRule taskRule: taskRules){
            RuleResponse ruleResponse = new RuleResponse(taskRule.getId(),taskRule.getCreatedAt(),taskRule.getUpdatedAt(),taskRule.getName(),
                    taskRule.getTaskId(),taskRule.getGoalId(),taskRule.getActive(),taskRule.getValue(),taskRule.getTaskConditionType().toString(),
                    taskRule.getDescription(),"task",taskRule.getWeightage());
            ruleResponses.add(ruleResponse);
        }
        for (HabitRule habitRule: habitRules){
            RuleResponse ruleResponse = new RuleResponse(habitRule.getId(),habitRule.getCreatedAt(),habitRule.getUpdatedAt(),habitRule.getName(),
                    habitRule.getHabitId(),habitRule.getGoalId(),habitRule.getActive(),habitRule.getValue(),habitRule.getHabitConditionType().toString(),
                    habitRule.getDescription(),"habit",habitRule.getWeightage());
            ruleResponses.add(ruleResponse);
        }
        for (BadHabitRule badHabitRule: badHabitRules){
            RuleResponse ruleResponse = new RuleResponse(badHabitRule.getId(),badHabitRule.getCreatedAt(),badHabitRule.getUpdatedAt(),badHabitRule.getName(),
                    badHabitRule.getBadHabitId(),badHabitRule.getGoalId(),badHabitRule.getActive(),badHabitRule.getValue(),badHabitRule.getBadHabitConditionType().toString(),
                    badHabitRule.getDescription(),"badHabit",badHabitRule.getWeightage());
            ruleResponses.add(ruleResponse);
        }
        for (SkillRule skillRule: skillRules){
            RuleResponse ruleResponse = new RuleResponse(skillRule.getId(),skillRule.getCreatedAt(),skillRule.getUpdatedAt(),skillRule.getName(),
                    skillRule.getSkillId(),skillRule.getGoalId(),skillRule.getActive(),skillRule.getValue(),"",
                    skillRule.getDescription(),"skill",skillRule.getWeightage());
            ruleResponses.add(ruleResponse);
        }
        for (StatRule statRule: statRules){
            RuleResponse ruleResponse = new RuleResponse(statRule.getId(),statRule.getCreatedAt(),statRule.getUpdatedAt(),statRule.getName(),
                    statRule.getStatId(),statRule.getGoalId(),statRule.getActive(),statRule.getValue(),"",
                    statRule.getDescription(),"stat",statRule.getWeightage());
            ruleResponses.add(ruleResponse);
        }
        return ruleResponses;
    };

    public List<RuleResponse> getAllWorkRules(Long userId, Long goalId){

        List<RuleResponse> ruleResponses = new ArrayList<>();
        List<TaskRule> taskRules = taskRuleRepository.findWorkRulesByUserIdAndGoalIdAndActive(userId,goalId,true);
        List<HabitRule> habitRules = habitRuleRepository.findWorkRulesByUserIdAndGoalIdAndActive(userId,goalId,true);
        List<BadHabitRule> badHabitRules = badHabitRuleRepository.findWorkRulesByUserIdAndGoalIdAndActive(userId,goalId,true);
        List<SkillRule> skillRules = skillRuleRepository.findWorkRulesByUserIdAndGoalIdAndActive(userId,goalId,true);
        List<StatRule> statRules = statRuleRepository.findWorkRulesByUserIdAndGoalIdAndActive(userId,goalId,true);
        for (TaskRule taskRule: taskRules){
            RuleResponse ruleResponse = new RuleResponse(taskRule.getId(),taskRule.getCreatedAt(),taskRule.getUpdatedAt(),taskRule.getName(),
                    taskRule.getTaskId(),taskRule.getGoalId(),taskRule.getActive(),taskRule.getValue(),taskRule.getTaskConditionType().toString(),
                    taskRule.getDescription(),"task",taskRule.getWeightage());
            ruleResponses.add(ruleResponse);
        }
        for (HabitRule habitRule: habitRules){
            RuleResponse ruleResponse = new RuleResponse(habitRule.getId(),habitRule.getCreatedAt(),habitRule.getUpdatedAt(),habitRule.getName(),
                    habitRule.getHabitId(),habitRule.getGoalId(),habitRule.getActive(),habitRule.getValue(),habitRule.getHabitConditionType().toString(),
                    habitRule.getDescription(),"habit",habitRule.getWeightage());
            ruleResponses.add(ruleResponse);
        }
        for (BadHabitRule badHabitRule: badHabitRules){
            RuleResponse ruleResponse = new RuleResponse(badHabitRule.getId(),badHabitRule.getCreatedAt(),badHabitRule.getUpdatedAt(),badHabitRule.getName(),
                    badHabitRule.getBadHabitId(),badHabitRule.getGoalId(),badHabitRule.getActive(),badHabitRule.getValue(),badHabitRule.getBadHabitConditionType().toString(),
                    badHabitRule.getDescription(),"badHabit",badHabitRule.getWeightage());
            ruleResponses.add(ruleResponse);
        }
        for (SkillRule skillRule: skillRules){
            RuleResponse ruleResponse = new RuleResponse(skillRule.getId(),skillRule.getCreatedAt(),skillRule.getUpdatedAt(),skillRule.getName(),
                    skillRule.getSkillId(),skillRule.getGoalId(),skillRule.getActive(),skillRule.getValue(),"",
                    skillRule.getDescription(),"skill",skillRule.getWeightage());
            ruleResponses.add(ruleResponse);
        }
        for (StatRule statRule: statRules){
            RuleResponse ruleResponse = new RuleResponse(statRule.getId(),statRule.getCreatedAt(),statRule.getUpdatedAt(),statRule.getName(),
                    statRule.getStatId(),statRule.getGoalId(),statRule.getActive(),statRule.getValue(),"",
                    statRule.getDescription(),"stat",statRule.getWeightage());
            ruleResponses.add(ruleResponse);
        }
        return ruleResponses;
    };

    public void createRule(Long userId, RuleCreateRequest ruleRequest) {
        if (ruleRequest.getRuleType().equals("task")) {
            taskRuleRepository.save(new TaskRule(ruleRequest.getName(), ruleRequest.getId(), ruleRequest.getGoalId(), true, userId,
                    TaskEnum.valueOf(ruleRequest.getConditionType()), ruleRequest.getValue(),ruleRequest.getWeightage(),ruleRequest.getRuleCategory()));
        } else if (ruleRequest.getRuleType().equals("habit")) {
            habitRuleRepository.save(new HabitRule(ruleRequest.getName(), ruleRequest.getId(), ruleRequest.getGoalId(), true, userId,
                    HabitEnum.valueOf(ruleRequest.getConditionType()), ruleRequest.getValue(),ruleRequest.getWeightage(),ruleRequest.getRuleCategory()));
        } else if (ruleRequest.getRuleType().equals("badHabit")) {
            badHabitRuleRepository.save(new BadHabitRule(ruleRequest.getName(), ruleRequest.getId(), ruleRequest.getGoalId(), true, userId,
                    BadHabitEnum.valueOf(ruleRequest.getConditionType()), ruleRequest.getValue(),ruleRequest.getWeightage(),ruleRequest.getRuleCategory()));
        } else if (ruleRequest.getRuleType().equals("skill")) {
            skillRuleRepository.save(new SkillRule(ruleRequest.getName(), ruleRequest.getId(), ruleRequest.getGoalId(), true, userId,
                    ruleRequest.getValue(),ruleRequest.getWeightage(),ruleRequest.getRuleCategory()));
        } else if (ruleRequest.getRuleType().equals("stat")) {
            statRuleRepository.save(new StatRule(ruleRequest.getName(), ruleRequest.getId(), ruleRequest.getGoalId(), true, userId,
                    StatEnum.valueOf(ruleRequest.getConditionType()),ruleRequest.getValue(),ruleRequest.getWeightage(),ruleRequest.getRuleCategory()));
        }
    }


    public List<TypesResponse> getAllTypes(Long userId, String type){
        List<TypesResponse> typesResponses = new ArrayList<>();
        if (type.equals("task")){
            List<TaskType> list = taskTypeRepository.findByUserId(userId);
            for (TaskType type1 : list){
                TypesResponse typesResponse = new TypesResponse();
                typesResponse.setLabel(type1.getName());
                typesResponse.setValue(type1.getName());
                typesResponses.add(typesResponse);
            }
        }
        else if(type.equals("habit")){
            List<HabitType> list = habitTypeRepository.findByUserId(userId);
            for (HabitType type1 : list){
                TypesResponse typesResponse = new TypesResponse();
                typesResponse.setLabel(type1.getName());
                typesResponse.setValue(type1.getName());
                typesResponses.add(typesResponse);
            }
        }
        else if(type.equals("badHabit")){
            List<BadHabitType> list = badHabitTypeRepository.findByUserId(userId);
            for (BadHabitType type1 : list){
                TypesResponse typesResponse = new TypesResponse();
                typesResponse.setLabel(type1.getName());
                typesResponse.setValue(type1.getName());
                typesResponses.add(typesResponse);
            }
        }
        else if(type.equals("skill")){
            List<SkillType> list = skillTypeRepository.findByUserId(userId);
            for (SkillType type1 : list){
                TypesResponse typesResponse = new TypesResponse();
                typesResponse.setLabel(type1.getName());
                typesResponse.setValue(type1.getName());
                typesResponses.add(typesResponse);
            }
        }
        else if(type.equals("stat")){
            List<StatsType> list = statsTypeRepository.findByUserId(userId);
            for (StatsType type1 : list){
                TypesResponse typesResponse = new TypesResponse();
                typesResponse.setLabel(type1.getName());
                typesResponse.setValue(type1.getName());
                typesResponses.add(typesResponse);
            }
        }
        return typesResponses;
    }
    public List<NamesResponse> getAllNames(Long userId, String type, String name){

        List<NamesResponse> namesResponses = new ArrayList<>();
        if (type.equals("task")){
            List<TaskResponse> list = taskService.getAllTasksAndSubTasks(userId,true,name);
            for (TaskResponse type1 : list){
                NamesResponse namesResponse = new NamesResponse(type1.getId(),type1.getName());
                namesResponses.add(namesResponse);
            }
        }
        else if(type.equals("habit")){
            List<HabitResponse> list = habitService.getAllHabitsAndSubHabits(userId,true,name);
            for (HabitResponse type1 : list){
                NamesResponse namesResponse = new NamesResponse(type1.getId(),type1.getName());
                namesResponses.add(namesResponse);
            }
        }
        else if(type.equals("badHabit")){
            List<BadHabitResponse> list = badHabitService.getAllBadHabitsAndSubBadHabits(userId,true,name);
            for (BadHabitResponse type1 : list){
                NamesResponse namesResponse = new NamesResponse(type1.getId(),type1.getName());
                namesResponses.add(namesResponse);
            }
        }
        else if(type.equals("skill")){
            List<SkillResponse> list = skillService.getAllSkillsAndSubSkills(userId,true,name);
            for (SkillResponse type1 : list){
                NamesResponse namesResponse = new NamesResponse(type1.getId(),type1.getName());
                namesResponses.add(namesResponse);
            }
        }
        else if(type.equals("stat")){
            List<StatsResponse> list = statsService.getAllStatsAndSubStats(userId,name);
            for (StatsResponse type1 : list){
                NamesResponse namesResponse = new NamesResponse(type1.getId(),type1.getName());
                namesResponses.add(namesResponse);
            }
        }
        return namesResponses;

    }

    public Float getCompletedPercentage(Long userId, Long goalId){
        List<Float> completePercentages = new ArrayList<>();
        List<Float> weightages = new ArrayList<>();
        List<TaskRule> taskRules = taskRuleRepository.findCompletedRulesByUserIdAndGoalIdAndActive(userId,goalId,true);
        List<HabitRule> habitRules = habitRuleRepository.findCompletedRulesByUserIdAndGoalIdAndActive(userId,goalId,true);
        List<BadHabitRule> badHabitRules = badHabitRuleRepository.findCompletedRulesByUserIdAndGoalIdAndActive(userId,goalId,true);
        List<SkillRule> skillRules = skillRuleRepository.findCompletedRulesByUserIdAndGoalIdAndActive(userId,goalId,true);
        List<StatRule> statRules = statRuleRepository.findCompletedRulesByUserIdAndGoalIdAndActive(userId,goalId,true);
        for (TaskRule taskRule: taskRules){
            weightages.add(taskRule.getWeightage());
            Optional<Task> task = taskRepository.findById(taskRule.getTaskId());
            if(taskRule.getTaskConditionType().equals(TaskEnum.TASK_COMPLETED)){
                if(task.get().getActive().equals(false)){
                    completePercentages.add(100F);
                }
                else{
                    completePercentages.add(0F);
                }
            }
        };
        for (HabitRule habitRule: habitRules){
            weightages.add(habitRule.getWeightage());
            Optional<Habit> habit = habitRepository.findById(habitRule.getHabitId());
            if(habitRule.getHabitConditionType().equals(HabitEnum.HABIT_STREAK)){
                completePercentages.add((float) (habit.get().getStreak()/habitRule.getValue()));
            }
            else if (habitRule.getHabitConditionType().equals(HabitEnum.HABIT_TOTAL_TIMES)){
                completePercentages.add((float) (habit.get().getTotalTimes()/habitRule.getValue()));
            }
            else if(habitRule.getHabitConditionType().equals(HabitEnum.HABIT_TOTAL_TIME_SPENT)){
                completePercentages.add((float) (habit.get().getTotalTimeSpent()/habitRule.getValue()));
            }
        }
        for (BadHabitRule badHabitRule: badHabitRules){
            weightages.add(badHabitRule.getWeightage());
            Optional<BadHabit> badHabit = badHabitRepository.findById(badHabitRule.getBadHabitId());
            if(badHabitRule.getBadHabitConditionType().equals(BadHabitEnum.BAD_HABIT_MONTHLY)){
                LocalDate currentDate = LocalDate.now();
                LocalDate currentDateMinus30days = currentDate.minusDays(30);
                Date date = Date.from(currentDateMinus30days.atStartOfDay(ZoneId.systemDefault()).toInstant());
                List<BadHabitTransaction> badHabitTransactions = badHabitTransactionRepository.getBadHabitTransactionsByHabitIdAndUpdatedAt(badHabitRule.getBadHabitId(),
                        date);
                if (badHabitTransactions.size()<=badHabitRule.getValue()){
                    completePercentages.add(100F);
                }
                else{
                    completePercentages.add(0F);
                }
            }
            else if(badHabitRule.getBadHabitConditionType().equals(BadHabitEnum.BAD_HABIT_YEARLY)){
                LocalDate currentDate = LocalDate.now();
                LocalDate currentDateMinus30days = currentDate.minusDays(365);
                Date date = Date.from(currentDateMinus30days.atStartOfDay(ZoneId.systemDefault()).toInstant());
                List<BadHabitTransaction> badHabitTransactions = badHabitTransactionRepository.getBadHabitTransactionsByHabitIdAndUpdatedAt(badHabitRule.getBadHabitId(),
                        date);
                if (badHabitTransactions.size()<=badHabitRule.getValue()){
                    completePercentages.add(100F);
                }
                else{
                    completePercentages.add(0F);
                }
            }
            else if(badHabitRule.getBadHabitConditionType().equals(BadHabitEnum.BAD_HABIT_LAST_TIME)){
                LocalDateTime currentDateTime = LocalDateTime.now();
                LocalDateTime currentDateMinus30days = currentDateTime.minusHours(badHabitRule.getValue());
                Date date = Date.from(currentDateMinus30days.atZone(ZoneId.systemDefault()).toInstant());
                if (date.before(badHabit.get().getUpdatedAt())){
                    completePercentages.add(100F);
                }
                else{
                    completePercentages.add(0F);
                }
            }
        }
        for (SkillRule skillRule:skillRules){
            weightages.add(skillRule.getWeightage());
            Optional<Skill> skill = skillRepository.findById(skillRule.getSkillId());
            if(skill.get().getCompleted()){
                completePercentages.add(100F);
            }
            else{
                completePercentages.add(0F);
            }
        }
        for (StatRule statRule:statRules){
            weightages.add(statRule.getWeightage());
            Optional<Stats> stats = statsRepository.findById(statRule.getStatId());
            StatsTransaction statsTransaction = statsTransactionRepository.findByStatId(statRule.getStatId()).get(0);
            if(statRule.getStatConditionType().equals(StatEnum.STAT_HIGHER_PREFERRED)){
                if(stats.get().getValue()>=statRule.getValue()){
                    completePercentages.add(100F);
                }
                else{
                    Float total_progress = statRule.getValue() - statsTransaction.getValue();
                    Float current_progress = stats.get().getValue() - statsTransaction.getValue();
                    completePercentages.add(current_progress*100/total_progress);
                }
            }
            else if(statRule.getStatConditionType().equals(StatEnum.STAT_LOWER_PREFERRED)){
                if(stats.get().getValue()<=statRule.getValue()){
                    completePercentages.add(100F);
                }
                else{
                    Float total_progress = statsTransaction.getValue() - statRule.getValue();
                    Float current_progress = statsTransaction.getValue() - stats.get().getValue();
                    completePercentages.add(current_progress*100/total_progress);
                }
            }
        }
        Float completePercentage = 0F;
        Float totalWeightage = 0F;
        for (Float weightage: weightages){
            totalWeightage+=weightage;
        }
        if (completePercentages.size()>0){
            int start =0;
            for(Float percentage : completePercentages){
                completePercentage+=percentage*weightages.get(start)/totalWeightage;
                start+=1;
            }
            completePercentage = completePercentage/completePercentages.size();
            return completePercentage;
        }
        return completePercentage;
    }

    public Float getWorkPercentage(Long userId, Long goalId){
        List<Float> workPercentages = new ArrayList<>();
        List<Float> weightages = new ArrayList<>();
        List<TaskRule> taskRules = taskRuleRepository.findWorkRulesByUserIdAndGoalIdAndActive(userId,goalId,true);
        List<HabitRule> habitRules = habitRuleRepository.findWorkRulesByUserIdAndGoalIdAndActive(userId,goalId,true);
        List<BadHabitRule> badHabitRules = badHabitRuleRepository.findWorkRulesByUserIdAndGoalIdAndActive(userId,goalId,true);
        for (TaskRule taskRule: taskRules){
            weightages.add(taskRule.getWeightage());
            Optional<Task> task = taskRepository.findById(taskRule.getTaskId());
            if(taskRule.getTaskConditionType().equals(TaskEnum.TASK_COMPLETED)){
                if(task.get().getActive().equals(false)){
                    workPercentages.add(100F);
                }
                else{
                    workPercentages.add(0F);
                }
            }
        };
        for (HabitRule habitRule: habitRules){
            weightages.add(habitRule.getWeightage());
            Optional<Habit> habit = habitRepository.findById(habitRule.getHabitId());
            if(habitRule.getHabitConditionType().equals(HabitEnum.HABIT_STREAK)){
                workPercentages.add((float) (habit.get().getStreak()*100/habitRule.getValue()));
            }
            else if (habitRule.getHabitConditionType().equals(HabitEnum.HABIT_TOTAL_TIMES)){
                workPercentages.add((float) (habit.get().getTotalTimes()*100/habitRule.getValue()));
            }
            else if(habitRule.getHabitConditionType().equals(HabitEnum.HABIT_TOTAL_TIME_SPENT)){
                workPercentages.add((float) (habit.get().getTotalTimeSpent()*100/habitRule.getValue()));
            }
        }
        for (BadHabitRule badHabitRule: badHabitRules){
            weightages.add(badHabitRule.getWeightage());
            Optional<BadHabit> badHabit = badHabitRepository.findById(badHabitRule.getBadHabitId());
            if(badHabitRule.getBadHabitConditionType().equals(BadHabitEnum.BAD_HABIT_MONTHLY)){
                LocalDate currentDate = LocalDate.now();
                LocalDate currentDateMinus30days = currentDate.minusDays(30);
                Date date = Date.from(currentDateMinus30days.atStartOfDay(ZoneId.systemDefault()).toInstant());
                List<BadHabitTransaction> badHabitTransactions = badHabitTransactionRepository.getBadHabitTransactionsByHabitIdAndUpdatedAt(badHabitRule.getBadHabitId(),
                        date);
                if (badHabitTransactions.size()<=badHabitRule.getValue()){
                    workPercentages.add(100F);
                }
                else{
                    workPercentages.add(0F);
                }
            }
            else if(badHabitRule.getBadHabitConditionType().equals(BadHabitEnum.BAD_HABIT_YEARLY)){
                LocalDate currentDate = LocalDate.now();
                LocalDate currentDateMinus30days = currentDate.minusDays(365);
                Date date = Date.from(currentDateMinus30days.atStartOfDay(ZoneId.systemDefault()).toInstant());
                List<BadHabitTransaction> badHabitTransactions = badHabitTransactionRepository.getBadHabitTransactionsByHabitIdAndUpdatedAt(badHabitRule.getBadHabitId(),
                        date);
                if (badHabitTransactions.size()<=badHabitRule.getValue()){
                    workPercentages.add(100F);
                }
                else{
                    workPercentages.add(0F);
                }
            }
            else if(badHabitRule.getBadHabitConditionType().equals(BadHabitEnum.BAD_HABIT_LAST_TIME)){
                LocalDateTime currentDateTime = LocalDateTime.now();
                LocalDateTime currentDateMinus30days = currentDateTime.minusHours(badHabitRule.getValue());
                Date date = Date.from(currentDateMinus30days.atZone(ZoneId.systemDefault()).toInstant());
                if (date.before(badHabit.get().getUpdatedAt())){
                    workPercentages.add(100F);
                }
                else{
                    workPercentages.add(0F);
                }
            }
        }
        Float workPercentage = 0F;
        Float totalWeightage = 0F;
        for (Float weightage: weightages){
            totalWeightage+=weightage;
        }
        if (workPercentages.size()>0){
            int start =0;
            for(Float percentage : workPercentages){
                workPercentage+=percentage*weightages.get(start)/totalWeightage;
                start+=1;
            }
            workPercentage = workPercentage/workPercentages.size();
            return workPercentage;
        }
        return workPercentage;
    }

}
