package com.bayesiansamaritan.lifeplanner.model.Goal;

import com.bayesiansamaritan.lifeplanner.enums.HabitEnum;
import com.bayesiansamaritan.lifeplanner.enums.StatEnum;
import com.bayesiansamaritan.lifeplanner.enums.TaskEnum;
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
@Table(name = "stat_rule",schema = "life_schema")
public class StatRule {

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

    @Column(name="stat_id")
    private Long statId;

    @Column(name="goal_id")
    private Long goalId;

    @Column(name="active")
    private Boolean active;

    @Column(name="user_id")
    private Long userId;
    @Column(name="value")
    private Long value;

    @Column(name="weightage")
    private Float weightage;

    @Column(name="stat_condition_type")
    private StatEnum statConditionType;
    @Column(name="description",length = 10240)
    private String description;
    @Column(name="rule_category")
    private String ruleCategory;
    public StatRule(String name, Long statId, Long goalId, Boolean active, Long userId,  StatEnum statConditionType,Long value,Float weightage,
                    String ruleCategory) {
        this.name = name;
        this.statId = statId;
        this.goalId = goalId;
        this.active = active;
        this.userId = userId;
        this.value = value;
        this.statConditionType = statConditionType;
        this.weightage = weightage;
        this.ruleCategory =ruleCategory;
    }
}
