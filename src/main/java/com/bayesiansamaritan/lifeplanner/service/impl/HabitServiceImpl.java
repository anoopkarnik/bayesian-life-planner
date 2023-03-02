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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bayesiansamaritan.lifeplanner.enums.DayOfWeek;

import java.text.SimpleDateFormat;
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
    public List<HabitResponse> getAllHabits(Long userId, Boolean active, String habitTypeName){

        HabitType habitType = habitTypeRepository.findByNameAndUserId(habitTypeName,userId);
        List<Habit> oldHabits = habitRepository.findRootHabitByUserIdAndActiveAndHabitTypeId(userId,active,habitType.getId());
        List<Habit> habits = removeRootStreak(oldHabits);
        List<HabitResponse> habitResponses = new ArrayList<>();
        for (Habit habit: habits){
            Optional<List<Habit>> oldChildHabits1 =  habitRepository.findChildHabitsByUserIdAndActiveAndParentHabitId(userId,true,habit.getId());
            HabitResponse habitResponse = new HabitResponse(habit.getId(),habit.getCreatedAt(),
                    habit.getUpdatedAt(),habit.getName(),habit.getScheduleType(),habit.getTimeTaken(),habit.getStartDate(),
                    habit.getDueDate(),habitType.getName(),habit.getDescription(),habit.getStreak(),habit.getTotalTimes(),
                    habit.getActive(),habit.getHidden(),habit.getCompleted());
            List<HabitResponse> childHabitResponses1 = new ArrayList<>();
            if (oldChildHabits1.get().size()>0){
                List<Habit> childHabits1 = removeChildStreak(oldChildHabits1.get());
                for(Habit childHabit1 : childHabits1) {
                    Optional<HabitType> childHabitType1 = habitTypeRepository.findById(childHabit1.getHabitTypeId());
                    HabitResponse childHabitResponse1 = new HabitResponse(childHabit1.getId(),childHabit1.getCreatedAt(),
                            childHabit1.getUpdatedAt(),childHabit1.getName(),childHabit1.getScheduleType(),childHabit1.getTimeTaken(),
                            childHabit1.getStartDate(),childHabit1.getDueDate(),childHabitType1.get().getName(),
                            childHabit1.getDescription(),childHabit1.getStreak(),childHabit1.getTotalTimes(),
                            childHabit1.getActive(),childHabit1.getHidden(),childHabit1.getCompleted());
                    childHabitResponses1.add(childHabitResponse1);
                }
                habitResponse.setHabitResponses(childHabitResponses1);
            }
            else{
                habitResponse.setHabitResponses(childHabitResponses1);
            }
            habitResponses.add(habitResponse);
        }
        return habitResponses;
    };

    @Override
    public List<HabitResponse> getAllHabitsAndSubHabits(Long userId, Boolean active, String habitTypeName){

        HabitType habitType = habitTypeRepository.findByNameAndUserId(habitTypeName,userId);
        List<Habit> oldHabits = habitRepository.findRootHabitByUserIdAndActiveAndHabitTypeId(userId,active,habitType.getId());
        List<Habit> habits = removeRootStreak(oldHabits);
        List<HabitResponse> habitResponses = new ArrayList<>();
        for (Habit habit: habits){
            Optional<List<Habit>> oldChildHabits1 =  habitRepository.findChildHabitsByUserIdAndActiveAndParentHabitId(userId,true,habit.getId());
            HabitResponse habitResponse = new HabitResponse(habit.getId(),habit.getCreatedAt(),
                    habit.getUpdatedAt(),habit.getName(),habit.getScheduleType(),habit.getTimeTaken(),habit.getStartDate(),
                    habit.getDueDate(),habitType.getName(),habit.getDescription(),habit.getStreak(),habit.getTotalTimes(),
                    habit.getActive(),habit.getHidden(),habit.getCompleted());
            if (oldChildHabits1.get().size()>0){
                List<Habit> childHabits1 = removeChildStreak(oldChildHabits1.get());
                for(Habit childHabit1 : childHabits1) {
                    Optional<HabitType> childHabitType1 = habitTypeRepository.findById(childHabit1.getHabitTypeId());
                    HabitResponse childHabitResponse1 = new HabitResponse(childHabit1.getId(),childHabit1.getCreatedAt(),
                            childHabit1.getUpdatedAt(),childHabit1.getName(),childHabit1.getScheduleType(),childHabit1.getTimeTaken(),
                            childHabit1.getStartDate(),childHabit1.getDueDate(),childHabitType1.get().getName(),
                            childHabit1.getDescription(),childHabit1.getStreak(),childHabit1.getTotalTimes(),
                            childHabit1.getActive(),childHabit1.getHidden(),childHabit1.getCompleted());
                    habitResponses.add(childHabitResponse1);
                }

            }
            habitResponses.add(habitResponse);
        }
        return habitResponses;
    };

    @Override
    public Habit createDailyRootHabit(Long userId, String name,Date startDate,  Long timeTaken, Date dueDate, Long every, String scheduleType,
                                  String habitTypeName, Boolean active){
        HabitType habitType = habitTypeRepository.findByNameAndUserId(habitTypeName,userId);
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
    public Habit createDailyChildHabit(Long userId, String name,Date startDate,  Long timeTaken, Date dueDate, Long every, String scheduleType,
                                  String habitTypeName,String parentName, Boolean active){
        HabitType habitType = habitTypeRepository.findByNameAndUserId(habitTypeName,userId);
        Habit oldHabit = habitRepository.findByUserIdAndName(userId,parentName);
        Long streak = 0L;
        Long totalTimes = 0L;
        Long time = dueDate.getTime();
        Date newDueDate = new Date(time + 1000*60*60*18 - time%(24*60*60*1000));
        Habit habit = habitRepository.save(new Habit(name, timeTaken, startDate, newDueDate, habitType.getId(), active, userId, streak,
                totalTimes,scheduleType, oldHabit.getId()));
        Daily daily = dailyRepository.save(new Daily(every,"habit/"+habit.getId()));
        return habit;
    };

    @Override
    public Habit createWeeklyRootHabit(Long userId, String name, Date startDate, Long timeTaken, Date dueDate, Long every, String scheduleType,
                                   String habitTypeName, List<DayOfWeek> daysOfWeek, Boolean active){
        HabitType habitType = habitTypeRepository.findByNameAndUserId(habitTypeName,userId);
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
    public Habit createWeeklyChildHabit(Long userId, String name, Date startDate, Long timeTaken, Date dueDate, Long every, String scheduleType,
                                   String habitTypeName, List<DayOfWeek> daysOfWeek,String parentName, Boolean active){
        HabitType habitType = habitTypeRepository.findByNameAndUserId(habitTypeName,userId);
        Habit oldHabit = habitRepository.findByUserIdAndName(userId,parentName);
        Long streak = 0L;
        Long totalTimes = 0L;
        Long time = dueDate.getTime();
        Date newDueDate = new Date(time + 1000*60*60*18 - time%(24*60*60*1000));
        Habit habit = habitRepository.save(new Habit(name, timeTaken, startDate, newDueDate, habitType.getId(), active, userId, streak,
                totalTimes,scheduleType, oldHabit.getId()));
        Weekly weekly = weeklyRepository.save(new Weekly(every,"habit/"+habit.getId(),daysOfWeek));
        return habit;
    };


    @Override
    public Habit createMonthlyRootHabit(Long userId, String name,Date startDate,  Long timeTaken, Date dueDate, Long every, String scheduleType,
                                  String habitTypeName, Boolean active){
        HabitType habitType = habitTypeRepository.findByNameAndUserId(habitTypeName,userId);
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
    public Habit createMonthlyChildHabit(Long userId, String name,Date startDate,  Long timeTaken, Date dueDate, Long every, String scheduleType,
                                    String habitTypeName,String parentName, Boolean active){
        HabitType habitType = habitTypeRepository.findByNameAndUserId(habitTypeName,userId);
        Habit oldHabit = habitRepository.findByUserIdAndName(userId,parentName);
        Long streak = 0L;
        Long totalTimes = 0L;
        Long time = dueDate.getTime();
        Date newDueDate = new Date(time + 1000*60*60*18 - time%(24*60*60*1000));
        Habit habit = habitRepository.save(new Habit(name, timeTaken, startDate, newDueDate, habitType.getId(), active, userId, streak,
                totalTimes,scheduleType, oldHabit.getId()));
        Monthly monthly = monthlyRepository.save(new Monthly(every,"habit/"+habit.getId()));
        return habit;
    };

    @Override
    public Habit createYearlyRootHabit(Long userId, String name,Date startDate,  Long timeTaken, Date dueDate, Long every, String scheduleType,
                                  String habitTypeName, Boolean active){
        HabitType habitType = habitTypeRepository.findByNameAndUserId(habitTypeName,userId);
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
    public Habit createYearlyChildHabit(Long userId, String name,Date startDate,  Long timeTaken, Date dueDate, Long every, String scheduleType,
                                   String habitTypeName,String parentName, Boolean active){
        HabitType habitType = habitTypeRepository.findByNameAndUserId(habitTypeName,userId);
        Habit oldHabit = habitRepository.findByUserIdAndName(userId,parentName);
        Long streak = 0L;
        Long totalTimes = 0L;
        Long time = dueDate.getTime();
        Date newDueDate = new Date(time + 1000*60*60*18 - time%(24*60*60*1000));
        Habit habit = habitRepository.save(new Habit(name, timeTaken, startDate, newDueDate, habitType.getId(), active, userId, streak,
                totalTimes,scheduleType, oldHabit.getId()));
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
                habit.getHabitTypeId(),userId,habit.getStreak(),habit.getTotalTimes(),habit.getScheduleType(),habit.getId()));
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
                    localDueDate = localDueDate.plusDays(7L * (weekly.getEvery()-1));
                }
                localDueDate = localDueDate.plusDays(1L);
                for(DayOfWeek dayOfWeek1:daysOfWeek) {
                    if (dayOfWeek1.name().equals(localDueDate.getDayOfWeek().name())) {
                        match = true;
                    }
                }
            }
            Date newDueDate = Date.from(localDueDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            Date newDueDate1 = new Date(newDueDate.getTime()+ 1000*60*60*23);
            habitRepository.modifyDueDate(habit.getId(),newDueDate1);
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

    List<Habit> removeRootStreak(List<Habit> habits){
        Date date = new Date();
        for (Habit habit : habits){
            if (habit.getDueDate().compareTo(date)<0){
                habitRepository.removeStreak(habit.getId());
            }
        }
        List<Habit> newHabits = habitRepository.findRootHabitByUserIdAndActiveAndHabitTypeId(habits.get(0).getUserId(),
                habits.get(0).getActive(),habits.get(0).getHabitTypeId());
        return newHabits;
    }

    List<Habit> removeChildStreak(List<Habit> habits){
        Date date = new Date();
        for (Habit habit : habits){
            if (habit.getDueDate().compareTo(date)<0){
                habitRepository.removeStreak(habit.getId());
            }
        }
        Optional<List<Habit>> newHabits = habitRepository.findChildHabitsByUserIdAndActiveAndParentHabitId(habits.get(0).getUserId(),
                habits.get(0).getActive(),habits.get(0).getParentId());
        return newHabits.get();
    }

}
