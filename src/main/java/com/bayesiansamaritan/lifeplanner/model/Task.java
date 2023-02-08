package com.bayesiansamaritan.lifeplanner.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;


@Entity
@Table(name = "task",schema = "life_schema")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", updatable = false)
    protected Date createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    protected Date updatedAt;

    @Column(name="name")
    private String name;

    @Column(name="schedule_type")
    private String scheduleType;

    @Column(name="time_taken")
    private Long timeTaken;

    @Column(name="start_date")
    private Date startDate;

    @Column(name="due_date")
    private Date dueDate;

    @Column(name="task_type_id")
    private Long taskTypeId;

    @Column(name="active")
    private Boolean active;

    @Column(name="user_id")
    private Long userId;

    public Task(){};

    public Task(String name, Long timeTaken, Date startDate, Date dueDate, Long taskTypeId, Boolean active, Long userId,String scheduleType) {
        this.name = name;
        this.timeTaken = timeTaken;
        this.startDate = startDate;
        this.dueDate = dueDate;
        this.taskTypeId = taskTypeId;
        this.active = active;
        this.userId = userId;
        this.scheduleType = scheduleType;
    }



    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScheduleType() {
        return scheduleType;
    }

    public void setScheduleType(String scheduleType) {
        this.scheduleType = scheduleType;
    }

    public Long getTimeTaken() {
        return timeTaken;
    }

    public void setTimeTaken(Long timeTaken) {
        this.timeTaken = timeTaken;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Long getTaskTypeId() {
        return taskTypeId;
    }

    public void setTaskTypeId(Long taskTypeId) {
        this.taskTypeId = taskTypeId;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
