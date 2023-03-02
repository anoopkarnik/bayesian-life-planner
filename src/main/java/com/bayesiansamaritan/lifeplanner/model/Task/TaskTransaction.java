package com.bayesiansamaritan.lifeplanner.model.Task;

import com.bayesiansamaritan.lifeplanner.model.Base2Model;
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
public class TaskTransaction extends Base2Model {

    @Column(name="schedule_type")
    private String scheduleType;
    @Column(name="time_taken")
    private Long timeTaken;
    @Column(name="due_date")
    private Date dueDate;
    @Column(name="task_type_id")
    private Long taskTypeId;
    @Column(name="task_id")
    private Long taskId;

    public TaskTransaction(){};

    public TaskTransaction(String name, Long timeTaken, Date startDate, Date dueDate, Long taskTypeId,  Long userId, String scheduleType,
                           Long taskId) {
        this.name = name;
        this.timeTaken = timeTaken;
        this.startDate = startDate;
        this.dueDate = dueDate;
        this.taskTypeId = taskTypeId;
        this.userId = userId;
        this.scheduleType = scheduleType;
        this.taskId = taskId;
    }

    public TaskTransaction(Date createdAt, Date updatedAt, String name, String scheduleType, Long timeTaken, Date startDate, Date dueDate, Long taskTypeId,
                           Long userId, Long taskId) {
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.name = name;
        this.scheduleType = scheduleType;
        this.timeTaken = timeTaken;
        this.startDate = startDate;
        this.dueDate = dueDate;
        this.taskTypeId = taskTypeId;
        this.userId = userId;
        this.taskId = taskId;
    }

}
