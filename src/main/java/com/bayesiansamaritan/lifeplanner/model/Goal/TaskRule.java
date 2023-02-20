package com.bayesiansamaritan.lifeplanner.model.Goal;

import com.bayesiansamaritan.lifeplanner.enums.HabitEnum;
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
@Table(name = "task_rule",schema = "life_schema")
public class TaskRule {

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

    @Column(name="task_id")
    private Long taskId;

    @Column(name="goal_id")
    private Long goalId;

    @Column(name="active")
    private Boolean active;

    @Column(name="user_id")
    private Long userId;

    @Column(name="task_condition_type")
    private TaskEnum taskConditionType;

    @Column(name="value")
    private Long value;
    @Column(name="weightage")
    private Float weightage;
    @Column(name="description",length = 10240)
    private String description;
    @Column(name="rule_category")
    private String ruleCategory;

    public TaskRule(String name, Long taskId, Long goalId, Boolean active, Long userId, TaskEnum taskConditionType, Long value,Float weightage,
                    String ruleCategory) {
        this.name = name;
        this.taskId = taskId;
        this.goalId = goalId;
        this.active = active;
        this.userId = userId;
        this.taskConditionType = taskConditionType;
        this.value = value;
        this.weightage = weightage;
        this.ruleCategory =ruleCategory;
    }
}
