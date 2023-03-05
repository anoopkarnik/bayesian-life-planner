package com.bayesiansamaritan.lifeplanner.model.Habit;

import com.bayesiansamaritan.lifeplanner.model.Base2Model;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.time.LocalTime;


@Entity
@Table(name = "habit",schema = "life_schema")
@Getter
@Setter
@NoArgsConstructor
public class Habit extends Base2Model {

    @Column(name="time_taken")
    private Long timeTaken;
    @Column(name="due_date")
    private Date dueDate;

    @Column(name="habit_type_id")
    private Long habitTypeId;


    @Column(name="streak")
    private Long streak;

    @Column(name="total_times")
    private Long totalTimes;

    @Column(name="total_time_spent")
    private Long totalTimeSpent;

    @Column(name="schedule_type")
    private String scheduleType;

    @Column(name="parent_id")
    private long parentId=0L;
    @Column(name="time_of_day")
    private Long timeOfDay;


    public Habit(String name,  Long timeOfDay, Date startDate, Date dueDate, Long habitTypeId, Boolean active, Long userId,
                 Long streak, Long totalTimes,  String scheduleType) {
        this.name = name;
        this.startDate = startDate;
        this.dueDate = dueDate;
        this.habitTypeId = habitTypeId;
        this.active = active;
        this.userId = userId;
        this.streak = streak;
        this.timeOfDay = timeOfDay;
        this.scheduleType = scheduleType;
        this.totalTimes = totalTimes;
    }

    public Habit(String name,  Long timeOfDay, Date startDate, Date dueDate, Long habitTypeId, Boolean active, Long userId,
                 Long streak,Long totalTimes, String scheduleType, Long parentId) {
        this.name = name;
        this.startDate = startDate;
        this.dueDate = dueDate;
        this.habitTypeId = habitTypeId;
        this.active = active;
        this.userId = userId;
        this.streak = streak;
        this.timeOfDay = timeOfDay;
        this.totalTimes = totalTimes;
        this.scheduleType = scheduleType;
        this.parentId = parentId;
    }
}
