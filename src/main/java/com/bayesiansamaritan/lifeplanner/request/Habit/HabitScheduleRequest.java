package com.bayesiansamaritan.lifeplanner.request.Habit;

import com.bayesiansamaritan.lifeplanner.enums.DayOfWeek;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HabitScheduleRequest {

    private Long id;
    private String scheduleType;
    private Long every;
    private List<DayOfWeek> daysOfWeek;
}
