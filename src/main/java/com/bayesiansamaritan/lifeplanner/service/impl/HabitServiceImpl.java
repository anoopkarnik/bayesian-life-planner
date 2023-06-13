package com.bayesiansamaritan.lifeplanner.service.impl;

import com.bayesiansamaritan.lifeplanner.model.Habit.Habit;
import com.bayesiansamaritan.lifeplanner.model.Habit.HabitType;
import com.bayesiansamaritan.lifeplanner.model.Habit.HabitTransaction;
import com.bayesiansamaritan.lifeplanner.model.Scheduler.Daily;
import com.bayesiansamaritan.lifeplanner.model.Scheduler.Monthly;
import com.bayesiansamaritan.lifeplanner.model.Scheduler.Weekly;
import com.bayesiansamaritan.lifeplanner.model.Scheduler.Yearly;
import com.bayesiansamaritan.lifeplanner.repository.Habit.HabitRepository;
import com.bayesiansamaritan.lifeplanner.repository.Habit.HabitTransactionRepository;
import com.bayesiansamaritan.lifeplanner.repository.Habit.HabitTypeRepository;
import com.bayesiansamaritan.lifeplanner.repository.Scheduler.DailyRepository;
import com.bayesiansamaritan.lifeplanner.repository.Scheduler.MonthlyRepository;
import com.bayesiansamaritan.lifeplanner.repository.Scheduler.WeeklyRepository;
import com.bayesiansamaritan.lifeplanner.repository.Scheduler.YearlyRepository;
import com.bayesiansamaritan.lifeplanner.response.HabitResponse;
import com.bayesiansamaritan.lifeplanner.service.HabitService;
import com.bayesiansamaritan.lifeplanner.utils.HabitDateUtils;
import com.bayesiansamaritan.lifeplanner.utils.ModelToResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bayesiansamaritan.lifeplanner.enums.DayOfWeek;

import java.util.Date;
import java.util.List;

@Service
public class HabitServiceImpl implements HabitService {

    @Autowired
    HabitRepository habitRepository;

    @Autowired
    HabitTypeRepository habitTypeRepository;
    @Autowired
    private DailyRepository dailyRepository;

    @Autowired
    private WeeklyRepository weeklyRepository;

    @Autowired
    private MonthlyRepository monthlyRepository;

    @Autowired
    private YearlyRepository yearlyRepository;
    @Autowired
    private HabitTransactionRepository habitTransactionRepository;

    @Autowired
    private HabitDateUtils habitDateUtils;
    @Autowired
    private ModelToResponse modelToResponse;

    @Override
    public List<HabitResponse> getAllHabits(Long userId, Boolean active, String habitTypeName,Date currentDate){

        HabitType habitType = habitTypeRepository.findByNameAndUserId(habitTypeName,userId);
        List<Habit> habits = habitRepository.findHabitByUserIdAndActiveAndHabitTypeId(userId,active,habitType.getId(),currentDate);
        List<HabitResponse> habitResponses = modelToResponse.habitsModelsToResponses(habits,habitType);
        return habitResponses;
    };



    @Override
    public Habit createDailyHabit(Long userId, String name,Date startDate,  Long timeOfDay, Date dueDate, Long every, String scheduleType,
                                  String habitTypeName, Boolean active){
        HabitType habitType = habitTypeRepository.findByNameAndUserId(habitTypeName,userId);
        Long streak = 0L;
        Long totalTimes = 0L;
        Date newDueDate = habitDateUtils.getEndOfDay(dueDate);
        Habit habit = habitRepository.save(new Habit(name, timeOfDay, startDate, newDueDate, habitType.getId(), active, userId, streak,
                totalTimes,scheduleType));
        Daily daily = dailyRepository.save(new Daily(every,"habit/"+habit.getId()));
        return habit;
    };

    @Override
    public Habit createWeeklyHabit(Long userId, String name, Date startDate, Long timeOfDay, Date dueDate, Long every, String scheduleType,
                                   String habitTypeName, List<DayOfWeek> daysOfWeek, Boolean active){
        HabitType habitType = habitTypeRepository.findByNameAndUserId(habitTypeName,userId);
        Long streak = 0L;
        Long totalTimes = 0L;
        Date newDueDate = habitDateUtils.getEndOfDay(dueDate);
        Habit habit = habitRepository.save(new Habit(name, timeOfDay, startDate, newDueDate, habitType.getId(), active, userId, streak,
                totalTimes,scheduleType));
        Weekly weekly = weeklyRepository.save(new Weekly(every,"habit/"+habit.getId(),daysOfWeek));
        return habit;
    };

