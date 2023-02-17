package com.bayesiansamaritan.lifeplanner.request.BadHabit;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BadHabitCreateRootRequest {

    private String name;
    private Date startDate;
    private String habitTypeName;

}
