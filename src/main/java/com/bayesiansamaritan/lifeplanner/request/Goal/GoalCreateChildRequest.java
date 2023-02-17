package com.bayesiansamaritan.lifeplanner.request.Goal;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GoalCreateChildRequest {

    private String name;
    private Long timeTaken;
    private String goalTypeName;
    private String parentGoalName;
}
