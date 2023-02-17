package com.bayesiansamaritan.lifeplanner.model.Habit;

import com.bayesiansamaritan.lifeplanner.enums.DayOfWeek;
import com.bayesiansamaritan.lifeplanner.enums.HabitEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "habit_rule",schema = "life_schema")
public class HabitRule {

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

    @Column(name="habit_id")
    private Long habitId;

    @Column(name="active")
    private Boolean active;

    @Column(name="user_id")
    private Long userId;

    @Column(name="value")
    private Long value;
    @Column(name="habit_condition_type")
    private HabitEnum habitConditionType;
    @Column(name="description",length = 10240)
    private String description;

    public HabitRule(String name, Long habitId, Boolean active, Long userId, Long value, HabitEnum habitConditionType) {
        this.name = name;
        this.habitId = habitId;
        this.active = active;
        this.userId = userId;
        this.value = value;
        this.habitConditionType = habitConditionType;
    }
}
