package com.bayesiansamaritan.lifeplanner.controller;


import com.bayesiansamaritan.lifeplanner.model.BadHabit;
import com.bayesiansamaritan.lifeplanner.model.Habit;
import com.bayesiansamaritan.lifeplanner.repository.BadHabitRepository;
import com.bayesiansamaritan.lifeplanner.repository.HabitRepository;
import com.bayesiansamaritan.lifeplanner.request.BadHabitCreateRequest;
import com.bayesiansamaritan.lifeplanner.request.BadHabitDescriptionRequest;
import com.bayesiansamaritan.lifeplanner.request.HabitCreateRequest;
import com.bayesiansamaritan.lifeplanner.request.HabitDescriptionRequest;
import com.bayesiansamaritan.lifeplanner.response.BadHabitResponse;
import com.bayesiansamaritan.lifeplanner.response.HabitResponse;
import com.bayesiansamaritan.lifeplanner.service.BadHabitService;
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
@RequestMapping("/api/badHabit")
public class BadHabitController {
    @Autowired
    private BadHabitRepository habitRepository;

    @Autowired
    BadHabitService habitService;

    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<List<BadHabitResponse>> getAllBadHabits(@RequestParam("userId") Long userId, @RequestParam("habitTypeName") String habitTypeName) {
        try {
            List<BadHabitResponse> habits = habitService.getAllActiveBadHabits(userId,true,habitTypeName);
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
    public ResponseEntity<BadHabit> createHabit(@RequestBody BadHabitCreateRequest habitCreateRequest)
    {
        try {
             BadHabit habit = habitService.createBadHabit(habitCreateRequest.getUserId(),habitCreateRequest.getName(),habitCreateRequest.getStartDate(),
                        habitCreateRequest.getHabitTypeName());
            return new ResponseEntity<>(habit, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<BadHabit> carriedOutBadHabit(@RequestParam("userId") Long userId,@RequestParam("id") Long id)
    {
        try {
            BadHabit habit = habitService.carriedOutBadHabit(userId,id);
            return new ResponseEntity<>(habit, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/description")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public void addDescription(@RequestBody BadHabitDescriptionRequest habitDescriptionRequest)
    {
        habitRepository.addDescription(habitDescriptionRequest.getId(),habitDescriptionRequest.getDescription());
    }


    @DeleteMapping
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public void deleteTransactions(@RequestParam("id") Long id){
        habitRepository.deleteById(id);
    }
}
