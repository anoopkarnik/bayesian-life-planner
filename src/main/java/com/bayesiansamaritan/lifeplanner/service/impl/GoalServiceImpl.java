package com.bayesiansamaritan.lifeplanner.service.impl;

import com.bayesiansamaritan.lifeplanner.model.Goal.Goal;
import com.bayesiansamaritan.lifeplanner.model.Goal.GoalType;
import com.bayesiansamaritan.lifeplanner.repository.Goal.GoalRepository;
import com.bayesiansamaritan.lifeplanner.repository.Goal.GoalTypeRepository;
import com.bayesiansamaritan.lifeplanner.response.GoalResponse;
import com.bayesiansamaritan.lifeplanner.service.GoalService;
import com.bayesiansamaritan.lifeplanner.service.RuleEngineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class GoalServiceImpl implements GoalService {

    @Autowired
    GoalRepository goalRepository;

    @Autowired
    GoalTypeRepository goalTypeRepository;
    @Autowired
    RuleEngineService ruleEngineService;

    @Override
    public List<GoalResponse> getAllGoals(Long userId, Boolean active, String goalTypeName) throws ParseException {

        GoalType goalType = goalTypeRepository.findByNameAndUserId(goalTypeName,userId);
        List<Goal> goals = goalRepository.findRootGoalsByUserIdAndActiveAndGoalTypeId(userId,active,goalType.getId());
        List<GoalResponse> goalResponses = new ArrayList<>();
        for (Goal goal: goals){
            Optional<List<Goal>> childGoals1 =  goalRepository.findGoalsByUserIdAndActiveAndParentGoalId(userId,active,goal.getId());
            Float completedPercentage = ruleEngineService.getCompletedPercentage(userId,goal.getId());
            goalRepository.modifyCompletedPercentage(goal.getId(),completedPercentage);
            Float workPercentage = ruleEngineService.getWorkPercentage(userId,goal.getId());
            goalRepository.modifyWorkPercentage(goal.getId(),workPercentage);
            GoalResponse goalResponse = new GoalResponse(goal.getId(),goal.getCreatedAt(),
                    goal.getUpdatedAt(),goal.getName(),goal.getDueDate(),goalType.getName(),
                    goal.getDescription(),completedPercentage,workPercentage,goal.getStartDate(),goal.getTimeTaken(),goal.getActive(),
                    goal.getHidden(),goal.getCompleted());
            if (childGoals1.isPresent()){
                List<GoalResponse> childGoalResponses1 = new ArrayList<>();
                for(Goal childGoal1 : childGoals1.get()) {
                    Optional<GoalType> childGoalType1 = goalTypeRepository.findById(childGoal1.getGoalTypeId());
                    Float child1CompletedPercentage = ruleEngineService.getCompletedPercentage(userId,childGoal1.getId());
                    goalRepository.modifyCompletedPercentage(childGoal1.getId(),child1CompletedPercentage);
                    Float child1WorkPercentage = ruleEngineService.getWorkPercentage(userId,childGoal1.getId());
                    goalRepository.modifyWorkPercentage(childGoal1.getId(),child1WorkPercentage);
                    Optional<List<Goal>> childGoals2 =  goalRepository.findGoalsByUserIdAndActiveAndParentGoalId(userId,active,childGoal1.getId());
                    GoalResponse childGoalResponse1 = new GoalResponse(childGoal1.getId(), childGoal1.getCreatedAt(),
                            childGoal1.getUpdatedAt(), childGoal1.getName(), childGoal1.getDueDate(), childGoalType1.get().getName(),
                            childGoal1.getDescription(),child1CompletedPercentage,child1WorkPercentage,childGoal1.getStartDate(),
                            childGoal1.getTimeTaken(),childGoal1.getActive(),
                            childGoal1.getHidden(),childGoal1.getCompleted());
                    if (childGoals2.isPresent()){
                        List<GoalResponse> childGoalResponses2 = new ArrayList<>();
                        for(Goal childGoal2 : childGoals2.get()){
                            Optional<GoalType>  childGoalType2 = goalTypeRepository.findById(childGoal2.getGoalTypeId());
                            Float child2CompletedPercentage = ruleEngineService.getCompletedPercentage(userId,childGoal2.getId());
                            goalRepository.modifyCompletedPercentage(childGoal2.getId(),child2CompletedPercentage);
                            Float child2WorkPercentage = ruleEngineService.getWorkPercentage(userId,childGoal2.getId());
                            goalRepository.modifyWorkPercentage(childGoal2.getId(),child2WorkPercentage);
                            Optional<List<Goal>> childGoals3 =  goalRepository.findGoalsByUserIdAndActiveAndParentGoalId(userId,active,childGoal2.getId());
                            GoalResponse childGoalResponse2 = new GoalResponse(childGoal2.getId(), childGoal2.getCreatedAt(),
                                    childGoal2.getUpdatedAt(), childGoal2.getName(), childGoal2.getDueDate(), childGoalType2.get().getName(),
                                    childGoal2.getDescription(),child2CompletedPercentage,child2WorkPercentage,childGoal2.getStartDate(),childGoal2.getTimeTaken(),
                                    childGoal2.getActive(),childGoal2.getHidden(),childGoal2.getCompleted());
                            if (childGoals3.isPresent()){
                                List<GoalResponse> childGoalResponses3 = new ArrayList<>();
                                for(Goal childGoal3 : childGoals3.get()){
                                    Optional<GoalType>  childGoalType3 = goalTypeRepository.findById(childGoal2.getGoalTypeId());
                                    Float child3CompletedPercentage = ruleEngineService.getCompletedPercentage(userId,childGoal3.getId());
                                    goalRepository.modifyCompletedPercentage(childGoal3.getId(),child3CompletedPercentage);
                                    Float child3WorkPercentage = ruleEngineService.getWorkPercentage(userId,childGoal3.getId());
                                    goalRepository.modifyWorkPercentage(childGoal3.getId(),child3WorkPercentage);
                                    Optional<List<Goal>> childGoals4 =  goalRepository.findGoalsByUserIdAndActiveAndParentGoalId(userId,active,childGoal3.getId());
                                    GoalResponse childGoalResponse3 = new GoalResponse(childGoal3.getId(), childGoal3.getCreatedAt(),
                                            childGoal3.getUpdatedAt(), childGoal3.getName(), childGoal3.getDueDate(), childGoalType3.get().getName(),
                                            childGoal3.getDescription(),child3CompletedPercentage,child3WorkPercentage,childGoal3.getStartDate(),childGoal3.getTimeTaken(),
                                            childGoal3.getActive(),childGoal3.getHidden(),childGoal3.getCompleted());
                                    if (childGoals4.isPresent()){
                                        List<GoalResponse> childGoalResponses4 = new ArrayList<>();
                                        for(Goal childGoal4 : childGoals4.get()){
                                            Optional<GoalType>  childGoalType4 = goalTypeRepository.findById(childGoal3.getGoalTypeId());
                                            Float child4CompletedPercentage = ruleEngineService.getCompletedPercentage(userId,childGoal4.getId());
                                            goalRepository.modifyCompletedPercentage(childGoal4.getId(),child4CompletedPercentage);
                                            Float child4WorkPercentage = ruleEngineService.getWorkPercentage(userId,childGoal4.getId());
                                            goalRepository.modifyWorkPercentage(childGoal4.getId(),child4WorkPercentage);
                                            Optional<List<Goal>> childGoals5 =  goalRepository.findGoalsByUserIdAndActiveAndParentGoalId(userId,active,childGoal4.getId());
                                            GoalResponse childGoalResponse4 = new GoalResponse(childGoal4.getId(), childGoal4.getCreatedAt(),
                                                    childGoal4.getUpdatedAt(), childGoal4.getName(), childGoal4.getDueDate(), childGoalType4.get().getName(),
                                                    childGoal4.getDescription(),child4CompletedPercentage,child4WorkPercentage,childGoal4.getStartDate(),childGoal4.getTimeTaken(),
                                                    childGoal4.getActive(),childGoal4.getHidden(),childGoal4.getCompleted());
                                            childGoalResponses4.add(childGoalResponse4);
                                        }
                                        childGoalResponse3.setGoalResponses(childGoalResponses4);
                                    }
                                    childGoalResponses3.add(childGoalResponse3);
                                }
                                childGoalResponse2.setGoalResponses(childGoalResponses3);
                            }
                            childGoalResponses2.add(childGoalResponse2);
                        }
                        childGoalResponse1.setGoalResponses(childGoalResponses2);
                    }
                    childGoalResponses1.add(childGoalResponse1);
                }
                goalResponse.setGoalResponses(childGoalResponses1);
            }
            goalResponses.add(goalResponse);
        }
        return goalResponses;
    };

    @Override
    public Goal createRootGoal(Long userId, String name, String goalTypeName, Date dueDate, Boolean active){
        GoalType goalType = goalTypeRepository.findByNameAndUserId(goalTypeName,userId);
        Goal goal = goalRepository.save(new Goal(name,dueDate,goalType.getId(),active,userId,false));
        return goal;
    }
    @Override
    public Goal createChildGoal(Long userId, String name, String goalTypeName, Date dueDate,String parentName, Boolean active){
        GoalType goalType = goalTypeRepository.findByNameAndUserId(goalTypeName,userId);
        Goal goal = goalRepository.findByUserIdAndName(userId,parentName);
        Goal newGoal = goalRepository.save(new Goal(name,dueDate,goalType.getId(),active,userId,false,goal.getId()));
        return newGoal;
    }

}
