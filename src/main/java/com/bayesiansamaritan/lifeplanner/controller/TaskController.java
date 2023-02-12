package com.bayesiansamaritan.lifeplanner.controller;


import com.bayesiansamaritan.lifeplanner.model.Task;
import com.bayesiansamaritan.lifeplanner.repository.TaskRepository;
import com.bayesiansamaritan.lifeplanner.request.TaskCreateRequest;
import com.bayesiansamaritan.lifeplanner.request.TaskDescriptionRequest;
import com.bayesiansamaritan.lifeplanner.response.TaskResponse;
import com.bayesiansamaritan.lifeplanner.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/task")
public class TaskController {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    TaskService taskService;

    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<List<TaskResponse>> getAllTasks(@RequestParam("userId") Long userId, @RequestParam("taskTypeName") String taskTypeName) {
        try {
            List<TaskResponse> taskResponses = taskService.getAllActiveTasks(userId,true,taskTypeName);
            if (taskResponses.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(taskResponses , HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Task> createTask(@RequestBody TaskCreateRequest taskCreateRequest)
    {
        try {
            Task task;
            if (taskCreateRequest.getScheduleType().equals("daily")){
                task = taskService.createDailyTask(taskCreateRequest.getUserId(),taskCreateRequest.getName(),taskCreateRequest.getStartDate(),
                        taskCreateRequest.getTimeTaken(),taskCreateRequest.getDueDate(),taskCreateRequest.getEvery(),taskCreateRequest.getScheduleType(),
                        taskCreateRequest.getTaskTypeName());
            }
            else if(taskCreateRequest.getScheduleType().equals("monthly")){
                task = taskService.createMonthlyTask(taskCreateRequest.getUserId(),taskCreateRequest.getName(),taskCreateRequest.getStartDate(),
                        taskCreateRequest.getTimeTaken(),taskCreateRequest.getDueDate(),taskCreateRequest.getEvery(),taskCreateRequest.getScheduleType(),
                        taskCreateRequest.getTaskTypeName());
            }
            else if(taskCreateRequest.getScheduleType().equals("yearly")){
                task = taskService.createYearlyTask(taskCreateRequest.getUserId(),taskCreateRequest.getName(),taskCreateRequest.getStartDate(),
                        taskCreateRequest.getTimeTaken(),taskCreateRequest.getDueDate(),taskCreateRequest.getEvery(),taskCreateRequest.getScheduleType(),
                        taskCreateRequest.getTaskTypeName());
            }
            else if(taskCreateRequest.getScheduleType().equals("onetime")){
                task = taskService.createOneTimeTask(taskCreateRequest.getUserId(),taskCreateRequest.getName(),taskCreateRequest.getStartDate(),
                        taskCreateRequest.getTimeTaken(),taskCreateRequest.getDueDate(),taskCreateRequest.getEvery(),taskCreateRequest.getScheduleType(),
                        taskCreateRequest.getTaskTypeName());
            }
            else{
                task = taskService.createWeeklyTask(taskCreateRequest.getUserId(),taskCreateRequest.getName(),taskCreateRequest.getStartDate(),
                        taskCreateRequest.getTimeTaken(),taskCreateRequest.getDueDate(),taskCreateRequest.getEvery(),taskCreateRequest.getScheduleType(),
                        taskCreateRequest.getTaskTypeName(),taskCreateRequest.getDaysOfWeek());
            }
            return new ResponseEntity<>(task, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Task> completeTask(@RequestParam("userId") Long userId,@RequestParam("id") Long id)
    {
        try {
            Task task = taskService.completeTask(userId,id);
            return new ResponseEntity<>(task, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/description")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public void addDescription(@RequestBody TaskDescriptionRequest taskDescriptionRequest)
    {
        taskRepository.addDescription(taskDescriptionRequest.getId(),taskDescriptionRequest.getDescription());
    }

    @DeleteMapping
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public void deleteTransactions(@RequestParam("id") Long id){
        taskRepository.deleteById(id);
    }
}
