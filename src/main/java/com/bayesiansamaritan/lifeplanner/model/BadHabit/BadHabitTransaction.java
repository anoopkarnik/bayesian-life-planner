package com.bayesiansamaritan.lifeplanner.model.BadHabit;

import com.bayesiansamaritan.lifeplanner.model.Base2Model;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "bad_habit_transaction",schema = "life_schema")
@Getter
@Setter
@NoArgsConstructor
public class BadHabitTransaction extends Base2Model {

    @Column(name="habit_id")
    private Long habitId;
    @Column(name="habit_type_id")
    private Long habitTypeId;

    @Column(name="total_times")
    private Long totalTimes;

    public BadHabitTransaction(Long habitId,String name, Date startDate, Long habitTypeId, Long userId, Long totalTimes, String description) {
        this.habitId = habitId;
        this.name = name;
        this.startDate = startDate;
        this.habitTypeId = habitTypeId;
        this.userId = userId;
        this.totalTimes = totalTimes;
        this.description = description;
    }
}
