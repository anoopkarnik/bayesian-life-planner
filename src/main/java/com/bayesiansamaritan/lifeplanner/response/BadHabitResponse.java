package com.bayesiansamaritan.lifeplanner.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class BadHabitResponse {

    private Long id;
    private Date createdAt;
    private Date updatedAt;
    private String name;
    private Date startDate;
    private String habitTypeName;
    private Long totalTimes;
    private String description;

    public BadHabitResponse(Long id, Date createdAt, Date updatedAt, String name, Date startDate, String habitTypeName, Long totalTimes, String description) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.name = name;
        this.startDate = startDate;
        this.habitTypeName = habitTypeName;
        this.totalTimes = totalTimes;
        this.description = description;
    }
}