package com.bayesiansamaritan.lifeplanner.service.impl;

import com.bayesiansamaritan.lifeplanner.model.*;
import com.bayesiansamaritan.lifeplanner.repository.*;
import com.bayesiansamaritan.lifeplanner.service.SubTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bayesiansamaritan.lifeplanner.enums.DayOfWeek;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class SubTaskServiceImpl implements SubTaskService {

    @Autowired
    SubTaskRepository subTaskRepository;

    @Autowired
    private DailyRepository dailyRepository;

    @Autowired
    private WeeklyRepository weeklyRepository;

    @Autowired
    private MonthlyRepository monthlyRepository;

    @Autowired
    private YearlyRepository yearlyRepository;
    

    @Override
    public List<SubTask> getAllActiveSubTasks(Long userId, Boolean active, Long taskId){

        List<SubTask> subTasks = subTaskRepository.findByUserIdAndActiveAndTaskId(userId,true,taskId);
        return subTasks;
    };

    @Override
    public SubTask createOneTimeSubTask(Long userId, String name, Date startDate, Long timeTaken, Date dueDate, Long every, String scheduleType,
                                      Long taskId){
        Boolean active = true;
        SubTask task = subTaskRepository.save(new SubTask(name, timeTaken, startDate, dueDate, taskId, active, userId, scheduleType));
        return task;
    };

    @Override
    public SubTask createDailySubTask(Long userId, String name, Date startDate, Long timeTaken, Date dueDate, Long every, String scheduleType,
                                      Long taskId){
        Boolean active = true;
        SubTask task = subTaskRepository.save(new SubTask(name, timeTaken, startDate, dueDate, taskId, active, userId, scheduleType));
        Daily daily = dailyRepository.save(new Daily(every,"task/"+task.getId()));
        return task;
    };

    @Override
    public SubTask createWeeklySubTask(Long userId, String name, Date startDate, Long timeTaken, Date dueDate, Long every, String scheduleType,
                                       Long taskId, List<DayOfWeek> daysOfWeek){
        Boolean active = true;
        SubTask task = subTaskRepository.save(new SubTask(name, timeTaken, startDate, dueDate, taskId, active, userId, scheduleType));
        Weekly weekly = weeklyRepository.save(new Weekly(every,"task/"+task.getId(),daysOfWeek));
        return task;
    };

    @Override
    public SubTask createMonthlySubTask(Long userId, String name, Date startDate, Long timeTaken, Date dueDate, Long every, String scheduleType,
                                        Long taskId){
        Boolean active = true;
        SubTask task = subTaskRepository.save(new SubTask(name, timeTaken, startDate, dueDate, taskId, active, userId, scheduleType));
        Monthly monthly = monthlyRepository.save(new Monthly(every,"task/"+task.getId()));
        return task;
    };

    @Override
    public SubTask createYearlySubTask(Long userId, String name, Date startDate, Long timeTaken, Date dueDate, Long every, String scheduleType,
                                       Long taskId){
        Boolean active = true;
        SubTask task = subTaskRepository.save(new SubTask(name, timeTaken, startDate, dueDate, taskId, active, userId, scheduleType));
        Yearly yearly = yearlyRepository.save(new Yearly(every,"task/"+task.getId()));
        return task;
    };

    @Override
    public SubTask completeSubTask(Long userId, Long id){
        SubTask subTask = subTaskRepository.findByUserIdAndId(userId,id);
        String scheduleTypeName = subTask.getScheduleType();
        Date dueDate = subTask.getDueDate();
        subTaskRepository.completeSubTask(subTask.getId(), false);
        Boolean active = true;
        if(scheduleTypeName.equals("daily")){
            String referenceId = "subTask/"+subTask.getId();
            Daily daily = dailyRepository.findByReferenceId(referenceId);
            Date newDueDate = new Date(dueDate.getTime() + (1000*60*60*24*daily.getEvery()));
            SubTask newSubTask = subTaskRepository.save(new SubTask(subTask.getName(),subTask.getTimeTaken(), subTask.getStartDate(), newDueDate,
                    subTask.getTaskId(),
                    active, userId, subTask.getScheduleType()));
            dailyRepository.save(new Daily(daily.getEvery(),"task/"+newSubTask.getId()));
            return newSubTask;
        }
        else if(scheduleTypeName.equals("weekly")){
            String referenceId = "subTask/"+subTask.getId();
            Weekly weekly = weeklyRepository.findByReferenceId(referenceId);
            List<DayOfWeek> daysOfWeek = weekly.getDaysOfWeek();
            LocalDate localDueDate = LocalDate.of(dueDate.getYear(),dueDate.getMonth(),dueDate.getDate());
            Boolean match = false;
            Long current = 1L;
            while( !match){
                DayOfWeek dayOfWeek = DayOfWeek.valueOf(localDueDate.getDayOfWeek().toString());
                if (dayOfWeek.equals(dayOfWeek.SUNDAY)){
                    localDueDate = localDueDate.plusDays(7L* weekly.getEvery());
                }
                localDueDate = localDueDate.plusDays(1L);
                if (daysOfWeek.contains(localDueDate.getDayOfWeek())){
                    match = true;
                }
            }
            Date newDueDate = Date.from(localDueDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            SubTask newSubTask = subTaskRepository.save(new SubTask(subTask.getName(),subTask.getTimeTaken(), subTask.getStartDate(), newDueDate, subTask.getTaskId(),
                    active, userId, subTask.getScheduleType()));
            weeklyRepository.modifyReferenceId(weekly.getId(),"task/"+newSubTask.getId());
            return newSubTask;
        }
        else if(scheduleTypeName.equals("monthly")){
            LocalDate localDueDate = LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(dueDate));
            String referenceId = "subTask/"+subTask.getId();
            Monthly monthly = monthlyRepository.findByReferenceId(referenceId);
            LocalDate newLocalDueDate = localDueDate.plusMonths(monthly.getEvery());
            Date newDueDate = Date.from(newLocalDueDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            SubTask newSubTask = subTaskRepository.save(new SubTask(subTask.getName(),subTask.getTimeTaken(), subTask.getStartDate(), newDueDate, subTask.getTaskId(),
                    active, userId, subTask.getScheduleType()));
            monthlyRepository.save(new Monthly(monthly.getEvery(),"task/"+newSubTask.getId()));
            return newSubTask;
        }
        else if(scheduleTypeName.equals("yearly")){
            LocalDate localDueDate = LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(dueDate));
            String referenceId = "subTask/"+subTask.getId();
            Yearly yearly = yearlyRepository.findByReferenceId(referenceId);
            LocalDate newLocalDueDate = localDueDate.plusYears(1L*yearly.getEvery());
            Date newDueDate = Date.from(newLocalDueDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            SubTask newSubTask = subTaskRepository.save(new SubTask(subTask.getName(),subTask.getTimeTaken(), subTask.getStartDate(), newDueDate, subTask.getTaskId(),
                    active, userId, subTask.getScheduleType()));
            yearlyRepository.save(new Yearly(yearly.getEvery(),"task/"+newSubTask.getId()));
            return newSubTask;
        }
        return subTask;
    };

  
}
