package com.bayesiansamaritan.lifeplanner.service.impl;

import com.bayesiansamaritan.lifeplanner.model.*;
import com.bayesiansamaritan.lifeplanner.model.Habit;
import com.bayesiansamaritan.lifeplanner.repository.*;
import com.bayesiansamaritan.lifeplanner.response.HabitResponse;
import com.bayesiansamaritan.lifeplanner.service.HabitService;
import com.bayesiansamaritan.lifeplanner.service.HabitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bayesiansamaritan.lifeplanner.enums.DayOfWeek;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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

    @Override
    public List<HabitResponse> getAllActiveHabits(Long userId, Boolean active, String habitTypeName){

        HabitType habitType = habitTypeRepository.findByNameAndUserId(habitTypeName,userId);
        List<Habit> oldHabits = habitRepository.findByUserIdAndActiveAndHabitTypeId(userId,active,habitType.getId());
        Date date = new Date();
        for (Habit habit : oldHabits){
            if (habit.getDueDate().compareTo(date)<0){
                habitRepository.removeStreak(habit.getId());
            }
        }

        List<Habit> habits = habitRepository.findByUserIdAndActiveAndHabitTypeId(userId,active,habitType.getId());
        List<HabitResponse> habitResponses = new ArrayList<>();
        for (Habit habit: habits){
            HabitResponse habitResponse = new HabitResponse(habit.getId(),habit.getCreatedAt(),habit.getUpdatedAt(),habit.getName(),
                    habit.getScheduleType(),habit.getTimeTaken(),habit.getStartDate(),habit.getDueDate(),habitTypeName,habit.getDescription(),
                    habit.getStreak(),habit.getTotalTimes());
            habitResponses.add(habitResponse);
        }
        return habitResponses;
    };

    @Override
    public Habit createDailyHabit(Long userId, String name,Date startDate,  Long timeTaken, Date dueDate, Long every, String scheduleType,
                                  String habitTypeName){
        HabitType habitType = habitTypeRepository.findByNameAndUserId(habitTypeName,userId);
        Boolean active = true;
        Long streak = 0L;
        Long totalTimes = 0L;
        Long time = dueDate.getTime();
        Date newDueDate = new Date(time + 1000*60*60*18 - time%(24*60*60*1000));
        Habit habit = habitRepository.save(new Habit(name, timeTaken, startDate, newDueDate, habitType.getId(), active, userId, streak,
                totalTimes,scheduleType));
        Daily daily = dailyRepository.save(new Daily(every,"habit/"+habit.getId()));
        return habit;
    };

    @Override
    public Habit createWeeklyHabit(Long userId, String name, Date startDate, Long timeTaken, Date dueDate, Long every, String scheduleType,
                                   String habitTypeName, List<DayOfWeek> daysOfWeek){
        HabitType habitType = habitTypeRepository.findByNameAndUserId(habitTypeName,userId);
        Boolean active = true;
        Long streak = 0L;
        Long totalTimes = 0L;
        Long time = dueDate.getTime();
        Date newDueDate = new Date(time + 1000*60*60*18 - time%(24*60*60*1000));
        Habit habit = habitRepository.save(new Habit(name, timeTaken, startDate, newDueDate, habitType.getId(), active, userId, streak,
                totalTimes,scheduleType));
        Weekly weekly = weeklyRepository.save(new Weekly(every,"habit/"+habit.getId(),daysOfWeek));
        return habit;
    };

    @Override
    public Habit createMonthlyHabit(Long userId, String name,Date startDate,  Long timeTaken, Date dueDate, Long every, String scheduleType,
                                  String habitTypeName){
        HabitType habitType = habitTypeRepository.findByNameAndUserId(habitTypeName,userId);
        Boolean active = true;
        Long streak = 0L;
        Long totalTimes = 0L;
        Long time = dueDate.getTime();
        Date newDueDate = new Date(time + 1000*60*60*18 - time%(24*60*60*1000));
        Habit habit = habitRepository.save(new Habit(name, timeTaken, startDate, newDueDate, habitType.getId(), active, userId, streak,
                totalTimes,scheduleType));
        Monthly monthly = monthlyRepository.save(new Monthly(every,"habit/"+habit.getId()));
        return habit;
    };

    @Override
    public Habit createYearlyHabit(Long userId, String name,Date startDate,  Long timeTaken, Date dueDate, Long every, String scheduleType,
                                  String habitTypeName){
        HabitType habitType = habitTypeRepository.findByNameAndUserId(habitTypeName,userId);
        Boolean active = true;
        Long streak = 0L;
        Long totalTimes = 0L;
        Long time = dueDate.getTime();
        Date newDueDate = new Date(time + 1000*60*60*18 - time%(24*60*60*1000));
        Habit habit = habitRepository.save(new Habit(name, timeTaken, startDate, newDueDate, habitType.getId(), active, userId, streak,
                totalTimes,scheduleType));
        Yearly yearly = yearlyRepository.save(new Yearly(every,"habit/"+habit.getId()));
        return habit;
    };

    @Override
    public Habit completeHabit(Long userId, Long id){
        Habit oldHabit = habitRepository.findByUserIdAndId(userId,id);
        String scheduleTypeName = oldHabit .getScheduleType();
        Date dueDate = oldHabit.getDueDate();
        habitRepository.completeHabit(oldHabit.getId());
        Habit habit = habitRepository.findByUserIdAndId(userId,id);
        habitTransactionRepository.save(new HabitTransaction(habit.getName(),habit.getTimeTaken(),habit.getStartDate(),dueDate,
                habit.getHabitTypeId(),userId,habit.getStreak(),habit.getTotalTimes(),habit.getScheduleType()));
        Boolean active = true;
        if (scheduleTypeName.equals("daily")){
            String referenceId = "habit/"+habit.getId();
            Daily daily = dailyRepository.findByReferenceId(referenceId);
            Long time = dueDate.getTime();
            Date newDueDate1 = new Date(time + 1000*60*60*18 - time%(24*60*60*1000));
            Date newDueDate = new Date(newDueDate1.getTime() + (1000*60*60*24*daily.getEvery()));
            Date currentDate = new Date();
            if (newDueDate.compareTo(currentDate) <0){
                Date newDueDate2 = new Date(currentDate.getTime() + 1000*60*60*18 - currentDate.getTime()%(24*60*60*1000) + (1000*60*60*24*daily.getEvery()));
                habitRepository.modifyDueDate(habit.getId(),newDueDate2);
            }
            else{
                habitRepository.modifyDueDate(habit.getId(),newDueDate);
            }

        }
        else if(scheduleTypeName.equals("weekly")) {
            String referenceId = "habit/" + habit.getId();
            Weekly weekly = weeklyRepository.findByReferenceId(referenceId);
            List<DayOfWeek> daysOfWeek = weekly.getDaysOfWeek();
            LocalDate localDueDate = LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(dueDate));
            Boolean match = false;
            Long current = 1L;
            while (!match) {
                DayOfWeek dayOfWeek = DayOfWeek.valueOf(localDueDate.getDayOfWeek().toString());
                if (dayOfWeek.equals(dayOfWeek.SUNDAY)) {
                    localDueDate = localDueDate.plusDays(7L * weekly.getEvery());
                }
                localDueDate = localDueDate.plusDays(1L);
                for(DayOfWeek dayOfWeek1:daysOfWeek) {
                    if (dayOfWeek1.name().equals(localDueDate.getDayOfWeek().name())) {
                        match = true;
                    }
                }
            }
            Date newDueDate = Date.from(localDueDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            habitRepository.modifyDueDate(habit.getId(),newDueDate);
        }
        else if(scheduleTypeName.equals("monthly")) {
            LocalDate localDueDate = LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(dueDate));
            String referenceId = "habit/" + habit.getId();
            Monthly monthly = monthlyRepository.findByReferenceId(referenceId);
            LocalDate newLocalDueDate = localDueDate.plusMonths(monthly.getEvery());
            Date newDueDate = Date.from(newLocalDueDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            habitRepository.modifyDueDate(habit.getId(),newDueDate);
        }
        else if(scheduleTypeName.equals("yearly")){
            LocalDate localDueDate = LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(dueDate));
            String referenceId = "habit/"+habit.getId();
            Yearly yearly = yearlyRepository.findByReferenceId(referenceId);
            LocalDate newLocalDueDate = localDueDate.plusYears(1L*yearly.getEvery());
            Date newDueDate = Date.from(newLocalDueDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            habitRepository.modifyDueDate(habit.getId(),newDueDate);
        }
        return habit;
    };
}
