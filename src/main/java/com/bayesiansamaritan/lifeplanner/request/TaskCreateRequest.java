package com.bayesiansamaritan.lifeplanner.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import com.bayesiansamaritan.lifeplanner.enums.DayOfWeek;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TaskCreateRequest {

    private String name;
    private String scheduleType;
    private Long timeTaken;
    private Date startDate;
    private Date dueDate;
    private String taskTypeName;
    private Long userId;
    private Long every;
    private List<DayOfWeek> daysOfWeek;
}
