package com.bayesiansamaritan.lifeplanner.response;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class TaskResponse {

    private Long id;
    private Date createdAt;
    private Date updatedAt;
    private String name;
    private String scheduleType;
    private Long timeTaken;
    private Date startDate;
    private Date dueDate;
    private String taskTypeName;
    private String description;
    private List<TaskResponse> taskResponses;
    private Boolean active;
    private Boolean hidden;
    private Boolean completed;
    private Long every;
    private List<String> daysOfWeek;

    public TaskResponse(Long id,Date createdAt, Date updatedAt, String name, String scheduleType, Long timeTaken,
                        Date startDate, Date dueDate, String taskTypeName, String description,Boolean active, Boolean hidden, Boolean completed,Long every) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.name = name;
        this.scheduleType = scheduleType;
        this.timeTaken = timeTaken;
        this.startDate = startDate;
        this.dueDate = dueDate;
        this.taskTypeName = taskTypeName;
        this.description = description;
        this.active = active;
        this.hidden = hidden;
        this.completed = completed;
        this.every = every;
    }

    public TaskResponse(Long id,Date createdAt, Date updatedAt, String name, String scheduleType, Long timeTaken,
                        Date startDate, Date dueDate, String taskTypeName, String description,Boolean active, Boolean hidden, Boolean completed,Long every,
                        List<String> daysOfWeek) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.name = name;
        this.scheduleType = scheduleType;
        this.timeTaken = timeTaken;
        this.startDate = startDate;
        this.dueDate = dueDate;
        this.taskTypeName = taskTypeName;
        this.description = description;
        this.active = active;
        this.hidden = hidden;
        this.completed = completed;
        this.every = every;
        this.daysOfWeek = daysOfWeek;
    }
}