package com.bayesiansamaritan.lifeplanner.model.Goal;

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
    @Column(name="bad_habit_condition_type")
    private BadHabitEnum badHabitConditionType;
    @Column(name="description",length = 10240)
    private String description;
    @Column(name="rule_category")
    private String ruleCategory;

    public BadHabitRule(String name, Long badHabitId, Long goalId, Boolean active, Long userId, BadHabitEnum badHabitConditionType, Long value,Float weightage,
                        String ruleCategory) {
        this.goalId = goalId;
        this.name = name;
        this.badHabitId = badHabitId;
        this.active = active;
        this.userId = userId;
        this.value = value;
        this.badHabitConditionType = badHabitConditionType;
        this.weightage = weightage;
        this.ruleCategory =ruleCategory;
    }
}
