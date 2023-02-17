package com.bayesiansamaritan.lifeplanner.service.impl;

import com.bayesiansamaritan.lifeplanner.model.Goal.Goal;
import com.bayesiansamaritan.lifeplanner.model.Goal.GoalType;
import com.bayesiansamaritan.lifeplanner.repository.Goal.GoalRepository;
import com.bayesiansamaritan.lifeplanner.repository.Goal.GoalTypeRepository;
import com.bayesiansamaritan.lifeplanner.response.GoalResponse;
import com.bayesiansamaritan.lifeplanner.service.GoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GoalServiceImpl implements GoalService {

    @Autowired
    GoalRepository goalRepository;

    @Autowired
    GoalTypeRepository goalTypeRepository;

    @Override
    public List<GoalResponse> getAllGoals(Long userId, String goalTypeName){

        GoalType goalType = goalTypeRepository.findByNameAndUserId(goalTypeName,userId);
        List<Goal> goals = goalRepository.findRootGoalsByUserIdAndActiveAndGoalTypeId(userId,true,goalType.getId());
        List<GoalResponse> goalResponses = new ArrayList<>();
        for (Goal goal: goals){
            Optional<List<Goal>> childGoals1 =  goalRepository.findGoalsByUserIdAndActiveAndParentGoalId(userId,true,goal.getId());
            GoalResponse goalResponse = new GoalResponse(goal.getId(),goal.getCreatedAt(),
                    goal.getUpdatedAt(),goal.getName(),goal.getTimeTaken(),goalType.getName(),
                    goal.getCompleted(),goal.getDescription());
            if (childGoals1.isPresent()){
                List<GoalResponse> childGoalResponses1 = new ArrayList<>();
                for(Goal childGoal1 : childGoals1.get()) {
                    Optional<GoalType> childGoalType1 = goalTypeRepository.findById(childGoal1.getGoalTypeId());
                    Optional<List<Goal>> childGoals2 =  goalRepository.findGoalsByUserIdAndActiveAndParentGoalId(userId,true,childGoal1.getId());
                    GoalResponse childGoalResponse1 = new GoalResponse(childGoal1.getId(), childGoal1.getCreatedAt(),
                            childGoal1.getUpdatedAt(), childGoal1.getName(), childGoal1.getTimeTaken(), childGoalType1.get().getName(),
                            childGoal1.getCompleted(), childGoal1.getDescription());
                    if (childGoals2.isPresent()){
                        List<GoalResponse> childGoalResponses2 = new ArrayList<>();
                        for(Goal childGoal2 : childGoals2.get()){
                            Optional<GoalType>  childGoalType2 = goalTypeRepository.findById(childGoal2.getGoalTypeId());
                            Optional<List<Goal>> childGoals3 =  goalRepository.findGoalsByUserIdAndActiveAndParentGoalId(userId,true,childGoal2.getId());
                            GoalResponse childGoalResponse2 = new GoalResponse(childGoal2.getId(), childGoal2.getCreatedAt(),
                                    childGoal2.getUpdatedAt(), childGoal2.getName(), childGoal2.getTimeTaken(), childGoalType2.get().getName(),
                                    childGoal2.getCompleted(), childGoal2.getDescription());
                            if (childGoals3.isPresent()){
                                List<GoalResponse> childGoalResponses3 = new ArrayList<>();
                                for(Goal childGoal3 : childGoals3.get()){
                                    Optional<GoalType>  childGoalType3 = goalTypeRepository.findById(childGoal2.getGoalTypeId());
                                    Optional<List<Goal>> childGoals4 =  goalRepository.findGoalsByUserIdAndActiveAndParentGoalId(userId,true,childGoal3.getId());
                                    GoalResponse childGoalResponse3 = new GoalResponse(childGoal3.getId(), childGoal3.getCreatedAt(),
                                            childGoal3.getUpdatedAt(), childGoal3.getName(), childGoal3.getTimeTaken(), childGoalType3.get().getName(),
                                            childGoal3.getCompleted(), childGoal3.getDescription());
                                    if (childGoals4.isPresent()){
                                        List<GoalResponse> childGoalResponses4 = new ArrayList<>();
                                        for(Goal childGoal4 : childGoals4.get()){
                                            Optional<GoalType>  childGoalType4 = goalTypeRepository.findById(childGoal3.getGoalTypeId());
                                            Optional<List<Goal>> childGoals5 =  goalRepository.findGoalsByUserIdAndActiveAndParentGoalId(userId,true,childGoal4.getId());
                                            GoalResponse childGoalResponse4 = new GoalResponse(childGoal4.getId(), childGoal4.getCreatedAt(),
                                                    childGoal4.getUpdatedAt(), childGoal4.getName(), childGoal4.getTimeTaken(), childGoalType4.get().getName(),
                                                    childGoal4.getCompleted(), childGoal4.getDescription());
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
    public Goal createRootGoal(Long userId, String name, String goalTypeName, Long timeTaken){
        GoalType goalType = goalTypeRepository.findByNameAndUserId(goalTypeName,userId);
        Goal goal = goalRepository.save(new Goal(name,timeTaken,goalType.getId(),true,userId,false));
        return goal;
    }
    @Override
    public Goal createChildGoal(Long userId, String name, String goalTypeName, Long timeTaken,String parentName){
        GoalType goalType = goalTypeRepository.findByNameAndUserId(goalTypeName,userId);
        Goal goal = goalRepository.findByUserIdAndName(userId,parentName);
        Goal newGoal = goalRepository.save(new Goal(name,timeTaken,goalType.getId(),true,userId,false,goal.getId()));
        return newGoal;
    }

}
