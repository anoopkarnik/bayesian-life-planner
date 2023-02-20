package com.bayesiansamaritan.lifeplanner.service;

import com.bayesiansamaritan.lifeplanner.model.Habit.Habit;

import com.bayesiansamaritan.lifeplanner.enums.DayOfWeek;
import com.bayesiansamaritan.lifeplanner.response.HabitResponse;

import java.util.Date;
import java.util.List;

public interface HabitService {

    public List<HabitResponse> getAllActiveHabits(Long userId, Boolean active, String habitTypeName);

    public List<HabitResponse> getAllActiveHabitsAndSubHabits(Long userId, Boolean active, String habitTypeName);
    public Habit createDailyRootHabit(Long userId, String name,Date startDate,  Long timeTaken, Date dueDate, Long every, String scheduleType,
                           String habitTypeName);

    public Habit createWeeklyRootHabit(Long userId, String name,Date startDate,  Long timeTaken, Date dueDate,Long every, String scheduleType,
                                  String habitTypeName, List<DayOfWeek> daysOfWeek);

    public Habit createMonthlyRootHabit(Long userId, String name,Date startDate,  Long timeTaken, Date dueDate,Long every, String scheduleType,
                                  String habitTypeName);

    public Habit createYearlyRootHabit(Long userId, String name,Date startDate,  Long timeTaken, Date dueDate,Long every, String scheduleType,
                                  String habitTypeName);

    public Habit createDailyChildHabit(Long userId, String name,Date startDate,  Long timeTaken, Date dueDate, Long every, String scheduleType,
                                  String habitTypeName, String parentName);

    public Habit createWeeklyChildHabit(Long userId, String name,Date startDate,  Long timeTaken, Date dueDate,Long every, String scheduleType,
                                   String habitTypeName, List<DayOfWeek> daysOfWeek, String parentName);

    public Habit createMonthlyChildHabit(Long userId, String name,Date startDate,  Long timeTaken, Date dueDate,Long every, String scheduleType,
                                    String habitTypeName, String parentName);

    public Habit createYearlyChildHabit(Long userId, String name,Date startDate,  Long timeTaken, Date dueDate,Long every, String scheduleType,
                                   String habitTypeName, String parentName);
    public Habit completeHabit(Long userId, Long id);

}
