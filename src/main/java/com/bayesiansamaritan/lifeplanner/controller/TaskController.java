package com.bayesiansamaritan.lifeplanner.controller;


import com.bayesiansamaritan.lifeplanner.enums.DayOfWeek;
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
import com.bayesiansamaritan.lifeplanner.repository.Task.TaskTypeRepository;
import com.bayesiansamaritan.lifeplanner.repository.User.UserProfileRepository;
import com.bayesiansamaritan.lifeplanner.request.Habit.HabitScheduleRequest;
import com.bayesiansamaritan.lifeplanner.request.Task.TaskCreateChildRequest;
import com.bayesiansamaritan.lifeplanner.request.Task.TaskCreateRootRequest;
import com.bayesiansamaritan.lifeplanner.request.Task.TaskModifyRequest;
import com.bayesiansamaritan.lifeplanner.response.TaskResponse;
import com.bayesiansamaritan.lifeplanner.security.jwt.JwtUtils;
import com.bayesiansamaritan.lifeplanner.service.TaskService;
import com.bayesiansamaritan.lifeplanner.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/task")
public class TaskController {
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private TaskTypeRepository taskTypeRepository;
    @Autowired
    private DailyRepository dailyRepository;
    @Autowired
    private WeeklyRepository weeklyRepository;
    @Autowired
    private MonthlyRepository monthlyRepository;
    @Autowired
    private YearlyRepository yearlyRepository;

