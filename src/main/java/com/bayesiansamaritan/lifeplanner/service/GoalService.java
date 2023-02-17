package com.bayesiansamaritan.lifeplanner.service;

import com.bayesiansamaritan.lifeplanner.model.Goal.Goal;
import com.bayesiansamaritan.lifeplanner.response.GoalResponse;

import java.util.List;

public interface GoalService {

    public List<GoalResponse> getAllGoals(Long userId, String goalTypeName);
    public Goal createRootGoal(Long userId, String name, String goalTypeName, Long timeTaken);
    public Goal createChildGoal(Long userId, String name, String goalTypeName, Long timeTaken,String parentName);
}
