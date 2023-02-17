package com.bayesiansamaritan.lifeplanner.service;

import com.bayesiansamaritan.lifeplanner.model.Task.Task;

import com.bayesiansamaritan.lifeplanner.enums.DayOfWeek;
import com.bayesiansamaritan.lifeplanner.response.TaskResponse;

import java.util.Date;
import java.util.List;

public interface TaskService {

    public List<TaskResponse> getAllActiveTasks(Long userId, Boolean active, String taskTypeName);

    public Task createOneTimeRootTask(Long userId, String name, Date startDate, Long timeTaken, Date dueDate, Long every, String scheduleType,
                                  String taskTypeName);
    public Task createDailyRootTask(Long userId, String name, Date startDate, Long timeTaken, Date dueDate, Long every, String scheduleType,
                           String taskTypeName);

    public Task createWeeklyRootTask(Long userId, String name, Date startDate, Long timeTaken, Date dueDate, Long every, String scheduleType,
                                String taskTypeName, List<DayOfWeek> daysOfWeek);

    public Task createMonthlyRootTask(Long userId, String name, Date startDate, Long timeTaken, Date dueDate, Long every, String scheduleType,
                                  String taskTypeName);

    public Task createYearlyRootTask(Long userId, String name, Date startDate, Long timeTaken, Date dueDate, Long every, String scheduleType,
                                 String taskTypeName);

    public Task createOneTimeChildTask(Long userId, String name, Date startDate, Long timeTaken, Date dueDate, Long every, String scheduleType,
                                  String taskTypeName, String parentName);
    public Task createDailyChildTask(Long userId, String name, Date startDate, Long timeTaken, Date dueDate, Long every, String scheduleType,
                                String taskTypeName, String parentName);

    public Task createWeeklyChildTask(Long userId, String name, Date startDate, Long timeTaken, Date dueDate, Long every, String scheduleType,
                                 String taskTypeName, List<DayOfWeek> daysOfWeek, String parentName);

    public Task createMonthlyChildTask(Long userId, String name, Date startDate, Long timeTaken, Date dueDate, Long every, String scheduleType,
                                  String taskTypeName, String parentName);

    public Task createYearlyChildTask(Long userId, String name, Date startDate, Long timeTaken, Date dueDate, Long every, String scheduleType,
                                 String taskTypeName, String parentName);

    public Task completeTask(Long userId, Long id);

}
