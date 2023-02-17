package com.bayesiansamaritan.lifeplanner.model.Task;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;


@Entity
@Table(name = "task",schema = "life_schema")
@Getter
@Setter
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

    @Column(name="description",length = 10240)
    private String description;

    @Column(name="parent_id")
    private long parentId=0L;


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

    public Task(String name, Long timeTaken, Date startDate, Date dueDate, Long taskTypeId, Boolean active, Long userId,String scheduleType, Long parentId) {
        this.name = name;
        this.timeTaken = timeTaken;
        this.startDate = startDate;
        this.dueDate = dueDate;
        this.taskTypeId = taskTypeId;
        this.active = active;
        this.userId = userId;
        this.scheduleType = scheduleType;
        this.parentId = parentId;
    }

}
