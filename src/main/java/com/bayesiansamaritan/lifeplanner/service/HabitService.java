package com.bayesiansamaritan.lifeplanner.service;

import com.bayesiansamaritan.lifeplanner.model.Habit.Habit;

import com.bayesiansamaritan.lifeplanner.enums.DayOfWeek;
import com.bayesiansamaritan.lifeplanner.response.HabitResponse;

import java.util.Date;
import java.util.List;

public interface HabitService {

    public List<HabitResponse> getAllHabits(Long userId, Boolean active, String habitTypeName, Date currentDate);

    public Habit createDailyHabit(Long userId, String name,Date startDate,  Long timeOfDay, Date dueDate, Long every, String scheduleType,
                           String habitTypeName, Boolean active);

    public Habit createWeeklyHabit(Long userId, String name,Date startDate,  Long timeOfDay, Date dueDate,Long every, String scheduleType,
                                  String habitTypeName, List<DayOfWeek> daysOfWeek, Boolean active);

    public Habit createMonthlyHabit(Long userId, String name,Date startDate,  Long timeOfDay, Date dueDate,Long every, String scheduleType,
                                  String habitTypeName, Boolean active);

    public Habit createYearlyHabit(Long userId, String name,Date startDate,  Long timeOfDay, Date dueDate,Long every, String scheduleType,
                                  String habitTypeName, Boolean active);

    public Habit completeHabit(Long userId, Long id, String habitCompletionType, Date currentDate);

    public void modifySchedule(Long userId, Long id,String oldScheduleType, String scheduleType,Long every, List<DayOfWeek> daysOfWeek);

}
