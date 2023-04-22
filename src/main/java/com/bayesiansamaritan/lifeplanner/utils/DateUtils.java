package com.bayesiansamaritan.lifeplanner.utils;

import com.bayesiansamaritan.lifeplanner.enums.DayOfWeek;
import com.bayesiansamaritan.lifeplanner.model.Scheduler.Daily;
import com.bayesiansamaritan.lifeplanner.model.Scheduler.Monthly;
import com.bayesiansamaritan.lifeplanner.model.Scheduler.Weekly;
import com.bayesiansamaritan.lifeplanner.model.Scheduler.Yearly;
import com.bayesiansamaritan.lifeplanner.repository.Habit.HabitRepository;
import com.bayesiansamaritan.lifeplanner.repository.Scheduler.DailyRepository;
import com.bayesiansamaritan.lifeplanner.repository.Scheduler.MonthlyRepository;
import com.bayesiansamaritan.lifeplanner.repository.Scheduler.WeeklyRepository;
import com.bayesiansamaritan.lifeplanner.repository.Scheduler.YearlyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Year;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Service
public class DateUtils {

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

    public Date getEndOfDay(Date date){
        Long time = date.getTime();
        Date newDate = new Date(time -time%(24*60*60*1000) +((18*60)+30)*60*1000-1);
        if (newDate.getHours()==18){
            Date newDate2 = new Date(newDate.getTime() + 6*60*60*100);
            return newDate2;
        }
        return newDate;
    }

    public Date getStartOfMorning(Date date){
        Long time = date.getTime();
        Date newDate = new Date(time -time%(24*60*60*1000) +((3*60)+30)*60*1000);
        if (newDate.getHours()==3){
            Date newDate2 = new Date(newDate.getTime() + 6*60*60*100);
            return newDate2;
        }
        return newDate;
    }

    public Date getNextDueDate(Date currentDueDate, String scheduleTypeName, String reference){
        if (scheduleTypeName.equals("daily")){
            Daily daily = dailyRepository.findByReferenceId(reference);
            Date newDueDate = new Date(getEndOfDay(currentDueDate).getTime() + 24*60*60*1000*daily.getEvery());
            Date currentDate = new Date();
            if (currentDate.compareTo(getStartOfMorning(newDueDate))>=0){
                Date newDueDate2 = new Date(getEndOfDay(currentDate).getTime() +24*60*60*1000*daily.getEvery());
                return newDueDate2;
            }
            return newDueDate;
        }
        else if(scheduleTypeName.equals("monthly")){
            Monthly monthly = monthlyRepository.findByReferenceId(reference);
            LocalDate localDueDate = LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(currentDueDate));
            LocalDate newLocalDueDate = localDueDate.plusMonths(monthly.getEvery());
            Date currentDate = new Date();
            Date newDueDate = Date.from(newLocalDueDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            return newDueDate;
        }
        else if(scheduleTypeName.equals("yearly")){
            Yearly yearly = yearlyRepository.findByReferenceId(reference);
            LocalDate localDueDate = LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(currentDueDate));
            LocalDate newLocalDueDate = localDueDate.plusYears(yearly.getEvery());
            Date newDueDate = Date.from(newLocalDueDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            return newDueDate;
        }
//        Weekly every scenario case left when not done on correct day
        else if(scheduleTypeName.equals("weekly")){
            Weekly weekly = weeklyRepository.findByReferenceId(reference);
            List<DayOfWeek> daysOfWeek = weekly.getDaysOfWeek();
            LocalDate localDueDate = LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(currentDueDate));
            Boolean match = false;
            while (!match) {
                DayOfWeek dayOfWeek = DayOfWeek.valueOf(localDueDate.getDayOfWeek().toString());
                if (dayOfWeek.equals(dayOfWeek.SUNDAY)) {
                    localDueDate = localDueDate.plusDays(7L * (weekly.getEvery()-1));
                }
                localDueDate = localDueDate.plusDays(1L);
                Date newDueDate = Date.from(localDueDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
                Date currentDate = new Date();
                if (currentDate.compareTo(getStartOfMorning(newDueDate))>=0){
                }
                else {
                    for (DayOfWeek dayOfWeek1 : daysOfWeek) {
                        if (dayOfWeek1.name().equals(localDueDate.getDayOfWeek().name())) {
                            match = true;
                        }
                    }
                }
            }
            Date newDueDate = Date.from(localDueDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            Date newDueDate1 = getEndOfDay(newDueDate);
            return newDueDate1;
        }
        else{
            return currentDueDate;
        }
    }

    public Date getDueDate(Date currentDueDate, String scheduleTypeName, String reference){
        if (scheduleTypeName.equals("daily")){
            Daily daily = dailyRepository.findByReferenceId(reference);
            Date newDueDate = new Date(getEndOfDay(currentDueDate).getTime() + 24*60*60*1000*daily.getEvery());
            return newDueDate;
        }
        else if(scheduleTypeName.equals("monthly")){
            Monthly monthly = monthlyRepository.findByReferenceId(reference);
            LocalDate localDueDate = LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(currentDueDate));
            LocalDate newLocalDueDate = localDueDate.plusMonths(monthly.getEvery());
            Date currentDate = new Date();
            Date newDueDate = Date.from(newLocalDueDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            return newDueDate;
        }
        else if(scheduleTypeName.equals("yearly")){
            Yearly yearly = yearlyRepository.findByReferenceId(reference);
            LocalDate localDueDate = LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(currentDueDate));
            LocalDate newLocalDueDate = localDueDate.plusYears(yearly.getEvery());
            Date newDueDate = Date.from(newLocalDueDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            return newDueDate;
        }
//        Weekly every scenario case left when not done on correct day
        else if(scheduleTypeName.equals("weekly")){
            Weekly weekly = weeklyRepository.findByReferenceId(reference);
            List<DayOfWeek> daysOfWeek = weekly.getDaysOfWeek();
            LocalDate localDueDate = LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(currentDueDate));
            Boolean match = false;
            while (!match) {
                DayOfWeek dayOfWeek = DayOfWeek.valueOf(localDueDate.getDayOfWeek().toString());
                if (dayOfWeek.equals(dayOfWeek.SUNDAY)) {
                    localDueDate = localDueDate.plusDays(7L * (weekly.getEvery()-1));
                }
                localDueDate = localDueDate.plusDays(1L);
                Date newDueDate = Date.from(localDueDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
                Date currentDate = new Date();
                if (currentDate.compareTo(getStartOfMorning(newDueDate))>=0){
                }
                else {
                    for (DayOfWeek dayOfWeek1 : daysOfWeek) {
                        if (dayOfWeek1.name().equals(localDueDate.getDayOfWeek().name())) {
                            match = true;
                        }
                    }
                }
            }
            Date newDueDate = Date.from(localDueDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            Date newDueDate1 = getEndOfDay(newDueDate);
            return newDueDate1;
        }
        else{
            return currentDueDate;
        }
    }


    public Boolean keepStreak(Long referenceId,String scheduleTypeName, Date dueDate){
        String reference = "habit/"+referenceId;
        Date newDueDate = getDueDate(dueDate,scheduleTypeName,reference);
        Date currentDate = new Date();
        if (currentDate.compareTo(getStartOfMorning(newDueDate))>=0){
            habitRepository.removeStreak(referenceId);
            return false;
        }
        else{
            return true;
        }
    }
}
