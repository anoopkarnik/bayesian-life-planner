package com.bayesiansamaritan.lifeplanner.request.Habit;

import com.bayesiansamaritan.lifeplanner.enums.DayOfWeek;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HabitCreateChildRequest {

    private String name;
    private String scheduleType;
    private Long timeTaken;
    private Date startDate;
    private Date dueDate;
    private String habitTypeName;
    private Long every;
    private List<DayOfWeek> daysOfWeek;
    private String parentHabitName;
}
