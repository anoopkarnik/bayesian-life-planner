package com.bayesiansamaritan.lifeplanner.model.BadHabit;

import com.bayesiansamaritan.lifeplanner.enums.BadHabitEnum;
import com.bayesiansamaritan.lifeplanner.enums.HabitEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;


@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "bad_habit_rule",schema = "life_schema")
public class BadHabitRule {

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

    @Column(name="bad_habit_id")
    private Long badHabitId;

    @Column(name="active")
    private Boolean active;

    @Column(name="user_id")
    private Long userId;

    @Column(name="value")
    private Long value;
    @Column(name="bad_habit_condition_type")
    private BadHabitEnum badHabitConditionType;
    @Column(name="description",length = 10240)
    private String description;

    public BadHabitRule(String name, Long habitId, Boolean active, Long userId, Long value, BadHabitEnum badHabitConditionType) {
        this.name = name;
        this.badHabitId = habitId;
        this.active = active;
        this.userId = userId;
        this.value = value;
        this.badHabitConditionType = badHabitConditionType;
    }
}
