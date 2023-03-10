package com.bayesiansamaritan.lifeplanner.request.Goal;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GoalCreateChildRequest {

    private String name;
    private Date dueDate;
    private String goalTypeName;
    private String parentGoalName;
    private Boolean active;
}
