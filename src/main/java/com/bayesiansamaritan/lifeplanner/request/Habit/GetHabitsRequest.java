package com.bayesiansamaritan.lifeplanner.request.Habit;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetHabitsRequest {

    private String habitTypeName;
    private Boolean active;
    private Date currentDate;
}
