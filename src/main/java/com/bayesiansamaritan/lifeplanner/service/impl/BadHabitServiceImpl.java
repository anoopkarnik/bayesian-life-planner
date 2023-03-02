package com.bayesiansamaritan.lifeplanner.service.impl;

import com.bayesiansamaritan.lifeplanner.model.BadHabit.BadHabit;
import com.bayesiansamaritan.lifeplanner.model.BadHabit.BadHabitTransaction;
import com.bayesiansamaritan.lifeplanner.model.Habit.HabitType;
import com.bayesiansamaritan.lifeplanner.model.BadHabit.BadHabit;
import com.bayesiansamaritan.lifeplanner.model.BadHabit.BadHabitType;
import com.bayesiansamaritan.lifeplanner.repository.BadHabit.BadHabitRepository;
import com.bayesiansamaritan.lifeplanner.repository.BadHabit.BadHabitTransactionRepository;
import com.bayesiansamaritan.lifeplanner.repository.BadHabit.BadHabitTypeRepository;
import com.bayesiansamaritan.lifeplanner.repository.Habit.HabitTypeRepository;
import com.bayesiansamaritan.lifeplanner.response.BadHabitResponse;
import com.bayesiansamaritan.lifeplanner.response.BadHabitResponse;
import com.bayesiansamaritan.lifeplanner.service.BadHabitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BadHabitServiceImpl implements BadHabitService {

    @Autowired
    BadHabitRepository badHabitRepository;

    @Autowired
    BadHabitTypeRepository badHabitTypeRepository;
    @Autowired
    private BadHabitTransactionRepository habitTransactionRepository;

    @Override
    public List<BadHabitResponse> getAllBadHabits(Long userId, Boolean active, String badHabitTypeName){

        BadHabitType badHabitType = badHabitTypeRepository.findByNameAndUserId(badHabitTypeName,userId);
        List<BadHabit> badHabits = badHabitRepository.findRootBadHabitsByUserIdAndActiveAndHabitTypeId(userId,active,badHabitType.getId());
        List<BadHabitResponse> badHabitResponses = new ArrayList<>();
        for (BadHabit badHabit: badHabits){
            Optional<List<BadHabit>> childBadHabits1 =  badHabitRepository.findChildBadHabitsByUserIdAndActiveAndParentBadHabitId(userId,
                    true,badHabit.getId());
            BadHabitResponse badHabitResponse = new BadHabitResponse(badHabit.getId(),badHabit.getCreatedAt(),
                    badHabit.getUpdatedAt(),badHabit.getName(),badHabit.getStartDate(),badHabitType.getName(),badHabit.getTotalTimes(),
                    badHabit.getDescription(),badHabit.getActive(),badHabit.getHidden(),badHabit.getCompleted());
            if (childBadHabits1.isPresent()){
                List<BadHabitResponse> childBadHabitResponses1 = new ArrayList<>();
                for(BadHabit childBadHabit1 : childBadHabits1.get()) {
                    Optional<BadHabitType> childBadHabitType1 = badHabitTypeRepository.findById(childBadHabit1.getBadHabitTypeId());
                    BadHabitResponse childBadHabitResponse1 = new BadHabitResponse(childBadHabit1.getId(),childBadHabit1.getCreatedAt(),
                            childBadHabit1.getUpdatedAt(),childBadHabit1.getName(),childBadHabit1.getStartDate(),childBadHabitType1.get().getName(),
                            childBadHabit1.getTotalTimes(),childBadHabit1.getDescription(),childBadHabit1.getActive(),childBadHabit1.getHidden(),
                            childBadHabit1.getCompleted());
                    childBadHabitResponses1.add(childBadHabitResponse1);
                }
                badHabitResponse.setBadHabitResponses(childBadHabitResponses1);
            }
            badHabitResponses.add(badHabitResponse);
        }
        return badHabitResponses;
    };

    @Override
    public List<BadHabitResponse> getAllBadHabitsAndSubBadHabits(Long userId, Boolean active, String badHabitTypeName){

        BadHabitType badHabitType = badHabitTypeRepository.findByNameAndUserId(badHabitTypeName,userId);
        List<BadHabit> badHabits = badHabitRepository.findRootBadHabitsByUserIdAndActiveAndHabitTypeId(userId,active,badHabitType.getId());
        List<BadHabitResponse> badHabitResponses = new ArrayList<>();
        for (BadHabit badHabit: badHabits){
            Optional<List<BadHabit>> childBadHabits1 =  badHabitRepository.findChildBadHabitsByUserIdAndActiveAndParentBadHabitId(userId,
                    active,badHabit.getId());
            BadHabitResponse badHabitResponse = new BadHabitResponse(badHabit.getId(),badHabit.getCreatedAt(),
                    badHabit.getUpdatedAt(),badHabit.getName(),badHabit.getStartDate(),badHabitType.getName(),badHabit.getTotalTimes(),
                    badHabit.getDescription(),badHabit.getActive(),badHabit.getHidden(),badHabit.getCompleted());
            if (childBadHabits1.isPresent()){
                for(BadHabit childBadHabit1 : childBadHabits1.get()) {
                    Optional<BadHabitType> childBadHabitType1 = badHabitTypeRepository.findById(childBadHabit1.getBadHabitTypeId());
                    BadHabitResponse childBadHabitResponse1 = new BadHabitResponse(childBadHabit1.getId(),childBadHabit1.getCreatedAt(),
                            childBadHabit1.getUpdatedAt(),childBadHabit1.getName(),childBadHabit1.getStartDate(),childBadHabitType1.get().getName(),
                            childBadHabit1.getTotalTimes(),childBadHabit1.getDescription(),childBadHabit1.getActive(),childBadHabit1.getHidden(),
                            childBadHabit1.getCompleted());
                    badHabitResponses.add(childBadHabitResponse1);
                }
            }
            badHabitResponses.add(badHabitResponse);
        }
        return badHabitResponses;
    };


    @Override
    public BadHabit createRootBadHabit(Long userId, String name,Date startDate, String badHabitTypeName,Boolean active){
        BadHabitType badHabitType = badHabitTypeRepository.findByNameAndUserId(badHabitTypeName,userId);
        Long totalTimes = 0L;
        BadHabit habit = badHabitRepository.save(new BadHabit(name, startDate, badHabitType.getId(), active, userId, totalTimes));
        return habit;
    };

    @Override
    public BadHabit createChildBadHabit(Long userId, String name,Date startDate, String habitTypeName, String parentName, Boolean active){
        BadHabitType badHabitType = badHabitTypeRepository.findByNameAndUserId(habitTypeName,userId);
        Long totalTimes = 0L;
        BadHabit parentHabit = badHabitRepository.findByUserIdAndName(userId,parentName);
        BadHabit habit = badHabitRepository.save(new BadHabit(name, startDate, badHabitType.getId(), active, userId, totalTimes,parentHabit.getId()));
        return habit;
    };

    @Override
    public BadHabit carriedOutBadHabit(Long userId, Long id){
        BadHabit oldHabit = badHabitRepository.findByUserIdAndId(userId,id);
        badHabitRepository.carriedOutBadHabit(oldHabit.getId());
        BadHabit habit = badHabitRepository.findByUserIdAndId(userId,id);
        habitTransactionRepository.save(new BadHabitTransaction(habit.getId(),habit.getName(),habit.getStartDate(), habit.getBadHabitTypeId(),
                userId,habit.getTotalTimes(),habit.getDescription()));
        return habit;
    };
}
