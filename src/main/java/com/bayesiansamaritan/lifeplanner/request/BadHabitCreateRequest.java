package com.bayesiansamaritan.lifeplanner.request;

import com.bayesiansamaritan.lifeplanner.enums.DayOfWeek;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BadHabitCreateRequest {

    private String name;
    private Date startDate;
    private String habitTypeName;
    private Long userId;


}
