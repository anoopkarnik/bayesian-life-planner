package com.bayesiansamaritan.lifeplanner.model.BadHabit;

import com.bayesiansamaritan.lifeplanner.model.Base2Model;
import com.bayesiansamaritan.lifeplanner.model.BaseModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "bad_habit",schema = "life_schema")
@Getter
@Setter
@NoArgsConstructor
public class BadHabit  extends Base2Model {
    @Column(name="bad_habit_type_id")
    private Long badHabitTypeId;

    @Column(name="total_times")
    private Long totalTimes;
    @Column(name="parent_id")
    private long parentId=0L;

    public BadHabit(String name, Date startDate, Long badHabitTypeId, Boolean active, Long userId, Long totalTimes) {
        this.name = name;
        this.startDate = startDate;
        this.badHabitTypeId = badHabitTypeId;
        this.active = active;
        this.userId = userId;
        this.totalTimes = totalTimes;
    }

    public BadHabit(String name, Date startDate, Long badHabitTypeId, Boolean active, Long userId, Long totalTimes, Long parentId) {
        this.name = name;
        this.startDate = startDate;
        this.badHabitTypeId = badHabitTypeId;
        this.active = active;
        this.userId = userId;
        this.totalTimes = totalTimes;
        this.parentId = parentId;
    }
}
