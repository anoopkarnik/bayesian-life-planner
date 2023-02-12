package com.bayesiansamaritan.lifeplanner.service.impl;

import com.bayesiansamaritan.lifeplanner.enums.DayOfWeek;
import com.bayesiansamaritan.lifeplanner.model.*;
import com.bayesiansamaritan.lifeplanner.repository.*;
import com.bayesiansamaritan.lifeplanner.response.BadHabitResponse;
import com.bayesiansamaritan.lifeplanner.response.HabitResponse;
import com.bayesiansamaritan.lifeplanner.service.BadHabitService;
import com.bayesiansamaritan.lifeplanner.service.HabitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BadHabitServiceImpl implements BadHabitService {

    @Autowired
    BadHabitRepository habitRepository;

    @Autowired
    HabitTypeRepository habitTypeRepository;
    @Autowired
    private BadHabitTransactionRepository habitTransactionRepository;

    @Override
    public List<BadHabitResponse> getAllActiveBadHabits(Long userId, Boolean active, String habitTypeName){

        HabitType habitType = habitTypeRepository.findByNameAndUserId(habitTypeName,userId);
        List<BadHabit> oldHabits = habitRepository.findByUserIdAndActiveAndHabitTypeId(userId,active,habitType.getId());
        Date date = new Date();
        List<BadHabit> habits = habitRepository.findByUserIdAndActiveAndHabitTypeId(userId,active,habitType.getId());
        List<BadHabitResponse> habitResponses = new ArrayList<>();
        for (BadHabit habit: habits){
            BadHabitResponse habitResponse = new BadHabitResponse(habit.getId(),habit.getCreatedAt(),habit.getUpdatedAt(),habit.getName(),
                    habit.getStartDate(),habitTypeName,habit.getTotalTimes(),habit.getDescription());
            habitResponses.add(habitResponse);
        }
        return habitResponses;
    };

    @Override
    public BadHabit createBadHabit(Long userId, String name,Date startDate, String habitTypeName){
        HabitType habitType = habitTypeRepository.findByNameAndUserId(habitTypeName,userId);
        Boolean active = true;
        Long totalTimes = 0L;
        BadHabit habit = habitRepository.save(new BadHabit(name, startDate, habitType.getId(), active, userId, totalTimes));
        return habit;
    };

    @Override
    public BadHabit carriedOutBadHabit(Long userId, Long id){
        BadHabit oldHabit = habitRepository.findByUserIdAndId(userId,id);
        habitRepository.carriedOutBadHabit(oldHabit.getId());
        BadHabit habit = habitRepository.findByUserIdAndId(userId,id);
        habitTransactionRepository.save(new BadHabitTransaction(habit.getName(),habit.getStartDate(), habit.getHabitTypeId(),
                userId,habit.getTotalTimes(),habit.getDescription()));
        return habit;
    };
}