    @Override
    public Habit createMonthlyHabit(Long userId, String name,Date startDate,  Long timeOfDay, Date dueDate, Long every, String scheduleType,
                                  String habitTypeName, Boolean active){
        HabitType habitType = habitTypeRepository.findByNameAndUserId(habitTypeName,userId);
        Long streak = 0L;
        Long totalTimes = 0L;
        Date newDueDate = habitDateUtils.getEndOfDay(dueDate);
        Habit habit = habitRepository.save(new Habit(name, timeOfDay, startDate, newDueDate, habitType.getId(), active, userId, streak,
                totalTimes,scheduleType));
        Monthly monthly = monthlyRepository.save(new Monthly(every,"habit/"+habit.getId()));
        return habit;
    };

    @Override
    public Habit createYearlyHabit(Long userId, String name,Date startDate,  Long timeOfDay, Date dueDate, Long every, String scheduleType,
                                  String habitTypeName, Boolean active){
        HabitType habitType = habitTypeRepository.findByNameAndUserId(habitTypeName,userId);
        Long streak = 0L;
        Long totalTimes = 0L;
        Date newDueDate = habitDateUtils.getEndOfDay(dueDate);
        Habit habit = habitRepository.save(new Habit(name, timeOfDay, startDate, newDueDate, habitType.getId(), active, userId, streak,
                totalTimes,scheduleType));
        Yearly yearly = yearlyRepository.save(new Yearly(every,"habit/"+habit.getId()));
        return habit;
    };

    @Override
    public Habit completeHabit(Long userId, Long id, String habitCompletionType, Date currentDate){
        Habit oldHabit = habitRepository.findByUserIdAndId(userId,id);
        String scheduleTypeName = oldHabit .getScheduleType();
        Date dueDate = oldHabit.getDueDate();
        Habit habit = oldHabit;
        if (habitCompletionType.equals("Complete")){
            habitRepository.completeHabit(oldHabit.getId());
            habit = habitRepository.findByUserIdAndId(userId,id);
            habitTransactionRepository.save(new HabitTransaction(habit.getName(),habit.getTimeTaken(),habit.getStartDate(),currentDate,
                    habit.getHabitTypeId(),userId,habit.getStreak(),habit.getTotalTimes(),habit.getScheduleType(),habit.getId(),habit.getTimeOfDay(),habitCompletionType));
        }
        else if (habitCompletionType.equals("Atomic")){
            habit = habitRepository.findByUserIdAndId(userId,id);
            habitTransactionRepository.save(new HabitTransaction(habit.getName(),habit.getTimeTaken(),habit.getStartDate(),currentDate,
                    habit.getHabitTypeId(),userId,habit.getStreak(),habit.getTotalTimes(),habit.getScheduleType(),habit.getId(),habit.getTimeOfDay(),habitCompletionType));
        }
        else if (habitCompletionType.equals("Condition")){
            habit = habitRepository.findByUserIdAndId(userId,id);
            habitTransactionRepository.save(new HabitTransaction(habit.getName(),habit.getTimeTaken(),habit.getStartDate(),currentDate,
                    habit.getHabitTypeId(),userId,habit.getStreak(),habit.getTotalTimes(),habit.getScheduleType(),habit.getId(),habit.getTimeOfDay(),habitCompletionType));
        }

        String referenceId = "habit/"+habit.getId();
        Date nextDueDate = habitDateUtils.getNextDueDate(dueDate,scheduleTypeName,referenceId,currentDate);
        habitRepository.modifyDueDate(habit.getId(),nextDueDate);
        return habit;
    };

    @Override
    public void modifySchedule(Long userId, Long id,String oldScheduleType,String scheduleType,Long every, List<DayOfWeek> daysOfWeek){
        Habit habit = habitRepository.findByUserIdAndId(userId,id);
        if(oldScheduleType.equals("daily")){
            Daily daily = dailyRepository.findByReferenceId("habit/"+habit.getId());
            dailyRepository.deleteById(daily.getId());
        }
        else if(oldScheduleType.equals("monthly")){
            Monthly monthly = monthlyRepository.findByReferenceId("habit/"+habit.getId());
            monthlyRepository.deleteById(monthly.getId());
        }
        else if(oldScheduleType.equals("yearly")){
            Yearly yearly = yearlyRepository.findByReferenceId("habit/"+habit.getId());
            yearlyRepository.deleteById(yearly.getId());
        }
        else if(oldScheduleType.equals("weekly")){
            Weekly weekly = weeklyRepository.findByReferenceId("habit/"+habit.getId());
            weeklyRepository.deleteById(weekly.getId());
        }

        if(scheduleType.equals("daily")){
            dailyRepository.save(new Daily(every,"habit/"+habit.getId()));
        }
        else if(scheduleType.equals("monthly")){
            monthlyRepository.save(new Monthly(every,"habit/"+habit.getId()));
        }
        else if(scheduleType.equals("yearly")){
            yearlyRepository.save(new Yearly(every,"habit/"+habit.getId()));
        }
        else if(scheduleType.equals("weekly")){
            weeklyRepository.save(new Weekly(every,"habit/"+habit.getId(),daysOfWeek));
        }
        habitRepository.modifyScheduleType(id,scheduleType);
    }

}
