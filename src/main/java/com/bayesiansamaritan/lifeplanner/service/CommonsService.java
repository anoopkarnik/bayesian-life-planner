package com.bayesiansamaritan.lifeplanner.service;

import com.bayesiansamaritan.lifeplanner.enums.DayOfWeek;
import com.bayesiansamaritan.lifeplanner.model.*;
import com.bayesiansamaritan.lifeplanner.response.HabitResponse;

import java.util.Date;
import java.util.List;

public interface CommonsService {

    public List<HabitType> findHabitTypeByUserId(Long userId);
    public List<TaskType> findTaskTypeByUserId(Long userId);

    public List<JournalType> findJournalTypeByUserId(Long userId);

    public List<StatsType> findStatsTypeByUserId(Long userId);
}
