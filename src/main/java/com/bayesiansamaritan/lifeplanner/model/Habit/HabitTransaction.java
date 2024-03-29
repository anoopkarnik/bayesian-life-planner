package com.bayesiansamaritan.lifeplanner.model.Habit;

import com.bayesiansamaritan.lifeplanner.model.Base2Model;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "habit_transaction",schema = "life_schema")
@Getter
@Setter
public class HabitTransaction extends Base2Model {

    @Column(name="time_taken")
    private Long timeTaken;

    @Column(name="due_date")
    private Date dueDate;

    @Column(name="habit_type_id")
    private Long habitTypeId;

    @Column(name="streak")
    private Long streak;

    @Column(name="habit_id")
    private Long habitId;

    @Column(name="total_times")
    private Long totalTimes;

    @Column(name="schedule_type")
    private String scheduleType;

    @Column(name="time_of_day")
    private Long timeOfDay;

    @Column(name="habit_completion_type")
    private String habitCompletionType;


    public HabitTransaction(){};

    public HabitTransaction(String name,  Long timeTaken, Date startDate, Date dueDate, Long habitTypeId,  Long userId,
                 Long streak, Long totalTimes, String scheduleType,Long habitId,Long timeOfDay, String habitCompletionType) {
        this.name = name;
        this.timeTaken = timeTaken;
        this.startDate = startDate;
        this.dueDate = dueDate;
        this.habitTypeId = habitTypeId;
        this.userId = userId;
        this.streak = streak;
        this.totalTimes = totalTimes;
        this.scheduleType = scheduleType;
        this.habitId = habitId;
        this.timeOfDay = timeOfDay;
        this.habitCompletionType = habitCompletionType;
    }

}
