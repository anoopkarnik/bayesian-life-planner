package com.bayesiansamaritan.lifeplanner.service;

import com.bayesiansamaritan.lifeplanner.model.Goal.Goal;
import com.bayesiansamaritan.lifeplanner.response.GoalResponse;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

public interface GoalService {

    public List<GoalResponse> getAllGoals(Long userId,Boolean active, String goalTypeName) throws ParseException;
    public Goal createRootGoal(Long userId, String name, String goalTypeName, Date dueDate, Boolean active);
    public Goal createChildGoal(Long userId, String name, String goalTypeName, Date dueDate ,String parentName, Boolean active);
}
