package com.bayesiansamaritan.lifeplanner.controller;


import com.bayesiansamaritan.lifeplanner.model.Habit.Habit;
import com.bayesiansamaritan.lifeplanner.repository.Habit.HabitRepository;
import com.bayesiansamaritan.lifeplanner.repository.User.UserProfileRepository;
import com.bayesiansamaritan.lifeplanner.request.Habit.HabitCreateChildRequest;
import com.bayesiansamaritan.lifeplanner.request.Habit.HabitCreateRootRequest;
import com.bayesiansamaritan.lifeplanner.request.Habit.HabitModifyRequest;
import com.bayesiansamaritan.lifeplanner.request.Habit.HabitScheduleRequest;
import com.bayesiansamaritan.lifeplanner.response.HabitResponse;
import com.bayesiansamaritan.lifeplanner.security.jwt.JwtUtils;
import com.bayesiansamaritan.lifeplanner.service.HabitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
    @Autowired
    private UserProfileRepository userProfileRepository;
    @Autowired
    JwtUtils jwtUtils;
    static final String HEADER_STRING = "Authorization";
    static final String TOKEN_PREFIX = "Bearer";

    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<List<HabitResponse>> getAllHabits(HttpServletRequest request, @RequestParam("habitTypeName") String habitTypeName,
                                                            @RequestParam("active") Boolean active) {
        String username = jwtUtils.getUserNameFromJwtToken(request.getHeader(HEADER_STRING).replace(TOKEN_PREFIX,""));
        Long userId = userProfileRepository.findByName(username).get().getId();
        try {
            List<HabitResponse> habits = habitService.getAllHabits(userId,active,habitTypeName);
            if (habits.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(habits , HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/root")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Habit> createHabit(HttpServletRequest request,@RequestBody HabitCreateRootRequest habitCreateRootRequest)
    {
        String username = jwtUtils.getUserNameFromJwtToken(request.getHeader(HEADER_STRING).replace(TOKEN_PREFIX,""));
        Long userId = userProfileRepository.findByName(username).get().getId();
        try {
            Habit habit;
            if(habitCreateRootRequest.getScheduleType().equals("daily")){
                habit = habitService.createDailyRootHabit(userId, habitCreateRootRequest.getName(), habitCreateRootRequest.getStartDate(),
                        habitCreateRootRequest.getTimeOfDay(), habitCreateRootRequest.getDueDate(), habitCreateRootRequest.getEvery(), habitCreateRootRequest.getScheduleType(),
                        habitCreateRootRequest.getHabitTypeName(),habitCreateRootRequest.getActive());
            }
            else if(habitCreateRootRequest.getScheduleType().equals("monthly")){
                habit = habitService.createMonthlyRootHabit(userId, habitCreateRootRequest.getName(), habitCreateRootRequest.getStartDate(),
                        habitCreateRootRequest.getTimeOfDay(), habitCreateRootRequest.getDueDate(), habitCreateRootRequest.getEvery(), habitCreateRootRequest.getScheduleType(),
                        habitCreateRootRequest.getHabitTypeName(),habitCreateRootRequest.getActive());
            }
            else if(habitCreateRootRequest.getScheduleType().equals("yearly")){
                habit = habitService.createYearlyRootHabit(userId, habitCreateRootRequest.getName(), habitCreateRootRequest.getStartDate(),
                        habitCreateRootRequest.getTimeOfDay(),habitCreateRootRequest.getDueDate(), habitCreateRootRequest.getEvery(), habitCreateRootRequest.getScheduleType(),
                        habitCreateRootRequest.getHabitTypeName(),habitCreateRootRequest.getActive());
            }
            else{
                habit = habitService.createWeeklyRootHabit(userId, habitCreateRootRequest.getName(), habitCreateRootRequest.getStartDate(),
                        habitCreateRootRequest.getTimeOfDay(), habitCreateRootRequest.getDueDate(), habitCreateRootRequest.getEvery(), habitCreateRootRequest.getScheduleType(),
                        habitCreateRootRequest.getHabitTypeName(), habitCreateRootRequest.getDaysOfWeek(),habitCreateRootRequest.getActive());
            }
            return new ResponseEntity<>(habit, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/child")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Habit> createChildHabit(HttpServletRequest request,@RequestBody HabitCreateChildRequest habitCreateChildRequest)
    {
        String username = jwtUtils.getUserNameFromJwtToken(request.getHeader(HEADER_STRING).replace(TOKEN_PREFIX,""));
        Long userId = userProfileRepository.findByName(username).get().getId();
        try {
            Habit habit;
            if(habitCreateChildRequest.getScheduleType().equals("daily")){
                habit = habitService.createDailyChildHabit(userId, habitCreateChildRequest.getName(), habitCreateChildRequest.getStartDate(),
                        habitCreateChildRequest.getTimeOfDay(), habitCreateChildRequest.getDueDate(), habitCreateChildRequest.getEvery(), habitCreateChildRequest.getScheduleType(),
                        habitCreateChildRequest.getHabitTypeName(),habitCreateChildRequest.getParentHabitName(),habitCreateChildRequest.getActive());
            }
            else if(habitCreateChildRequest.getScheduleType().equals("monthly")){
                habit = habitService.createMonthlyChildHabit(userId, habitCreateChildRequest.getName(), habitCreateChildRequest.getStartDate(),
                        habitCreateChildRequest.getTimeOfDay(), habitCreateChildRequest.getDueDate(), habitCreateChildRequest.getEvery(), habitCreateChildRequest.getScheduleType(),
                        habitCreateChildRequest.getHabitTypeName(),habitCreateChildRequest.getParentHabitName(),habitCreateChildRequest.getActive());
            }
            else if(habitCreateChildRequest.getScheduleType().equals("yearly")){
                habit = habitService.createYearlyChildHabit(userId, habitCreateChildRequest.getName(), habitCreateChildRequest.getStartDate(),
                        habitCreateChildRequest.getTimeOfDay(),habitCreateChildRequest.getDueDate(), habitCreateChildRequest.getEvery(), habitCreateChildRequest.getScheduleType(),
                        habitCreateChildRequest.getHabitTypeName(),habitCreateChildRequest.getParentHabitName(),habitCreateChildRequest.getActive());
            }
            else{
                habit = habitService.createWeeklyChildHabit(userId, habitCreateChildRequest.getName(), habitCreateChildRequest.getStartDate(),
                        habitCreateChildRequest.getTimeOfDay(), habitCreateChildRequest.getDueDate(), habitCreateChildRequest.getEvery(), habitCreateChildRequest.getScheduleType(),
                        habitCreateChildRequest.getHabitTypeName(), habitCreateChildRequest.getDaysOfWeek(),habitCreateChildRequest.getParentHabitName(),habitCreateChildRequest.getActive());
            }
            return new ResponseEntity<>(habit, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Habit> completeHabit(HttpServletRequest request,@RequestParam("id") Long id)
    {
        String username = jwtUtils.getUserNameFromJwtToken(request.getHeader(HEADER_STRING).replace(TOKEN_PREFIX,""));
        Long userId = userProfileRepository.findByName(username).get().getId();
        try {
            Habit habit = habitService.completeHabit(userId,id);
            return new ResponseEntity<>(habit, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/modifyParams")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public void modifyParams(@RequestBody HabitModifyRequest habitModifyRequest)
    {
        habitRepository.modifyParams(habitModifyRequest.getId(),habitModifyRequest.getName(),habitModifyRequest.getStartDate(),habitModifyRequest.getDescription(),
                habitModifyRequest.getActive(),habitModifyRequest.getHidden(),habitModifyRequest.getCompleted(),habitModifyRequest.getDueDate(),
                habitModifyRequest.getTimeTaken(),habitModifyRequest.getStreak(),habitModifyRequest.getTotalTimes(),habitModifyRequest.getTotalTimeSpent(),
                habitModifyRequest.getTimeOfDay());
    }

    @PatchMapping("/modifyScheduleType")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public void modifyScheduleType(HttpServletRequest request,@RequestBody HabitScheduleRequest habitScheduleRequest)
    {
        String username = jwtUtils.getUserNameFromJwtToken(request.getHeader(HEADER_STRING).replace(TOKEN_PREFIX,""));
        Long userId = userProfileRepository.findByName(username).get().getId();
        habitService.modifySchedule(userId,habitScheduleRequest.getId(),habitScheduleRequest.getScheduleType(),habitScheduleRequest.getEvery(),
                habitScheduleRequest.getDaysOfWeek());
    }


    @DeleteMapping
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public void deleteTransactions(@RequestParam("id") Long id){
        habitRepository.deleteById(id);
    }
}
