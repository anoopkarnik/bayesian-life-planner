package com.bayesiansamaritan.lifeplanner.request.Habit;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HabitModifyRequest {

    private Long id;
    private String name;
    private Date startDate;
    private String description;
    private Boolean active;
    private Boolean hidden;
    private Boolean completed;
    private Long habitTypeName;
    private Date dueDate;
    private Long timeTaken;
    private Long streak;
    private Long totalTimes;
    private Long totalTimeSpent;
}
