package com.bayesiansamaritan.lifeplanner.service.impl;

import com.bayesiansamaritan.lifeplanner.model.*;
import com.bayesiansamaritan.lifeplanner.repository.*;
import com.bayesiansamaritan.lifeplanner.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bayesiansamaritan.lifeplanner.enums.DayOfWeek;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    TaskRepository taskRepository;
    @Autowired
    TaskTypeRepository taskTypeRepository;
    @Autowired
    private DailyRepository dailyRepository;

    @Autowired
    private WeeklyRepository weeklyRepository;

    @Autowired
    private MonthlyRepository monthlyRepository;

    @Autowired
    private YearlyRepository yearlyRepository;


    @Override
    public List<Task> getAllActiveTasks(Long userId, Boolean active, String taskTypeName){

        TaskType taskType = taskTypeRepository.findByName(taskTypeName);
        List<Task> tasks = taskRepository.findByUserIdAndActiveAndTaskTypeId(userId,active,taskType.getId());
        return tasks;
    };

    @Override
    public Task createOneTimeTask(Long userId, String name, Date startDate, Long timeTaken, Date dueDate, Long every, String scheduleType,
                                String taskTypeName){
        TaskType taskType = taskTypeRepository.findByName(taskTypeName);
        Boolean active = true;
        Task task = taskRepository.save(new Task(name, timeTaken, startDate, dueDate, taskType.getId(), active, userId, scheduleType));
        return task;
    };

    @Override
    public Task createDailyTask(Long userId, String name, Date startDate, Long timeTaken, Date dueDate, Long every, String scheduleType,
                            String taskTypeName){
        TaskType taskType = taskTypeRepository.findByName(taskTypeName);
        Boolean active = true;
        Task task = taskRepository.save(new Task(name, timeTaken, startDate, dueDate, taskType.getId(), active, userId, scheduleType));
        Daily daily = dailyRepository.save(new Daily(every,"task/"+task.getId()));
        return task;
    };

    @Override
    public Task createWeeklyTask(Long userId, String name, Date startDate, Long timeTaken, Date dueDate, Long every, String scheduleType,
                                String taskTypeName, List<DayOfWeek> daysOfWeek){
        TaskType taskType = taskTypeRepository.findByName(taskTypeName);
        Boolean active = true;
        Task task = taskRepository.save(new Task(name, timeTaken, startDate, dueDate, taskType.getId(), active, userId, scheduleType));
        Weekly weekly = weeklyRepository.save(new Weekly(every,"task/"+task.getId(),daysOfWeek));
        return task;
    };

    @Override
    public Task createMonthlyTask(Long userId, String name, Date startDate, Long timeTaken, Date dueDate, Long every, String scheduleType,
                                String taskTypeName){
        TaskType taskType = taskTypeRepository.findByName(taskTypeName);
        Boolean active = true;
        Task task = taskRepository.save(new Task(name, timeTaken, startDate, dueDate, taskType.getId(), active, userId, scheduleType));
        Monthly monthly = monthlyRepository.save(new Monthly(every,"task/"+task.getId()));
        return task;
    };

    @Override
    public Task createYearlyTask(Long userId, String name, Date startDate, Long timeTaken, Date dueDate, Long every, String scheduleType,
                                String taskTypeName){
        TaskType taskType = taskTypeRepository.findByName(taskTypeName);
        Boolean active = true;
        Task task = taskRepository.save(new Task(name, timeTaken, startDate, dueDate, taskType.getId(), active, userId, scheduleType));
        Yearly yearly = yearlyRepository.save(new Yearly(every,"task/"+task.getId()));
        return task;
    };

    @Override
    public Task completeTask(Long userId, Long id){
        Task task = taskRepository.findByUserIdAndId(userId,id);
        String scheduleTypeName = task.getScheduleType();
        Date dueDate = task.getDueDate();
        taskRepository.completeTask(task.getId(), false);
        Boolean active = true;
        if(scheduleTypeName.equals("daily")){
            String referenceId = "task/"+task.getId();
            Daily daily = dailyRepository.findByReferenceId(referenceId);
            Date newDueDate = new Date(dueDate.getTime() + (1000*60*60*24*daily.getEvery()));
            Task newTask = taskRepository.save(new Task(task.getName(),task.getTimeTaken(), task.getStartDate(), newDueDate, task.getTaskTypeId(),
                    active, userId, task.getScheduleType()));
            dailyRepository.save(new Daily(daily.getEvery(),"task/"+newTask.getId()));
            return newTask;
        }
        else if(scheduleTypeName.equals("weekly")){
            String referenceId = "task/"+task.getId();
            Weekly weekly = weeklyRepository.findByReferenceId(referenceId);
            List<DayOfWeek> daysOfWeek = weekly.getDaysOfWeek();
            LocalDate localDueDate = LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(dueDate));
            Boolean match = false;
            Long current = 1L;
            taskRepository.completeTask(task.getId(), false);
            while( !match){
                DayOfWeek dayOfWeek = DayOfWeek.valueOf(localDueDate.getDayOfWeek().toString());
                if (dayOfWeek.equals(dayOfWeek.SUNDAY)){
                    localDueDate = localDueDate.plusDays(7L* (weekly.getEvery()-1));
                }
                localDueDate = localDueDate.plusDays(1L);
                if (daysOfWeek.contains(DayOfWeek.valueOf(localDueDate.getDayOfWeek().toString()))){
                    match = true;
                }
            }
            Date newDueDate = Date.from(localDueDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            Task newTask = taskRepository.save(new Task(task.getName(),task.getTimeTaken(), task.getStartDate(), newDueDate, task.getTaskTypeId(),
                    active, userId, task.getScheduleType()));
            weeklyRepository.modifyReferenceId(weekly.getId(),"task/"+newTask.getId());
            return newTask;
        }
        else if(scheduleTypeName.equals("monthly")){
            LocalDate localDueDate = LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(dueDate));
            String referenceId = "task/"+task.getId();
            Monthly monthly = monthlyRepository.findByReferenceId(referenceId);
            LocalDate newLocalDueDate = localDueDate.plusMonths(monthly.getEvery());
            Date newDueDate = Date.from(newLocalDueDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            Task newTask = taskRepository.save(new Task(task.getName(),task.getTimeTaken(), task.getStartDate(), newDueDate, task.getTaskTypeId(),
                    active, userId, task.getScheduleType()));
            monthlyRepository.save(new Monthly(monthly.getEvery(),"task/"+newTask.getId()));
            return newTask;
        }
        else if(scheduleTypeName.equals("yearly")){
            LocalDate localDueDate = LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(dueDate));
            String referenceId = "task/"+task.getId();
            Yearly yearly = yearlyRepository.findByReferenceId(referenceId);
            LocalDate newLocalDueDate = localDueDate.plusYears(1L*yearly.getEvery());
            Date newDueDate = Date.from(newLocalDueDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            Task newTask = taskRepository.save(new Task(task.getName(),task.getTimeTaken(), task.getStartDate(), newDueDate, task.getTaskTypeId(),
                    active, userId, task.getScheduleType()));
            yearlyRepository.save(new Yearly(yearly.getEvery(),"task/"+newTask.getId()));
            return newTask;
        }
        return task;
    };
}
