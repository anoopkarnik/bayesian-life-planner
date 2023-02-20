package com.bayesiansamaritan.lifeplanner.service;

import com.bayesiansamaritan.lifeplanner.model.Goal.Goal;
import com.bayesiansamaritan.lifeplanner.response.GoalResponse;

import java.util.Date;
import java.util.List;

public interface GoalService {

    public List<GoalResponse> getAllGoals(Long userId, String goalTypeName);
    public Goal createRootGoal(Long userId, String name, String goalTypeName, Date dueDate);
    public Goal createChildGoal(Long userId, String name, String goalTypeName, Date dueDate ,String parentName);
}
