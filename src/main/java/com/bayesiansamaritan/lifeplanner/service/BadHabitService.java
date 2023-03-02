package com.bayesiansamaritan.lifeplanner.service;

import com.bayesiansamaritan.lifeplanner.model.BadHabit.BadHabit;
import com.bayesiansamaritan.lifeplanner.response.BadHabitResponse;

import java.util.Date;
import java.util.List;

public interface BadHabitService {

    public List<BadHabitResponse> getAllBadHabits(Long userId, Boolean active, String habitTypeName);
    public List<BadHabitResponse> getAllBadHabitsAndSubBadHabits(Long userId, Boolean active, String habitTypeName);

    public BadHabit createRootBadHabit(Long userId, String name,Date startDate, String habitTypeName,Boolean active);
    public BadHabit createChildBadHabit(Long userId, String name,Date startDate, String habitTypeName,String parentName, Boolean active);

    public BadHabit carriedOutBadHabit(Long userId, Long id);

}
