package com.bayesiansamaritan.lifeplanner.controller;


import com.bayesiansamaritan.lifeplanner.model.Habit;
import com.bayesiansamaritan.lifeplanner.repository.HabitRepository;
import com.bayesiansamaritan.lifeplanner.repository.HabitRepository;
import com.bayesiansamaritan.lifeplanner.request.HabitCreateRequest;
import com.bayesiansamaritan.lifeplanner.request.HabitDescriptionRequest;
import com.bayesiansamaritan.lifeplanner.request.TaskDescriptionRequest;
import com.bayesiansamaritan.lifeplanner.response.HabitResponse;
import com.bayesiansamaritan.lifeplanner.service.HabitService;
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
@RequestMapping("/api/habit")
public class HabitController {
    @Autowired
    private HabitRepository habitRepository;

    @Autowired
    HabitService habitService;

    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<List<HabitResponse>> getAllHabits(@RequestParam("userId") Long userId, @RequestParam("habitTypeName") String habitTypeName) {
        try {
            List<HabitResponse> habits = habitService.getAllActiveHabits(userId,true,habitTypeName);
            if (habits.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(habits , HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Habit> createHabit(@RequestBody HabitCreateRequest habitCreateRequest)
    {
        try {
            Habit habit;
            if(habitCreateRequest.getScheduleType().equals("daily")){
                habit = habitService.createDailyHabit(habitCreateRequest.getUserId(),habitCreateRequest.getName(),habitCreateRequest.getStartDate(),
                        habitCreateRequest.getTimeTaken(),habitCreateRequest.getDueDate(),habitCreateRequest.getEvery(),habitCreateRequest.getScheduleType(),
                        habitCreateRequest.getHabitTypeName());
            }
            else if(habitCreateRequest.getScheduleType().equals("monthly")){
                habit = habitService.createMonthlyHabit(habitCreateRequest.getUserId(),habitCreateRequest.getName(),habitCreateRequest.getStartDate(),
                        habitCreateRequest.getTimeTaken(),habitCreateRequest.getDueDate(),habitCreateRequest.getEvery(),habitCreateRequest.getScheduleType(),
                        habitCreateRequest.getHabitTypeName());
            }
            else if(habitCreateRequest.getScheduleType().equals("yearly")){
                habit = habitService.createYearlyHabit(habitCreateRequest.getUserId(),habitCreateRequest.getName(),habitCreateRequest.getStartDate(),
                        habitCreateRequest.getTimeTaken(),habitCreateRequest.getDueDate(),habitCreateRequest.getEvery(),habitCreateRequest.getScheduleType(),
                        habitCreateRequest.getHabitTypeName());
            }
            else{
                habit = habitService.createWeeklyHabit(habitCreateRequest.getUserId(),habitCreateRequest.getName(),habitCreateRequest.getStartDate(),
                        habitCreateRequest.getTimeTaken(),habitCreateRequest.getDueDate(),habitCreateRequest.getEvery(),habitCreateRequest.getScheduleType(),
                        habitCreateRequest.getHabitTypeName(),habitCreateRequest.getDaysOfWeek());
            }
            return new ResponseEntity<>(habit, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Habit> completeHabit(@RequestParam("userId") Long userId,@RequestParam("id") Long id)
    {
        try {
            Habit habit = habitService.completeHabit(userId,id);
            return new ResponseEntity<>(habit, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/description")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public void addDescription(@RequestBody HabitDescriptionRequest habitDescriptionRequest)
    {
        habitRepository.addDescription(habitDescriptionRequest.getId(),habitDescriptionRequest.getDescription());
    }


    @DeleteMapping
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public void deleteTransactions(@RequestParam("id") Long id){
        habitRepository.deleteById(id);
    }
}
