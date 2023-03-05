package com.bayesiansamaritan.lifeplanner.service;

import com.bayesiansamaritan.lifeplanner.model.Habit.Habit;
import com.bayesiansamaritan.lifeplanner.model.Task.Task;

import com.bayesiansamaritan.lifeplanner.enums.DayOfWeek;
import com.bayesiansamaritan.lifeplanner.response.TaskResponse;

import java.util.Date;
import java.util.List;

public interface TaskService {

    public List<TaskResponse> getAllTasks(Long userId, Boolean active, String taskTypeName);
    public List<TaskResponse> getAllTasksAndSubTasks(Long userId, Boolean active, String taskTypeName);

    public Task createOneTimeRootTask(Long userId, String name, Date startDate, Long timeTaken, Date dueDate, Long every, String scheduleType,
                                  String taskTypeName, Boolean active);
    public Task createDailyRootTask(Long userId, String name, Date startDate, Long timeTaken, Date dueDate, Long every, String scheduleType,
                           String taskTypeName, Boolean active);

    public Task createWeeklyRootTask(Long userId, String name, Date startDate, Long timeTaken, Date dueDate, Long every, String scheduleType,
                                String taskTypeName, List<DayOfWeek> daysOfWeek, Boolean active);

    public Task createMonthlyRootTask(Long userId, String name, Date startDate, Long timeTaken, Date dueDate, Long every, String scheduleType,
                                  String taskTypeName, Boolean active);

    public Task createYearlyRootTask(Long userId, String name, Date startDate, Long timeTaken, Date dueDate, Long every, String scheduleType,
                                 String taskTypeName, Boolean active);

    public Task createOneTimeChildTask(Long userId, String name, Date startDate, Long timeTaken, Date dueDate, Long every, String scheduleType,
                                  String taskTypeName, String parentName, Boolean active);
    public Task createDailyChildTask(Long userId, String name, Date startDate, Long timeTaken, Date dueDate, Long every, String scheduleType,
                                String taskTypeName, String parentName, Boolean active);

    public Task createWeeklyChildTask(Long userId, String name, Date startDate, Long timeTaken, Date dueDate, Long every, String scheduleType,
                                 String taskTypeName, List<DayOfWeek> daysOfWeek, String parentName, Boolean active);

    public Task createMonthlyChildTask(Long userId, String name, Date startDate, Long timeTaken, Date dueDate, Long every, String scheduleType,
                                  String taskTypeName, String parentName, Boolean active);

    public Task createYearlyChildTask(Long userId, String name, Date startDate, Long timeTaken, Date dueDate, Long every, String scheduleType,
                                 String taskTypeName, String parentName, Boolean active);

    public Task completeTask(Long userId, Long id);
    public void modifySchedule(Long userId, Long id, String scheduleType, Long every, List<DayOfWeek> daysOfWeek);

}
