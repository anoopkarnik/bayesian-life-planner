package com.bayesiansamaritan.lifeplanner.service;

import com.bayesiansamaritan.lifeplanner.model.Habit;
import com.bayesiansamaritan.lifeplanner.model.Task;

import com.bayesiansamaritan.lifeplanner.enums.DayOfWeek;
import com.bayesiansamaritan.lifeplanner.response.HabitResponse;

import java.util.Date;
import java.util.List;

public interface HabitService {

    public List<HabitResponse> getAllActiveHabits(Long userId, Boolean active, String habitTypeName);
    public Habit createDailyHabit(Long userId, String name,Date startDate,  Long timeTaken, Date dueDate, Long every, String scheduleType,
                           String habitTypeName);

    public Habit createWeeklyHabit(Long userId, String name,Date startDate,  Long timeTaken, Date dueDate,Long every, String scheduleType,
                                  String habitTypeName, List<DayOfWeek> daysOfWeek);

    public Habit createMonthlyHabit(Long userId, String name,Date startDate,  Long timeTaken, Date dueDate,Long every, String scheduleType,
                                  String habitTypeName);

    public Habit createYearlyHabit(Long userId, String name,Date startDate,  Long timeTaken, Date dueDate,Long every, String scheduleType,
                                  String habitTypeName);

    public Habit completeHabit(Long userId, Long id);

}
