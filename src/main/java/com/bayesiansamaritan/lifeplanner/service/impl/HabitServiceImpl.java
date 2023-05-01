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
import com.bayesiansamaritan.lifeplanner.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bayesiansamaritan.lifeplanner.enums.DayOfWeek;

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

    @Autowired
    private DateUtils dateUtils;

    @Override
    public List<HabitResponse> getAllHabits(Long userId, Boolean active, String habitTypeName){

        HabitType habitType = habitTypeRepository.findByNameAndUserId(habitTypeName,userId);
        List<Habit> habits = habitRepository.findRootHabitByUserIdAndActiveAndHabitTypeId(userId,active,habitType.getId());
        List<HabitResponse> habitResponses = new ArrayList<>();
        for (Habit habit: habits){
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
            Long streak;
            if (dateUtils.keepStreak(habit.getId(),habit.getScheduleType(),habit.getDueDate())){
                streak = habit.getStreak();
            }
            else{
                streak = 0L;
            }
            Optional<List<Habit>> childHabits1 =  habitRepository.findChildHabitsByUserIdAndActiveAndParentHabitId(userId,active,habit.getId());
            HabitResponse habitResponse = new HabitResponse(habit.getId(),habit.getCreatedAt(),
                    habit.getUpdatedAt(),habit.getName(),habit.getScheduleType(),habit.getTimeTaken(),habit.getStartDate(),
                    habit.getDueDate(),habitType.getName(),habit.getDescription(),streak,habit.getTotalTimes(),
                    habit.getActive(),habit.getHidden(),habit.getCompleted(),habit.getTimeOfDay(),every,daysOfWeek);
            List<HabitResponse> childHabitResponses1 = new ArrayList<>();
            if (childHabits1.get().size()>0){
                for(Habit childHabit1 : childHabits1.get()) {
                    Long childEvery = null;
                    List<String> childDaysOfWeek = new ArrayList<>();
                    if (childHabit1.getScheduleType().equals("daily")) {
                        Daily daily = dailyRepository.findByReferenceId("habit/" + childHabit1.getId());
                        childEvery= daily.getEvery();
                    }
                    else if(childHabit1.getScheduleType().equals("monthly")){
                        Monthly monthly = monthlyRepository.findByReferenceId("habit/"+childHabit1.getId());
                        childEvery= monthly.getEvery();
                    }
                    else if(childHabit1.getScheduleType().equals("yearly")){
                        Yearly yearly = yearlyRepository.findByReferenceId("habit/"+childHabit1.getId());
                        childEvery = yearly.getEvery();
                    }
                    else if(childHabit1.getScheduleType().equals("weekly")){
                        Weekly weekly = weeklyRepository.findByReferenceId("habit/"+childHabit1.getId());
                        childEvery = weekly.getEvery();
                        for (DayOfWeek dayOfWeek:weekly.getDaysOfWeek()){
                            daysOfWeek.add(dayOfWeek.toString());
                        }
                    }
                    Long childStreak;
                    if (dateUtils.keepStreak(childHabit1.getId(),childHabit1.getScheduleType(),childHabit1.getDueDate())){
                        childStreak = childHabit1.getStreak();
                    }
                    else{
                        childStreak = 0L;
                    }
                    Optional<HabitType> childHabitType1 = habitTypeRepository.findById(childHabit1.getHabitTypeId());
                    HabitResponse childHabitResponse1 = new HabitResponse(childHabit1.getId(),childHabit1.getCreatedAt(),
                            childHabit1.getUpdatedAt(),childHabit1.getName(),childHabit1.getScheduleType(),childHabit1.getTimeTaken(),
                            childHabit1.getStartDate(),childHabit1.getDueDate(),childHabitType1.get().getName(),
                            childHabit1.getDescription(),childStreak,childHabit1.getTotalTimes(),
                            childHabit1.getActive(),childHabit1.getHidden(),childHabit1.getCompleted(),habit.getTimeOfDay(),childEvery,childDaysOfWeek);
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
        List<Habit> habits = habitRepository.findRootHabitByUserIdAndActiveAndHabitTypeId(userId,active,habitType.getId());
        List<HabitResponse> habitResponses = new ArrayList<>();
        for (Habit habit: habits) {
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
            Long streak;
            if (dateUtils.keepStreak(habit.getId(),habit.getScheduleType(),habit.getDueDate())){
                streak = habit.getStreak();
            }
            else{
                streak = 0L;
            }
            Optional<List<Habit>> childHabits1 = habitRepository.findChildHabitsByUserIdAndActiveAndParentHabitId(userId, active, habit.getId());
            HabitResponse habitResponse = new HabitResponse(habit.getId(), habit.getCreatedAt(),
                    habit.getUpdatedAt(), habit.getName(), habit.getScheduleType(), habit.getTimeTaken(), habit.getStartDate(),
                    habit.getDueDate(), habitType.getName(), habit.getDescription(),streak, habit.getTotalTimes(),
                    habit.getActive(), habit.getHidden(), habit.getCompleted(), habit.getTimeOfDay(), every, daysOfWeek);
            if (childHabits1.get().size() > 0) {
                for (Habit childHabit1 : childHabits1.get()) {
                    Long childEvery = null;
                    List<String> childDaysOfWeek = new ArrayList<>();
                    if (childHabit1.getScheduleType().equals("daily")) {
                        Daily daily = dailyRepository.findByReferenceId("habit/" + childHabit1.getId());
                        childEvery= daily.getEvery();
                    }
                    else if(childHabit1.getScheduleType().equals("monthly")){
                        Monthly monthly = monthlyRepository.findByReferenceId("habit/"+childHabit1.getId());
                        childEvery= monthly.getEvery();
                    }
                    else if(childHabit1.getScheduleType().equals("yearly")){
                        Yearly yearly = yearlyRepository.findByReferenceId("habit/"+childHabit1.getId());
                        childEvery = yearly.getEvery();
                    }
                    else if(childHabit1.getScheduleType().equals("weekly")){
                        Weekly weekly = weeklyRepository.findByReferenceId("habit/"+childHabit1.getId());
                        childEvery = weekly.getEvery();
                        for (DayOfWeek dayOfWeek:weekly.getDaysOfWeek()){
                            daysOfWeek.add(dayOfWeek.toString());
                        }
                    }
                    Long childStreak;
                    if (dateUtils.keepStreak(childHabit1.getId(),childHabit1.getScheduleType(),childHabit1.getDueDate())){
                        childStreak = childHabit1.getStreak();
                    }
                    else{
                        childStreak = 0L;
                    }
                    Optional<HabitType> childHabitType1 = habitTypeRepository.findById(childHabit1.getHabitTypeId());
                    HabitResponse childHabitResponse1 = new HabitResponse(childHabit1.getId(), childHabit1.getCreatedAt(),
                            childHabit1.getUpdatedAt(), childHabit1.getName(), childHabit1.getScheduleType(), childHabit1.getTimeTaken(),
                            childHabit1.getStartDate(), childHabit1.getDueDate(), childHabitType1.get().getName(),
                            childHabit1.getDescription(), childStreak, childHabit1.getTotalTimes(),
                            childHabit1.getActive(), childHabit1.getHidden(), childHabit1.getCompleted(), childHabit1.getTimeOfDay(),childEvery,childDaysOfWeek);
                    habitResponses.add(childHabitResponse1);
                }

            }
            habitResponses.add(habitResponse);
        }
        return habitResponses;
    };

    @Override
    public Habit createDailyRootHabit(Long userId, String name,Date startDate,  Long timeOfDay, Date dueDate, Long every, String scheduleType,
                                  String habitTypeName, Boolean active){
        HabitType habitType = habitTypeRepository.findByNameAndUserId(habitTypeName,userId);
        Long streak = 0L;
        Long totalTimes = 0L;
        Date newDueDate = dateUtils.getEndOfDay(dueDate);
        Habit habit = habitRepository.save(new Habit(name, timeOfDay, startDate, newDueDate, habitType.getId(), active, userId, streak,
                totalTimes,scheduleType));
        Daily daily = dailyRepository.save(new Daily(every,"habit/"+habit.getId()));
        return habit;
    };

    @Override
    public Habit createDailyChildHabit(Long userId, String name,Date startDate,  Long timeOfDay, Date dueDate, Long every, String scheduleType,
                                  String habitTypeName,String parentName, Boolean active){
        HabitType habitType = habitTypeRepository.findByNameAndUserId(habitTypeName,userId);
        Habit oldHabit = habitRepository.findByUserIdAndName(userId,parentName);
        Long streak = 0L;
        Long totalTimes = 0L;
        Date newDueDate = dateUtils.getEndOfDay(dueDate);
        Habit habit = habitRepository.save(new Habit(name, timeOfDay, startDate, newDueDate, habitType.getId(), active, userId, streak,
                totalTimes,scheduleType, oldHabit.getId()));
        Daily daily = dailyRepository.save(new Daily(every,"habit/"+habit.getId()));
        return habit;
    };

    @Override
    public Habit createWeeklyRootHabit(Long userId, String name, Date startDate, Long timeOfDay, Date dueDate, Long every, String scheduleType,
                                   String habitTypeName, List<DayOfWeek> daysOfWeek, Boolean active){
        HabitType habitType = habitTypeRepository.findByNameAndUserId(habitTypeName,userId);
        Long streak = 0L;
        Long totalTimes = 0L;
        Date newDueDate = dateUtils.getEndOfDay(dueDate);
        Habit habit = habitRepository.save(new Habit(name, timeOfDay, startDate, newDueDate, habitType.getId(), active, userId, streak,
                totalTimes,scheduleType));
        Weekly weekly = weeklyRepository.save(new Weekly(every,"habit/"+habit.getId(),daysOfWeek));
        return habit;
    };

    @Override
    public Habit createWeeklyChildHabit(Long userId, String name, Date startDate, Long timeOfDay, Date dueDate, Long every, String scheduleType,
                                   String habitTypeName, List<DayOfWeek> daysOfWeek,String parentName, Boolean active){
        HabitType habitType = habitTypeRepository.findByNameAndUserId(habitTypeName,userId);
        Habit oldHabit = habitRepository.findByUserIdAndName(userId,parentName);
        Long streak = 0L;
        Long totalTimes = 0L;
        Date newDueDate = dateUtils.getEndOfDay(dueDate);
        Habit habit = habitRepository.save(new Habit(name, timeOfDay, startDate, newDueDate, habitType.getId(), active, userId, streak,
                totalTimes,scheduleType, oldHabit.getId()));
        Weekly weekly = weeklyRepository.save(new Weekly(every,"habit/"+habit.getId(),daysOfWeek));
        return habit;
    };


    @Override
    public Habit createMonthlyRootHabit(Long userId, String name,Date startDate,  Long timeOfDay, Date dueDate, Long every, String scheduleType,
                                  String habitTypeName, Boolean active){
        HabitType habitType = habitTypeRepository.findByNameAndUserId(habitTypeName,userId);
        Long streak = 0L;
        Long totalTimes = 0L;
        Date newDueDate = dateUtils.getEndOfDay(dueDate);
        Habit habit = habitRepository.save(new Habit(name, timeOfDay, startDate, newDueDate, habitType.getId(), active, userId, streak,
                totalTimes,scheduleType));
        Monthly monthly = monthlyRepository.save(new Monthly(every,"habit/"+habit.getId()));
        return habit;
    };

    @Override
    public Habit createMonthlyChildHabit(Long userId, String name,Date startDate,  Long timeOfDay, Date dueDate, Long every, String scheduleType,
                                    String habitTypeName,String parentName, Boolean active){
        HabitType habitType = habitTypeRepository.findByNameAndUserId(habitTypeName,userId);
        Habit oldHabit = habitRepository.findByUserIdAndName(userId,parentName);
        Long streak = 0L;
        Long totalTimes = 0L;
        Date newDueDate = dateUtils.getEndOfDay(dueDate);
        Habit habit = habitRepository.save(new Habit(name, timeOfDay, startDate, newDueDate, habitType.getId(), active, userId, streak,
                totalTimes,scheduleType, oldHabit.getId()));
        Monthly monthly = monthlyRepository.save(new Monthly(every,"habit/"+habit.getId()));
        return habit;
    };

    @Override
    public Habit createYearlyRootHabit(Long userId, String name,Date startDate,  Long timeOfDay, Date dueDate, Long every, String scheduleType,
                                  String habitTypeName, Boolean active){
        HabitType habitType = habitTypeRepository.findByNameAndUserId(habitTypeName,userId);
        Long streak = 0L;
        Long totalTimes = 0L;
        Date newDueDate = dateUtils.getEndOfDay(dueDate);
        Habit habit = habitRepository.save(new Habit(name, timeOfDay, startDate, newDueDate, habitType.getId(), active, userId, streak,
                totalTimes,scheduleType));
        Yearly yearly = yearlyRepository.save(new Yearly(every,"habit/"+habit.getId()));
        return habit;
    };

    @Override
    public Habit createYearlyChildHabit(Long userId, String name,Date startDate,  Long timeOfDay, Date dueDate, Long every, String scheduleType,
                                   String habitTypeName,String parentName, Boolean active){
        HabitType habitType = habitTypeRepository.findByNameAndUserId(habitTypeName,userId);
        Habit oldHabit = habitRepository.findByUserIdAndName(userId,parentName);
        Long streak = 0L;
        Long totalTimes = 0L;
        Date newDueDate = dateUtils.getEndOfDay(dueDate);
        Habit habit = habitRepository.save(new Habit(name, timeOfDay, startDate, newDueDate, habitType.getId(), active, userId, streak,
                totalTimes,scheduleType,oldHabit.getId()));
        Yearly yearly = yearlyRepository.save(new Yearly(every,"habit/"+habit.getId()));
        return habit;
    };

    @Override
    public Habit completeHabit(Long userId, Long id, String habitCompletionType){
        Habit oldHabit = habitRepository.findByUserIdAndId(userId,id);
        String scheduleTypeName = oldHabit .getScheduleType();
        Date dueDate = oldHabit.getDueDate();
        Habit habit = oldHabit;
        if (habitCompletionType.equals("Complete")){
            habitRepository.completeHabit(oldHabit.getId());
            habit = habitRepository.findByUserIdAndId(userId,id);
            habitTransactionRepository.save(new HabitTransaction(habit.getName(),habit.getTimeTaken(),habit.getStartDate(),dueDate,
                    habit.getHabitTypeId(),userId,habit.getStreak(),habit.getTotalTimes(),habit.getScheduleType(),habit.getId(),habit.getTimeOfDay(),habitCompletionType));
        }
        else if (habitCompletionType.equals("Atomic")){
            habit = habitRepository.findByUserIdAndId(userId,id);
            habitTransactionRepository.save(new HabitTransaction(habit.getName(),habit.getTimeTaken(),habit.getStartDate(),dueDate,
                    habit.getHabitTypeId(),userId,habit.getStreak(),habit.getTotalTimes(),habit.getScheduleType(),habit.getId(),habit.getTimeOfDay(),habitCompletionType));
        }
        else if (habitCompletionType.equals("Condition")){
            habit = habitRepository.findByUserIdAndId(userId,id);
            habitTransactionRepository.save(new HabitTransaction(habit.getName(),habit.getTimeTaken(),habit.getStartDate(),dueDate,
                    habit.getHabitTypeId(),userId,habit.getStreak(),habit.getTotalTimes(),habit.getScheduleType(),habit.getId(),habit.getTimeOfDay(),habitCompletionType));
        }

        String referenceId = "habit/"+habit.getId();
        Date nextDueDate = dateUtils.getNextDueDate(dueDate,scheduleTypeName,referenceId);
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
