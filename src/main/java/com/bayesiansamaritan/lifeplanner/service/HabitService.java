package com.bayesiansamaritan.lifeplanner.service;

import com.bayesiansamaritan.lifeplanner.model.Habit.Habit;

import com.bayesiansamaritan.lifeplanner.enums.DayOfWeek;
import com.bayesiansamaritan.lifeplanner.response.HabitResponse;

import java.util.Date;
import java.util.List;

public interface HabitService {

    public List<HabitResponse> getAllHabits(Long userId, Boolean active, String habitTypeName);

    public List<HabitResponse> getAllHabitsAndSubHabits(Long userId, Boolean active, String habitTypeName);
    public Habit createDailyRootHabit(Long userId, String name,Date startDate,  Long timeOfDay, Date dueDate, Long every, String scheduleType,
                           String habitTypeName, Boolean active);

    public Habit createWeeklyRootHabit(Long userId, String name,Date startDate,  Long timeOfDay, Date dueDate,Long every, String scheduleType,
                                  String habitTypeName, List<DayOfWeek> daysOfWeek, Boolean active);

    public Habit createMonthlyRootHabit(Long userId, String name,Date startDate,  Long timeOfDay, Date dueDate,Long every, String scheduleType,
                                  String habitTypeName, Boolean active);

    public Habit createYearlyRootHabit(Long userId, String name,Date startDate,  Long timeOfDay, Date dueDate,Long every, String scheduleType,
                                  String habitTypeName, Boolean active);

    public Habit createDailyChildHabit(Long userId, String name,Date startDate,  Long timeOfDay, Date dueDate, Long every, String scheduleType,
                                  String habitTypeName, String parentName, Boolean active);

    public Habit createWeeklyChildHabit(Long userId, String name,Date startDate,  Long timeOfDay, Date dueDate,Long every, String scheduleType,
                                   String habitTypeName, List<DayOfWeek> daysOfWeek, String parentName, Boolean active);

    public Habit createMonthlyChildHabit(Long userId, String name,Date startDate,  Long timeOfDay, Date dueDate,Long every, String scheduleType,
                                    String habitTypeName, String parentName, Boolean active);

    public Habit createYearlyChildHabit(Long userId, String name,Date startDate,  Long timeOfDay, Date dueDate,Long every, String scheduleType,
                                   String habitTypeName, String parentName, Boolean active);
    public Habit completeHabit(Long userId, Long id);

    public void modifySchedule(Long userId, Long id,String oldScheduleType, String scheduleType,Long every, List<DayOfWeek> daysOfWeek);

}