    @Autowired
    TaskService taskService;
    @Autowired
    private UserProfileRepository userProfileRepository;
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    DateUtils dateUtils;
    static final String HEADER_STRING = "Authorization";
    static final String TOKEN_PREFIX = "Bearer";

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public TaskResponse getTask(@PathVariable("id") Long taskId) {
        Task task = taskRepository.findById(taskId).get();
        TaskType taskType = taskTypeRepository.findById(taskId).get();
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
        TaskResponse taskResponse = new TaskResponse(task.getId(),task.getCreatedAt(),task.getUpdatedAt(),task.getName(),task.getScheduleType(),task.getTimeTaken(),
                task.getStartDate(),task.getDueDate(),taskType.getName(),task.getDescription(),task.getActive(),task.getHidden(),task.getCompleted(),every,daysOfWeek);
        return taskResponse;

    }

    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<List<TaskResponse>> getAllTasks(HttpServletRequest request, @RequestParam("taskTypeName") String taskTypeName,
                                                          @RequestParam("active") Boolean active) {
        String username = jwtUtils.getUserNameFromJwtToken(request.getHeader(HEADER_STRING).replace(TOKEN_PREFIX,""));
        Long userId = userProfileRepository.findByName(username).get().getId();
        try {
            List<TaskResponse> taskResponses = taskService.getAllTasks(userId,active,taskTypeName);
            if (taskResponses.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(taskResponses , HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/root")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Task> createRootTask(HttpServletRequest request,@RequestBody TaskCreateRootRequest taskCreateRootRequest)
    {
        String username = jwtUtils.getUserNameFromJwtToken(request.getHeader(HEADER_STRING).replace(TOKEN_PREFIX,""));
        Long userId = userProfileRepository.findByName(username).get().getId();
        try {
            Task task;
            if (taskCreateRootRequest.getScheduleType().equals("daily")){
                task = taskService.createDailyRootTask(userId, taskCreateRootRequest.getName(), taskCreateRootRequest.getStartDate(),
                        taskCreateRootRequest.getTimeTaken(), taskCreateRootRequest.getDueDate(), taskCreateRootRequest.getEvery(), taskCreateRootRequest.getScheduleType(),
                        taskCreateRootRequest.getTaskTypeName(),taskCreateRootRequest.getActive());
            }
            else if(taskCreateRootRequest.getScheduleType().equals("monthly")){
                task = taskService.createMonthlyRootTask(userId, taskCreateRootRequest.getName(), taskCreateRootRequest.getStartDate(),
                        taskCreateRootRequest.getTimeTaken(), taskCreateRootRequest.getDueDate(), taskCreateRootRequest.getEvery(), taskCreateRootRequest.getScheduleType(),
                        taskCreateRootRequest.getTaskTypeName(),taskCreateRootRequest.getActive());
            }
            else if(taskCreateRootRequest.getScheduleType().equals("yearly")){
                task = taskService.createYearlyRootTask(userId, taskCreateRootRequest.getName(), taskCreateRootRequest.getStartDate(),
                        taskCreateRootRequest.getTimeTaken(), taskCreateRootRequest.getDueDate(), taskCreateRootRequest.getEvery(), taskCreateRootRequest.getScheduleType(),
                        taskCreateRootRequest.getTaskTypeName(),taskCreateRootRequest.getActive());
            }
            else if(taskCreateRootRequest.getScheduleType().equals("onetime")){
                task = taskService.createOneTimeRootTask(userId, taskCreateRootRequest.getName(), taskCreateRootRequest.getStartDate(),
                        taskCreateRootRequest.getTimeTaken(), taskCreateRootRequest.getDueDate(), taskCreateRootRequest.getEvery(), taskCreateRootRequest.getScheduleType(),
                        taskCreateRootRequest.getTaskTypeName(),taskCreateRootRequest.getActive());
            }
            else{
                task = taskService.createWeeklyRootTask(userId, taskCreateRootRequest.getName(), taskCreateRootRequest.getStartDate(),
                        taskCreateRootRequest.getTimeTaken(), taskCreateRootRequest.getDueDate(), taskCreateRootRequest.getEvery(), taskCreateRootRequest.getScheduleType(),
                        taskCreateRootRequest.getTaskTypeName(), taskCreateRootRequest.getDaysOfWeek(),taskCreateRootRequest.getActive());
            }
            return new ResponseEntity<>(task, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/child")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Task> createChildTask(HttpServletRequest request,@RequestBody TaskCreateChildRequest taskCreateChildRequest)
    {
        String username = jwtUtils.getUserNameFromJwtToken(request.getHeader(HEADER_STRING).replace(TOKEN_PREFIX,""));
        Long userId = userProfileRepository.findByName(username).get().getId();
        try {
            Task task;
            if (taskCreateChildRequest.getScheduleType().equals("daily")){
                task = taskService.createDailyChildTask(userId, taskCreateChildRequest.getName(), taskCreateChildRequest.getStartDate(),
                        taskCreateChildRequest.getTimeTaken(), taskCreateChildRequest.getDueDate(), taskCreateChildRequest.getEvery(), taskCreateChildRequest.getScheduleType(),
                        taskCreateChildRequest.getTaskTypeName(),taskCreateChildRequest.getParentTaskName(),taskCreateChildRequest.getActive());
            }
            else if(taskCreateChildRequest.getScheduleType().equals("monthly")){
                task = taskService.createMonthlyChildTask(userId, taskCreateChildRequest.getName(), taskCreateChildRequest.getStartDate(),
                        taskCreateChildRequest.getTimeTaken(), taskCreateChildRequest.getDueDate(), taskCreateChildRequest.getEvery(), taskCreateChildRequest.getScheduleType(),
                        taskCreateChildRequest.getTaskTypeName(),taskCreateChildRequest.getParentTaskName(),taskCreateChildRequest.getActive());
            }
            else if(taskCreateChildRequest.getScheduleType().equals("yearly")){
                task = taskService.createYearlyChildTask(userId, taskCreateChildRequest.getName(), taskCreateChildRequest.getStartDate(),
                        taskCreateChildRequest.getTimeTaken(), taskCreateChildRequest.getDueDate(), taskCreateChildRequest.getEvery(), taskCreateChildRequest.getScheduleType(),
                        taskCreateChildRequest.getTaskTypeName(),taskCreateChildRequest.getParentTaskName(),taskCreateChildRequest.getActive());
            }
            else if(taskCreateChildRequest.getScheduleType().equals("onetime")){
                task = taskService.createOneTimeChildTask(userId, taskCreateChildRequest.getName(), taskCreateChildRequest.getStartDate(),
                        taskCreateChildRequest.getTimeTaken(), taskCreateChildRequest.getDueDate(), taskCreateChildRequest.getEvery(), taskCreateChildRequest.getScheduleType(),
                        taskCreateChildRequest.getTaskTypeName(),taskCreateChildRequest.getParentTaskName(),taskCreateChildRequest.getActive());
            }
            else{
                task = taskService.createWeeklyChildTask(userId, taskCreateChildRequest.getName(), taskCreateChildRequest.getStartDate(),
                        taskCreateChildRequest.getTimeTaken(), taskCreateChildRequest.getDueDate(), taskCreateChildRequest.getEvery(), taskCreateChildRequest.getScheduleType(),
                        taskCreateChildRequest.getTaskTypeName(), taskCreateChildRequest.getDaysOfWeek(),taskCreateChildRequest.getParentTaskName()
                        ,taskCreateChildRequest.getActive());
            }
            return new ResponseEntity<>(task, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Task> completeTask(HttpServletRequest request,@RequestParam("id") Long id)
    {
        String username = jwtUtils.getUserNameFromJwtToken(request.getHeader(HEADER_STRING).replace(TOKEN_PREFIX,""));
        Long userId = userProfileRepository.findByName(username).get().getId();
        try {
            Task task = taskService.completeTask(userId,id);
            return new ResponseEntity<>(task, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/modifyParams")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public void modifyParams(@RequestBody TaskModifyRequest taskModifyRequest)
    {
        Date newDueDate = dateUtils.getEndOfDay(taskModifyRequest.getDueDate());
        taskRepository.modifyParams(taskModifyRequest.getId(),taskModifyRequest.getName(),taskModifyRequest.getStartDate(),taskModifyRequest.getDescription(),
                taskModifyRequest.getActive(),taskModifyRequest.getHidden(),taskModifyRequest.getCompleted(),taskModifyRequest.getTimeTaken(),
                newDueDate);
    }

    @PatchMapping("/modifyScheduleType")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public void modifyScheduleType(HttpServletRequest request,@RequestBody HabitScheduleRequest taskScheduleRequest)
    {
        String username = jwtUtils.getUserNameFromJwtToken(request.getHeader(HEADER_STRING).replace(TOKEN_PREFIX,""));
        Long userId = userProfileRepository.findByName(username).get().getId();
        taskService.modifySchedule(userId,taskScheduleRequest.getId(),taskScheduleRequest.getScheduleType(),taskScheduleRequest.getEvery(),
                taskScheduleRequest.getDaysOfWeek());
    }

    @DeleteMapping
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public void deleteTransactions(@RequestParam("id") Long id){
        taskRepository.deleteById(id);
    }
}
