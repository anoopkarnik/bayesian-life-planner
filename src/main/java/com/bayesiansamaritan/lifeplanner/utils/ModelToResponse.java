package com.bayesiansamaritan.lifeplanner.utils;

import com.bayesiansamaritan.lifeplanner.enums.DayOfWeek;
import com.bayesiansamaritan.lifeplanner.model.Habit.Habit;
import com.bayesiansamaritan.lifeplanner.model.Habit.HabitType;
import com.bayesiansamaritan.lifeplanner.model.Scheduler.Daily;
import com.bayesiansamaritan.lifeplanner.model.Scheduler.Monthly;
import com.bayesiansamaritan.lifeplanner.model.Scheduler.Weekly;
import com.bayesiansamaritan.lifeplanner.model.Scheduler.Yearly;
import com.bayesiansamaritan.lifeplanner.repository.Habit.HabitRepository;
import com.bayesiansamaritan.lifeplanner.repository.Scheduler.DailyRepository;
import com.bayesiansamaritan.lifeplanner.repository.Scheduler.MonthlyRepository;
import com.bayesiansamaritan.lifeplanner.repository.Scheduler.WeeklyRepository;
import com.bayesiansamaritan.lifeplanner.repository.Scheduler.YearlyRepository;
import com.bayesiansamaritan.lifeplanner.response.HabitResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ModelToResponse {

    @Autowired
    private DailyRepository dailyRepository;
    @Autowired
    private WeeklyRepository weeklyRepository;
    @Autowired
    private MonthlyRepository monthlyRepository;
    @Autowired
    private YearlyRepository yearlyRepository;
    @Autowired
    private HabitRepository habitRepository;
    @Autowired
    private HabitDateUtils habitDateUtils;

    public List<HabitResponse> habitsModelsToResponses(List<Habit> habits, HabitType habitType){
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
            if (habitDateUtils.keepStreak(habit.getId(),habit.getScheduleType(),habit.getDueDate())){
                streak = habit.getStreak();
            }
            else{
                streak = 0L;
            }
            HabitResponse habitResponse = new HabitResponse(habit.getId(),habit.getCreatedAt(),
                    habit.getUpdatedAt(),habit.getName(),habit.getScheduleType(),habit.getTimeTaken(),habit.getStartDate(),
                    habit.getDueDate(),habitType.getName(),habit.getDescription(),streak,habit.getTotalTimes(),
                    habit.getActive(),habit.getHidden(),habit.getCompleted(),habit.getTimeOfDay(),every,daysOfWeek);
            habitResponses.add(habitResponse);
        }
        return habitResponses;
    };
}
