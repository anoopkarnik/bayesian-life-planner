package com.bayesiansamaritan.lifeplanner.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class BadHabitResponse {

    private Long id;
    private Date createdAt;
    private Date updatedAt;
    private String name;
    private Date startDate;
    private String badHabitTypeName;
    private Long totalTimes;
    private String description;
    private Boolean active;
    private Boolean hidden;
    private Boolean completed;
    private List<BadHabitResponse> badHabitResponses;

    public BadHabitResponse(Long id, Date createdAt, Date updatedAt, String name, Date startDate, String badHabitTypeName, Long totalTimes,
                            String description,Boolean active, Boolean hidden, Boolean completed) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.name = name;
        this.startDate = startDate;
        this.badHabitTypeName = badHabitTypeName;
        this.totalTimes = totalTimes;
        this.description = description;
        this.active = active;
        this.hidden = hidden;
        this.completed = completed;
    }
}