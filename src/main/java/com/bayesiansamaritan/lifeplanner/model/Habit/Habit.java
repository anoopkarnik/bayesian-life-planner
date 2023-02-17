package com.bayesiansamaritan.lifeplanner.model.Habit;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "habit",schema = "life_schema")
@Getter
@Setter
@NoArgsConstructor
public class Habit {

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

    @Column(name="time_taken")
    private Long timeTaken;

    @Column(name="start_date")
    private Date startDate;

    @Column(name="due_date")
    private Date dueDate;

    @Column(name="habit_type_id")
    private Long habitTypeId;

    @Column(name="active")
    private Boolean active;

    @Column(name="user_id")
    private Long userId;

    @Column(name="streak")
    private Long streak;

    @Column(name="total_times")
    private Long totalTimes;

    @Column(name="total_time_spent")
    private Long totalTimeSpent;

    @Column(name="schedule_type")
    private String scheduleType;

    @Column(name="description",length = 10240)
    private String description;

    @Column(name="parent_id")
    private long parentId=0L;

    public Habit(String name,  Long timeTaken, Date startDate, Date dueDate, Long habitTypeId, Boolean active, Long userId,
                 Long streak, Long totalTimes, String scheduleType) {
        this.name = name;
        this.timeTaken = timeTaken;
        this.startDate = startDate;
        this.dueDate = dueDate;
        this.habitTypeId = habitTypeId;
        this.active = active;
        this.userId = userId;
        this.streak = streak;
        this.totalTimes = totalTimes;
        this.scheduleType = scheduleType;
    }

    public Habit(String name,  Long timeTaken, Date startDate, Date dueDate, Long habitTypeId, Boolean active, Long userId,
                 Long streak, Long totalTimes, String scheduleType, Long parentId) {
        this.name = name;
        this.timeTaken = timeTaken;
        this.startDate = startDate;
        this.dueDate = dueDate;
        this.habitTypeId = habitTypeId;
        this.active = active;
        this.userId = userId;
        this.streak = streak;
        this.totalTimes = totalTimes;
        this.scheduleType = scheduleType;
        this.parentId = parentId;
    }
}
