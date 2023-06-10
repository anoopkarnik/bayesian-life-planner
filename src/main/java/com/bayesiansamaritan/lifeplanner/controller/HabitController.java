package com.bayesiansamaritan.lifeplanner.controller;


import com.bayesiansamaritan.lifeplanner.enums.DayOfWeek;
import com.bayesiansamaritan.lifeplanner.model.Habit.Habit;
import com.bayesiansamaritan.lifeplanner.model.Habit.HabitType;
import com.bayesiansamaritan.lifeplanner.model.Scheduler.Daily;
import com.bayesiansamaritan.lifeplanner.model.Scheduler.Monthly;
import com.bayesiansamaritan.lifeplanner.model.Scheduler.Weekly;
import com.bayesiansamaritan.lifeplanner.model.Scheduler.Yearly;
import com.bayesiansamaritan.lifeplanner.repository.Habit.HabitRepository;
import com.bayesiansamaritan.lifeplanner.repository.Habit.HabitTypeRepository;
import com.bayesiansamaritan.lifeplanner.repository.Scheduler.DailyRepository;
import com.bayesiansamaritan.lifeplanner.repository.Scheduler.MonthlyRepository;
import com.bayesiansamaritan.lifeplanner.repository.Scheduler.WeeklyRepository;
import com.bayesiansamaritan.lifeplanner.repository.Scheduler.YearlyRepository;
import com.bayesiansamaritan.lifeplanner.repository.User.UserProfileRepository;
import com.bayesiansamaritan.lifeplanner.request.Habit.HabitCreateChildRequest;
import com.bayesiansamaritan.lifeplanner.request.Habit.HabitCreateRootRequest;
import com.bayesiansamaritan.lifeplanner.request.Habit.HabitModifyRequest;
import com.bayesiansamaritan.lifeplanner.request.Habit.HabitScheduleRequest;
import com.bayesiansamaritan.lifeplanner.response.HabitResponse;
import com.bayesiansamaritan.lifeplanner.security.jwt.JwtUtils;
import com.bayesiansamaritan.lifeplanner.service.HabitService;
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
@RequestMapping("/api/habit")
public class HabitController {
    @Autowired
    private HabitRepository habitRepository;
    @Autowired
    private HabitTypeRepository habitTypeRepository;

    @Autowired
    HabitService habitService;

    @Autowired
    private UserProfileRepository userProfileRepository;
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    DateUtils dateUtils;
    @Autowired
    DailyRepository dailyRepository;
    @Autowired
    WeeklyRepository weeklyRepository;
    @Autowired
    MonthlyRepository monthlyRepository;
    @Autowired
    YearlyRepository yearlyRepository;
    static final String HEADER_STRING = "Authorization";
    static final String TOKEN_PREFIX = "Bearer";

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public HabitResponse getHabit(@PathVariable("id") Long id) {
        Habit habit = habitRepository.findById(id).get();
        HabitType habitType = habitTypeRepository.findById(habit.getHabitTypeId()).get();
        Long every = null;
        List<String> daysOfWeek = new ArrayList<>();
        if (habit.getScheduleType().equals("daily")) {
            Daily daily = dailyRepository.findByReferenceId("habit/" + habit.getId());
            every = daily.getEvery();
        }
        else if(habit.getScheduleType().equals("monthly")){
            Monthly monthly = monthlyRepository.findByReferenceId("habit/"+habit.getId());
            every = monthly.getEvery();
        }
        else if(habit.getScheduleType().equals("yearly")){
            Yearly yearly = yearlyRepository.findByReferenceId("habit/"+habit.getId());
            every = yearly.getEvery();
        }
        else if(habit.getScheduleType().equals("weekly")){
            Weekly weekly = weeklyRepository.findByReferenceId("habit/"+habit.getId());
            every = weekly.getEvery();
            for (DayOfWeek dayOfWeek:weekly.getDaysOfWeek()){
                daysOfWeek.add(dayOfWeek.toString());
            }
        }
        HabitResponse habitResponse = new HabitResponse(habit.getId(),habit.getCreatedAt(),habit.getUpdatedAt(),
                habit.getName(),habit.getScheduleType(),habit.getTimeTaken(),habit.getStartDate(),
                habit.getDueDate(),habitType.getName(),habit.getDescription(),habit.getStreak(),habit.getTotalTimes(),
                habit.getActive(),habit.getHidden(),habit.getCompleted(),habit.getTimeOfDay(),every,daysOfWeek);
        return habitResponse;
    }


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
    public ResponseEntity<Habit> completeHabit(HttpServletRequest request,@RequestParam("id") Long id,@RequestParam("completion") String completionType)
    {
        String username = jwtUtils.getUserNameFromJwtToken(request.getHeader(HEADER_STRING).replace(TOKEN_PREFIX,""));
        Long userId = userProfileRepository.findByName(username).get().getId();
        try {
            Habit habit = habitService.completeHabit(userId,id,completionType);
            return new ResponseEntity<>(habit, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/modifyParams")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public void modifyParams(@RequestBody HabitModifyRequest habitModifyRequest)
    {
        Date newDueDate = dateUtils.getEndOfDay(habitModifyRequest.getDueDate());
        habitRepository.modifyParams(habitModifyRequest.getId(),habitModifyRequest.getName(),habitModifyRequest.getStartDate(),habitModifyRequest.getDescription(),
                habitModifyRequest.getActive(),habitModifyRequest.getHidden(),habitModifyRequest.getCompleted(),newDueDate,
                habitModifyRequest.getTimeTaken(),habitModifyRequest.getStreak(),habitModifyRequest.getTotalTimes(),habitModifyRequest.getTotalTimeSpent(),
                habitModifyRequest.getTimeOfDay());
    }

    @PatchMapping("/modifyScheduleType")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public void modifyScheduleType(HttpServletRequest request,@RequestBody HabitScheduleRequest habitScheduleRequest)
    {
        String username = jwtUtils.getUserNameFromJwtToken(request.getHeader(HEADER_STRING).replace(TOKEN_PREFIX,""));
        Long userId = userProfileRepository.findByName(username).get().getId();
        habitService.modifySchedule(userId,habitScheduleRequest.getId(),habitScheduleRequest.getOldScheduleType(),habitScheduleRequest.getScheduleType(),habitScheduleRequest.getEvery(),
                habitScheduleRequest.getDaysOfWeek());
    }


    @DeleteMapping
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public void deleteTransactions(@RequestParam("id") Long id){
        habitRepository.deleteById(id);
    }
}
