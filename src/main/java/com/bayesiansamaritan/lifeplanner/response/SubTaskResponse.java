package com.bayesiansamaritan.lifeplanner.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class SubTaskResponse {

    private Long id;
    private Date createdAt;
    private Date updatedAt;
    private String name;
    private String scheduleType;
    private Long timeTaken;
    private Date startDate;
    private Date dueDate;
    private String taskName;
    private String description;

    public SubTaskResponse(Long id, Date createdAt, Date updatedAt, String name, String scheduleType, Long timeTaken,
                           Date startDate, Date dueDate, String taskName, String description) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.name = name;
        this.scheduleType = scheduleType;
        this.timeTaken = timeTaken;
        this.startDate = startDate;
        this.dueDate = dueDate;
        this.taskName = taskName;
        this.description = description;
    }
}