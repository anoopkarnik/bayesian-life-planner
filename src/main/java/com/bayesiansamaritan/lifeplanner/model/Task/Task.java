package com.bayesiansamaritan.lifeplanner.model.Task;

import com.bayesiansamaritan.lifeplanner.model.Base2Model;
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
public class Task extends Base2Model {

    @Column(name="schedule_type")
    private String scheduleType;

    @Column(name="time_taken")
    private Long timeTaken;

    @Column(name="due_date")
    private Date dueDate;

    @Column(name="task_type_id")
    private Long taskTypeId;
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
