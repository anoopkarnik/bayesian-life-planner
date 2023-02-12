package com.bayesiansamaritan.lifeplanner.service;

import com.bayesiansamaritan.lifeplanner.model.SubTask;
import com.bayesiansamaritan.lifeplanner.model.Task;

import com.bayesiansamaritan.lifeplanner.enums.DayOfWeek;
import com.bayesiansamaritan.lifeplanner.response.SubTaskResponse;

import java.util.Date;
import java.util.List;

public interface SubTaskService {

    public List<SubTaskResponse> getAllActiveSubTasks(Long userId, Boolean active, Long taskId);

    public SubTask createOneTimeSubTask(Long userId, String name, Date startDate, Long timeTaken, Date dueDate, Long every, String scheduleType,
                                      Long taskId);
    public SubTask createDailySubTask(Long userId, String name, Date startDate, Long timeTaken, Date dueDate, Long every, String scheduleType,
                                Long taskId);

    public SubTask createWeeklySubTask(Long userId, String name, Date startDate, Long timeTaken, Date dueDate, Long every, String scheduleType,
                                 Long taskId, List<DayOfWeek> daysOfWeek);

    public SubTask createMonthlySubTask(Long userId, String name, Date startDate, Long timeTaken, Date dueDate, Long every, String scheduleType,
                                  Long taskId);

    public SubTask createYearlySubTask(Long userId, String name, Date startDate, Long timeTaken, Date dueDate, Long every, String scheduleType,
                                 Long taskId);

    public SubTask completeSubTask(Long userId, Long id);

}
