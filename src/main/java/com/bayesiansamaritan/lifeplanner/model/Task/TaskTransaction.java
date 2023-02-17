package com.bayesiansamaritan.lifeplanner.model.Task;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "task_transaction",schema = "life_schema")
@Getter
@Setter
public class TaskTransaction {

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

    @Column(name="user_id")
    private Long userId;

    @Column(name="description",length = 10240)
    private String description;


    public TaskTransaction(){};

    public TaskTransaction(String name, Long timeTaken, Date startDate, Date dueDate, Long taskTypeId,  Long userId, String scheduleType) {
        this.name = name;
        this.timeTaken = timeTaken;
        this.startDate = startDate;
        this.dueDate = dueDate;
        this.taskTypeId = taskTypeId;
        this.userId = userId;
        this.scheduleType = scheduleType;
    }

    public TaskTransaction(Date createdAt, Date updatedAt, String name, String scheduleType, Long timeTaken, Date startDate, Date dueDate, Long taskTypeId,  Long userId) {
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.name = name;
        this.scheduleType = scheduleType;
        this.timeTaken = timeTaken;
        this.startDate = startDate;
        this.dueDate = dueDate;
        this.taskTypeId = taskTypeId;
        this.userId = userId;
    }

}
