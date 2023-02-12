package com.bayesiansamaritan.lifeplanner.service;

import com.bayesiansamaritan.lifeplanner.enums.DayOfWeek;
import com.bayesiansamaritan.lifeplanner.model.BadHabit;
import com.bayesiansamaritan.lifeplanner.model.Habit;
import com.bayesiansamaritan.lifeplanner.response.BadHabitResponse;
import com.bayesiansamaritan.lifeplanner.response.HabitResponse;

import java.util.Date;
import java.util.List;

public interface BadHabitService {

    public List<BadHabitResponse> getAllActiveBadHabits(Long userId, Boolean active, String habitTypeName);

    public BadHabit createBadHabit(Long userId, String name,Date startDate, String habitTypeName);

    public BadHabit carriedOutBadHabit(Long userId, Long id);

}
