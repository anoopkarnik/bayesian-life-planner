package com.bayesiansamaritan.lifeplanner.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class GoalResponse {

    private Long id;
    private Date createdAt;
    private Date updatedAt;
    private String name;
    private Date dueDate;
    private String goalTypeName;

    private Boolean active;
    private Boolean hidden;
    private Boolean completed;
    private Float completedPercentage;

    private Float workPercentage;
    private String description;

    private List<GoalResponse> goalResponses;
    private Date startDate;
    private Long timeTaken;


    public GoalResponse(Long id, Date createdAt, Date updatedAt, String name, Date dueDate, String goalTypeName, Boolean completed, String description,
                        List<GoalResponse> goalResponses) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.name = name;
        this.dueDate = dueDate;
        this.goalTypeName = goalTypeName;
        this.completed = completed;
        this.description = description;
        this.goalResponses = goalResponses;
    }

    public GoalResponse(Long id, Date createdAt, Date updatedAt, String name, Date dueDate, String goalTypeName, String description,
                        Float completedPercentage, Float workPercentage,Date startDate, Long timeTaken, Boolean active, Boolean hidden, Boolean completed) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.name = name;
        this.dueDate = dueDate;
        this.goalTypeName = goalTypeName;
        this.description = description;
        this.completedPercentage = completedPercentage;
        this.workPercentage = workPercentage;
        this.active = active;
        this.hidden = hidden;
        this.completed = completed;
        this.startDate = startDate;
        this.timeTaken = timeTaken;
    }
}