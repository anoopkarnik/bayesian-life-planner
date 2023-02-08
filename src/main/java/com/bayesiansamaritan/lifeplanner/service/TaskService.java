package com.bayesiansamaritan.lifeplanner.service;

import com.bayesiansamaritan.lifeplanner.model.Task;

import com.bayesiansamaritan.lifeplanner.enums.DayOfWeek;
import java.util.Date;
import java.util.List;

public interface TaskService {

    public List<Task> getAllActiveTasks(Long userId, Boolean active, String taskTypeName);

    public Task createOneTimeTask(Long userId, String name, Date startDate, Long timeTaken, Date dueDate, Long every, String scheduleType,
                                  String taskTypeName);
    public Task createDailyTask(Long userId, String name, Date startDate, Long timeTaken, Date dueDate, Long every, String scheduleType,
                           String taskTypeName);

    public Task createWeeklyTask(Long userId, String name, Date startDate, Long timeTaken, Date dueDate, Long every, String scheduleType,
                                String taskTypeName, List<DayOfWeek> daysOfWeek);

    public Task createMonthlyTask(Long userId, String name, Date startDate, Long timeTaken, Date dueDate, Long every, String scheduleType,
                                  String taskTypeName);

    public Task createYearlyTask(Long userId, String name, Date startDate, Long timeTaken, Date dueDate, Long every, String scheduleType,
                                 String taskTypeName);

    public Task completeTask(Long userId, Long id);

}
