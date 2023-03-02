package com.bayesiansamaritan.lifeplanner.request.Goal;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GoalModifyRequest {

    private Long id;
    private String name;
    private Date startDate;
    private String description;
    private Boolean active;
    private Boolean hidden;
    private Boolean completed;
    private Long goalTypeName;
    private Date dueDate;
    private Long timeTaken;
}
