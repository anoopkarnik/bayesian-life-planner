package com.bayesiansamaritan.lifeplanner.service.impl;

import com.bayesiansamaritan.lifeplanner.model.Habit.Habit;
import com.bayesiansamaritan.lifeplanner.model.Task.*;
import com.bayesiansamaritan.lifeplanner.model.Scheduler.Daily;
import com.bayesiansamaritan.lifeplanner.model.Scheduler.Monthly;
import com.bayesiansamaritan.lifeplanner.model.Scheduler.Weekly;
import com.bayesiansamaritan.lifeplanner.model.Scheduler.Yearly;
import com.bayesiansamaritan.lifeplanner.model.Task.Task;
import com.bayesiansamaritan.lifeplanner.model.Task.TaskType;
import com.bayesiansamaritan.lifeplanner.repository.Scheduler.DailyRepository;
import com.bayesiansamaritan.lifeplanner.repository.Scheduler.MonthlyRepository;
import com.bayesiansamaritan.lifeplanner.repository.Scheduler.WeeklyRepository;
import com.bayesiansamaritan.lifeplanner.repository.Scheduler.YearlyRepository;
import com.bayesiansamaritan.lifeplanner.repository.Task.TaskRepository;
import com.bayesiansamaritan.lifeplanner.repository.Task.TaskTransactionRepository;
import com.bayesiansamaritan.lifeplanner.repository.Task.TaskTypeRepository;
import com.bayesiansamaritan.lifeplanner.response.TaskResponse;
import com.bayesiansamaritan.lifeplanner.response.TaskResponse;
import com.bayesiansamaritan.lifeplanner.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bayesiansamaritan.lifeplanner.enums.DayOfWeek;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
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

    @Autowired
    private TaskTransactionRepository taskTransactionRepository;


    @Override
    public List<TaskResponse> getAllTasks(Long userId, Boolean active, String taskTypeName){

        TaskType taskType = taskTypeRepository.findByNameAndUserId(taskTypeName, userId);
        List<Task> tasks = taskRepository.findRootTasksByUserIdAndActiveAndTaskTypeId(userId,active,taskType.getId());
        List<TaskResponse> taskResponses = new ArrayList<>();
        for (Task task: tasks){
            Long every = null;
            List<String> daysOfWeek = new ArrayList<>();
            if (task.getScheduleType().equals("daily")) {
                Daily daily = dailyRepository.findByReferenceId("task/" + task.getId());
                every = daily.getEvery();
            }
            else if(task.getScheduleType().equals("monthly")){
                Monthly monthly = monthlyRepository.findByReferenceId("task/"+task.getId());
                every = monthly.getEvery();
            }
            else if(task.getScheduleType().equals("yearly")){
                Yearly yearly = yearlyRepository.findByReferenceId("task/"+task.getId());
                every = yearly.getEvery();
            }
            else if(task.getScheduleType().equals("weekly")){
                Weekly weekly = weeklyRepository.findByReferenceId("task/"+task.getId());
                every = weekly.getEvery();
                for (DayOfWeek dayOfWeek:weekly.getDaysOfWeek()){
                    daysOfWeek.add(dayOfWeek.toString());
                }
            }
            Optional<List<Task>> childTasks1 =  taskRepository.findChildTasksByUserIdAndActiveAndParentTaskId(userId,active,task.getId());
            TaskResponse taskResponse = new TaskResponse(task.getId(),task.getCreatedAt(),
                    task.getUpdatedAt(),task.getName(),task.getScheduleType(),task.getTimeTaken(),task.getStartDate(),
                    task.getDueDate(),taskType.getName(),task.getDescription(),task.getActive(),task.getHidden(),task.getCompleted(),every,daysOfWeek);
            if (childTasks1.isPresent()){
                List<TaskResponse> childTaskResponses1 = new ArrayList<>();
                for(Task childTask1 : childTasks1.get()) {
                    Long childEvery = null;
                    List<String> childDaysOfWeek = new ArrayList<>();
                    if (childTask1.getScheduleType().equals("daily")) {
                        Daily daily = dailyRepository.findByReferenceId("task/" + childTask1.getId());
                        childEvery= daily.getEvery();
                    }
                    else if(childTask1.getScheduleType().equals("monthly")){
                        Monthly monthly = monthlyRepository.findByReferenceId("task/"+childTask1.getId());
                        childEvery= monthly.getEvery();
                    }
                    else if(childTask1.getScheduleType().equals("yearly")){
                        Yearly yearly = yearlyRepository.findByReferenceId("task/"+childTask1.getId());
                        childEvery = yearly.getEvery();
                    }
                    else if(childTask1.getScheduleType().equals("weekly")){
                        Weekly weekly = weeklyRepository.findByReferenceId("task/"+childTask1.getId());
                        childEvery = weekly.getEvery();
                        for (DayOfWeek dayOfWeek:weekly.getDaysOfWeek()){
                            daysOfWeek.add(dayOfWeek.toString());
                        }
                    }
                    Optional<TaskType> childTaskType1 = taskTypeRepository.findById(childTask1.getTaskTypeId());
                    TaskResponse childTaskResponse1 = new TaskResponse(childTask1.getId(),childTask1.getCreatedAt(),
                            childTask1.getUpdatedAt(),childTask1.getName(),childTask1.getScheduleType(),childTask1.getTimeTaken(),
                            childTask1.getStartDate(),childTask1.getDueDate(),childTaskType1.get().getName(),
                            childTask1.getDescription(),childTask1.getActive(),childTask1.getHidden(),childTask1.getCompleted(),childEvery,childDaysOfWeek);
                    childTaskResponses1.add(childTaskResponse1);
                }
                taskResponse.setTaskResponses(childTaskResponses1);
            }
            taskResponses.add(taskResponse);
        }
        return taskResponses;
    };

    @Override
    public List<TaskResponse> getAllTasksAndSubTasks(Long userId, Boolean active, String taskTypeName){

        TaskType taskType = taskTypeRepository.findByNameAndUserId(taskTypeName, userId);
        List<Task> tasks = taskRepository.findRootTasksByUserIdAndActiveAndTaskTypeId(userId,active,taskType.getId());
        List<TaskResponse> taskResponses = new ArrayList<>();
        for (Task task: tasks){
            Long every = null;
            List<String> daysOfWeek = new ArrayList<>();
            if (task.getScheduleType().equals("daily")) {
                Daily daily = dailyRepository.findByReferenceId("task/" + task.getId());
                every = daily.getEvery();
            }
            else if(task.getScheduleType().equals("monthly")){
                Monthly monthly = monthlyRepository.findByReferenceId("task/"+task.getId());
                every = monthly.getEvery();
            }
            else if(task.getScheduleType().equals("yearly")){
                Yearly yearly = yearlyRepository.findByReferenceId("task/"+task.getId());
                every = yearly.getEvery();
            }
            else if(task.getScheduleType().equals("weekly")){
                Weekly weekly = weeklyRepository.findByReferenceId("task/"+task.getId());
                every = weekly.getEvery();
                for (DayOfWeek dayOfWeek:weekly.getDaysOfWeek()){
                    daysOfWeek.add(dayOfWeek.toString());
                }
            }
            Optional<List<Task>> childTasks1 =  taskRepository.findChildTasksByUserIdAndActiveAndParentTaskId(userId,active,task.getId());
            TaskResponse taskResponse = new TaskResponse(task.getId(),task.getCreatedAt(),
                    task.getUpdatedAt(),task.getName(),task.getScheduleType(),task.getTimeTaken(),task.getStartDate(),
                    task.getDueDate(),taskType.getName(),task.getDescription(),task.getActive(),task.getHidden(),task.getCompleted(),every,daysOfWeek);
            if (childTasks1.isPresent()){
                for(Task childTask1 : childTasks1.get()) {
                    Long childEvery = null;
                    List<String> childDaysOfWeek = new ArrayList<>();
                    if (childTask1.getScheduleType().equals("daily")) {
                        Daily daily = dailyRepository.findByReferenceId("task/" + childTask1.getId());
                        childEvery= daily.getEvery();
                    }
                    else if(childTask1.getScheduleType().equals("monthly")){
                        Monthly monthly = monthlyRepository.findByReferenceId("task/"+childTask1.getId());
                        childEvery= monthly.getEvery();
                    }
                    else if(childTask1.getScheduleType().equals("yearly")){
                        Yearly yearly = yearlyRepository.findByReferenceId("task/"+childTask1.getId());
                        childEvery = yearly.getEvery();
                    }
                    else if(childTask1.getScheduleType().equals("weekly")){
                        Weekly weekly = weeklyRepository.findByReferenceId("task/"+childTask1.getId());
                        childEvery = weekly.getEvery();
                        for (DayOfWeek dayOfWeek:weekly.getDaysOfWeek()){
                            daysOfWeek.add(dayOfWeek.toString());
                        }
                    }
                    Optional<TaskType> childTaskType1 = taskTypeRepository.findById(childTask1.getTaskTypeId());
                    TaskResponse childTaskResponse1 = new TaskResponse(childTask1.getId(),childTask1.getCreatedAt(),
                            childTask1.getUpdatedAt(),childTask1.getName(),childTask1.getScheduleType(),childTask1.getTimeTaken(),
                            childTask1.getStartDate(),childTask1.getDueDate(),childTaskType1.get().getName(),
                            childTask1.getDescription(),task.getActive(),task.getHidden(),task.getCompleted(),childEvery,childDaysOfWeek);
                    taskResponses.add(childTaskResponse1);
                }
            }
            taskResponses.add(taskResponse);
        }
        return taskResponses;
    };

    @Override
    public Task createOneTimeRootTask(Long userId, String name, Date startDate, Long timeTaken, Date dueDate, Long every, String scheduleType,
                                String taskTypeName, Boolean active){
        TaskType taskType = taskTypeRepository.findByNameAndUserId(taskTypeName, userId);
        Task task = taskRepository.save(new Task(name, timeTaken, startDate, dueDate, taskType.getId(), active, userId, scheduleType,false));
        return task;
    };

    @Override
    public Task createOneTimeChildTask(Long userId, String name, Date startDate, Long timeTaken, Date dueDate, Long every, String scheduleType,
                                      String taskTypeName,String parentName, Boolean active){
        TaskType taskType = taskTypeRepository.findByNameAndUserId(taskTypeName, userId);
        Task oldTask = taskRepository.findByUserIdAndName(userId,parentName);
        Task task = taskRepository.save(new Task(name, timeTaken, startDate, dueDate, taskType.getId(), active, userId, scheduleType, oldTask.getId(),false));
        return task;
    };

    @Override
    public Task createDailyRootTask(Long userId, String name, Date startDate, Long timeTaken, Date dueDate, Long every, String scheduleType,
                            String taskTypeName, Boolean active){
        TaskType taskType = taskTypeRepository.findByNameAndUserId(taskTypeName, userId);
        Task task = taskRepository.save(new Task(name, timeTaken, startDate, dueDate, taskType.getId(), active, userId, scheduleType,false));
        Daily daily = dailyRepository.save(new Daily(every,"task/"+task.getId()));
        return task;
    };

    @Override
    public Task createDailyChildTask(Long userId, String name, Date startDate, Long timeTaken, Date dueDate, Long every, String scheduleType,
                                String taskTypeName,String parentName, Boolean active){
        TaskType taskType = taskTypeRepository.findByNameAndUserId(taskTypeName, userId);
        Task oldTask = taskRepository.findByUserIdAndName(userId,parentName);
        Task task = taskRepository.save(new Task(name, timeTaken, startDate, dueDate, taskType.getId(), active, userId, scheduleType, oldTask.getId(),false));
        Daily daily = dailyRepository.save(new Daily(every,"task/"+task.getId()));
        return task;
    };

    @Override
    public Task createWeeklyRootTask(Long userId, String name, Date startDate, Long timeTaken, Date dueDate, Long every, String scheduleType,
                                String taskTypeName, List<DayOfWeek> daysOfWeek, Boolean active){
        TaskType taskType = taskTypeRepository.findByNameAndUserId(taskTypeName, userId);
        Task task = taskRepository.save(new Task(name, timeTaken, startDate, dueDate, taskType.getId(), active, userId, scheduleType,false));
        Weekly weekly = weeklyRepository.save(new Weekly(every,"task/"+task.getId(),daysOfWeek));
        return task;
    };

    @Override
    public Task createWeeklyChildTask(Long userId, String name, Date startDate, Long timeTaken, Date dueDate, Long every, String scheduleType,
                                 String taskTypeName, List<DayOfWeek> daysOfWeek,String parentName, Boolean active){
        TaskType taskType = taskTypeRepository.findByNameAndUserId(taskTypeName, userId);
        Task oldTask = taskRepository.findByUserIdAndName(userId,parentName);
        Task task = taskRepository.save(new Task(name, timeTaken, startDate, dueDate, taskType.getId(), active, userId, scheduleType, oldTask.getId(),false));
        Weekly weekly = weeklyRepository.save(new Weekly(every,"task/"+task.getId(),daysOfWeek));
        return task;
    };

    @Override
    public Task createMonthlyRootTask(Long userId, String name, Date startDate, Long timeTaken, Date dueDate, Long every, String scheduleType,
                                String taskTypeName, Boolean active){
        TaskType taskType = taskTypeRepository.findByNameAndUserId(taskTypeName, userId);
        Task task = taskRepository.save(new Task(name, timeTaken, startDate, dueDate, taskType.getId(), active, userId, scheduleType,false));
        Monthly monthly = monthlyRepository.save(new Monthly(every,"task/"+task.getId()));
        return task;
    };

    @Override
    public Task createMonthlyChildTask(Long userId, String name, Date startDate, Long timeTaken, Date dueDate, Long every, String scheduleType,
                                  String taskTypeName,String parentName, Boolean active){
        TaskType taskType = taskTypeRepository.findByNameAndUserId(taskTypeName, userId);
        Task oldTask = taskRepository.findByUserIdAndName(userId,parentName);
        Task task = taskRepository.save(new Task(name, timeTaken, startDate, dueDate, taskType.getId(), active, userId, scheduleType, oldTask.getId(),false));
        Monthly monthly = monthlyRepository.save(new Monthly(every,"task/"+task.getId()));
        return task;
    };

    @Override
    public Task createYearlyRootTask(Long userId, String name, Date startDate, Long timeTaken, Date dueDate, Long every, String scheduleType,
                                String taskTypeName, Boolean active){
        TaskType taskType = taskTypeRepository.findByNameAndUserId(taskTypeName, userId);
        Task task = taskRepository.save(new Task(name, timeTaken, startDate, dueDate, taskType.getId(), active, userId, scheduleType,false));
        Yearly yearly = yearlyRepository.save(new Yearly(every,"task/"+task.getId()));
        return task;
    };

    @Override
    public Task createYearlyChildTask(Long userId, String name, Date startDate, Long timeTaken, Date dueDate, Long every, String scheduleType,
                                 String taskTypeName,String parentName, Boolean active){
        TaskType taskType = taskTypeRepository.findByNameAndUserId(taskTypeName, userId);
        Task oldTask = taskRepository.findByUserIdAndName(userId,parentName);
        Task task = taskRepository.save(new Task(name, timeTaken, startDate, dueDate, taskType.getId(), active, userId, scheduleType, oldTask.getId(),false));
        Yearly yearly = yearlyRepository.save(new Yearly(every,"task/"+task.getId()));
        return task;
    };

    @Override
    public Task completeTask(Long userId, Long id){
        Task oldTask = taskRepository.findByUserIdAndId(userId,id);
        String scheduleTypeName = oldTask.getScheduleType();
        Date dueDate = oldTask.getDueDate();
        Task task = taskRepository.findByUserIdAndId(userId,id);
        taskTransactionRepository.save(new TaskTransaction(task.getName(),task.getTimeTaken(),task.getStartDate(),task.getDueDate(),task.getTaskTypeId(),
                task.getUserId(),task.getScheduleType(),task.getId()));
        Boolean active = true;
        if(scheduleTypeName.equals("daily")){
            String referenceId = "task/"+task.getId();
            Daily daily = dailyRepository.findByReferenceId(referenceId);
            Long time = dueDate.getTime();
            Date newDueDate1 = new Date(time + 1000*60*60*18 - time%(24*60*60*1000));
            Date newDueDate = new Date(dueDate.getTime() + (1000*60*60*24*daily.getEvery()));
            Date currentDate = new Date();
            if (newDueDate.compareTo(currentDate)<0){
                Date newDueDate2 = new Date(currentDate.getTime() + 1000*60*60*18 - currentDate.getTime()%(24*60*60*1000) + (1000*60*60*24*daily.getEvery()));
                taskRepository.modifyDueDate(task.getId(),newDueDate2);
            }
            else{
                taskRepository.modifyDueDate(task.getId(),newDueDate);
            }
        }
        else if(scheduleTypeName.equals("weekly")){
            String referenceId = "task/"+task.getId();
            Weekly weekly = weeklyRepository.findByReferenceId(referenceId);
            List<DayOfWeek> daysOfWeek = weekly.getDaysOfWeek();
            LocalDate localDueDate = LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(dueDate));
            Boolean match = false;
            Long current = 1L;
            while( !match){
                DayOfWeek dayOfWeek = DayOfWeek.valueOf(localDueDate.getDayOfWeek().toString());
                if (dayOfWeek.equals(dayOfWeek.SUNDAY)){
                    localDueDate = localDueDate.plusDays(7L* (weekly.getEvery()-1));
                }
                localDueDate = localDueDate.plusDays(1L);
                for(DayOfWeek dayOfWeek1:daysOfWeek) {
                    if (dayOfWeek1.name().equals(localDueDate.getDayOfWeek().name())) {
                        match = true;
                    }
                }
            }
            Date newDueDate = Date.from(localDueDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            Date newDueDate1 = new Date(newDueDate.getTime()+ 1000*60*60*23);
            taskRepository.modifyDueDate(task.getId(),newDueDate1);
        }
        else if(scheduleTypeName.equals("monthly")){
            LocalDate localDueDate = LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(dueDate));
            String referenceId = "task/"+task.getId();
            Monthly monthly = monthlyRepository.findByReferenceId(referenceId);
            LocalDate newLocalDueDate = localDueDate.plusMonths(monthly.getEvery());
            Date newDueDate = Date.from(newLocalDueDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            taskRepository.modifyDueDate(task.getId(),newDueDate);
        }
        else if(scheduleTypeName.equals("yearly")){
            LocalDate localDueDate = LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(dueDate));
            String referenceId = "task/"+task.getId();
            Yearly yearly = yearlyRepository.findByReferenceId(referenceId);
            LocalDate newLocalDueDate = localDueDate.plusYears(1L*yearly.getEvery());
            Date newDueDate = Date.from(newLocalDueDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            taskRepository.modifyDueDate(task.getId(),newDueDate);
        }
        else{
            taskRepository.completeTask(task.getId(),true);
        }
        return task;
    };

    @Override
    public void modifySchedule(Long userId, Long id,String scheduleType,Long every, List<DayOfWeek> daysOfWeek){
        taskRepository.modifyScheduleType(id,scheduleType);
        Task task = taskRepository.findByUserIdAndId(userId,id);
        if(scheduleType.equals("daily")){
            Daily daily = dailyRepository.findByReferenceId("task/"+task.getId());
            dailyRepository.deleteById(daily.getId());
            dailyRepository.save(new Daily(every,"task/"+task.getId()));
        }
        else if(scheduleType.equals("monthly")){
            Monthly monthly = monthlyRepository.findByReferenceId("task/"+task.getId());
            monthlyRepository.deleteById(monthly.getId());
            monthlyRepository.save(new Monthly(every,"task/"+task.getId()));
        }
        else if(scheduleType.equals("yearly")){
            Yearly yearly = yearlyRepository.findByReferenceId("task/"+task.getId());
            yearlyRepository.deleteById(yearly.getId());
            yearlyRepository.save(new Yearly(every,"task/"+task.getId()));
        }
        else if(scheduleType.equals("weekly")){
            Weekly weekly = weeklyRepository.findByReferenceId("task/"+task.getId());
            weeklyRepository.deleteById(weekly.getId());
            weeklyRepository.save(new Weekly(every,"task/"+task.getId(),daysOfWeek));
        }
    }
}
