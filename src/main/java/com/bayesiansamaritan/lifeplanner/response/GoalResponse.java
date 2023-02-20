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

    private Boolean completed;
    private Float completedPercentage;
    private String description;

    private List<GoalResponse> goalResponses;


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

    public GoalResponse(Long id, Date createdAt, Date updatedAt, String name, Date dueDate, String goalTypeName, Boolean completed, String description,
                        Float completedPercentage) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.name = name;
        this.dueDate = dueDate;
        this.goalTypeName = goalTypeName;
        this.completed = completed;
        this.description = description;
        this.completedPercentage = completedPercentage;
    }
}