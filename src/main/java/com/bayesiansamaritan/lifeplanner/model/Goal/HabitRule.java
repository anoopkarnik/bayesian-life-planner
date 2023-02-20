package com.bayesiansamaritan.lifeplanner.model.Goal;

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

    @Column(name="goal_id")
    private Long goalId;

    @Column(name="active")
    private Boolean active;

    @Column(name="user_id")
    private Long userId;

    @Column(name="weightage")
    private Float weightage;
    @Column(name="value")
    private Long value;
    @Column(name="habit_condition_type")
    private HabitEnum habitConditionType;
    @Column(name="rule_category")
    private String ruleCategory;
    @Column(name="description",length = 10240)
    private String description;

    public HabitRule(String name, Long habitId, Long goalId, Boolean active,Long userId, HabitEnum habitConditionType, Long value,Float weightage,
                     String ruleCategory) {
        this.name = name;
        this.habitId = habitId;
        this.goalId = goalId;
        this.active = active;
        this.userId = userId;
        this.value = value;
        this.habitConditionType = habitConditionType;
        this.weightage = weightage;
        this.ruleCategory =ruleCategory;
    }
}
