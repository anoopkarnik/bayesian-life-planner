package com.bayesiansamaritan.lifeplanner.controller;


import com.bayesiansamaritan.lifeplanner.model.SubTask;
import com.bayesiansamaritan.lifeplanner.model.Task;
import com.bayesiansamaritan.lifeplanner.repository.SubTaskRepository;
import com.bayesiansamaritan.lifeplanner.repository.TaskRepository;
import com.bayesiansamaritan.lifeplanner.request.SubTaskCreateRequest;
import com.bayesiansamaritan.lifeplanner.request.TaskCreateRequest;
import com.bayesiansamaritan.lifeplanner.service.SubTaskService;
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
@RequestMapping("/api/subTask")
public class SubTaskController {
    @Autowired
    private SubTaskRepository subTaskRepository;

    @Autowired
    SubTaskService subTaskService;

    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<List<SubTask>> getAllSubTasks(@RequestParam("userId") Long userId, @RequestParam("taskId") Long taskId) {
        try {
            List<SubTask> subTasks = subTaskService.getAllActiveSubTasks(userId,true,taskId);
            if (subTasks.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(subTasks , HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<SubTask> createSubTask(@RequestBody SubTaskCreateRequest subTaskCreateRequest)
    {
        try {
            SubTask subTask;
            if (subTaskCreateRequest.getScheduleType().equals("daily")){
                subTask = subTaskService.createDailySubTask(subTaskCreateRequest.getUserId(),subTaskCreateRequest.getName(),subTaskCreateRequest.getStartDate(),
                        subTaskCreateRequest.getTimeTaken(),subTaskCreateRequest.getDueDate(),subTaskCreateRequest.getEvery(),subTaskCreateRequest.getScheduleType(),
                        subTaskCreateRequest.getTaskId());
            }
            else if(subTaskCreateRequest.getScheduleType().equals("monthly")){
                subTask = subTaskService.createMonthlySubTask(subTaskCreateRequest.getUserId(),subTaskCreateRequest.getName(),subTaskCreateRequest.getStartDate(),
                        subTaskCreateRequest.getTimeTaken(),subTaskCreateRequest.getDueDate(),subTaskCreateRequest.getEvery(),subTaskCreateRequest.getScheduleType(),
                        subTaskCreateRequest.getTaskId());
            }
            else if(subTaskCreateRequest.getScheduleType().equals("onetime")){
                subTask = subTaskService.createOneTimeSubTask(subTaskCreateRequest.getUserId(),subTaskCreateRequest.getName(),subTaskCreateRequest.getStartDate(),
                        subTaskCreateRequest.getTimeTaken(),subTaskCreateRequest.getDueDate(),subTaskCreateRequest.getEvery(),subTaskCreateRequest.getScheduleType(),
                        subTaskCreateRequest.getTaskId());
            }
            else if(subTaskCreateRequest.getScheduleType().equals("yearly")){
                subTask= subTaskService.createYearlySubTask(subTaskCreateRequest.getUserId(),subTaskCreateRequest.getName(),subTaskCreateRequest.getStartDate(),
                        subTaskCreateRequest.getTimeTaken(),subTaskCreateRequest.getDueDate(),subTaskCreateRequest.getEvery(),subTaskCreateRequest.getScheduleType(),
                        subTaskCreateRequest.getTaskId());
            }
            else{
                subTask = subTaskService.createWeeklySubTask(subTaskCreateRequest.getUserId(),subTaskCreateRequest.getName(),subTaskCreateRequest.getStartDate(),
                        subTaskCreateRequest.getTimeTaken(),subTaskCreateRequest.getDueDate(),subTaskCreateRequest.getEvery(),subTaskCreateRequest.getScheduleType(),
                        subTaskCreateRequest.getTaskId(),subTaskCreateRequest.getDaysOfWeek());
            }
            return new ResponseEntity<>(subTask, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<SubTask> completeTask(@RequestParam("userId") Long userId,@RequestParam("id") Long id)
    {
        try {
            SubTask subTask = subTaskService.completeSubTask(userId,id);
            return new ResponseEntity<>(subTask, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public void deleteTransactions(@RequestParam("id") Long id){
        subTaskRepository.deleteById(id);
    }
}
