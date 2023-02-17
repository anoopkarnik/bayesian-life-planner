package com.bayesiansamaritan.lifeplanner.model.Goal;

import com.bayesiansamaritan.lifeplanner.model.Habit.HabitRule;
import com.bayesiansamaritan.lifeplanner.model.Skill.SkillRule;
import com.bayesiansamaritan.lifeplanner.model.Task.TaskRule;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "goal",schema = "life_schema")
@Getter
@Setter
@NoArgsConstructor
public class Goal {

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

    @Column(name="time_taken")
    private Long timeTaken;

    @Column(name="start_date")
    private Date startDate;

    @Column(name="due_date")
    private Date dueDate;

    @Column(name="habit_type_id")
    private Long goalTypeId;

    @Column(name="active")
    private Boolean active;

    @Column(name="completed")
    private Boolean completed;


    @Column(name="user_id")
    private Long userId;

    @Column(name="description",length = 10240)
    private String description;

    @Column(name="parent_id")
    private long parentId=0L;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name="goal_id")
    private Set<HabitRule> habitRules = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name="goal_id")
    private Set<TaskRule> taskRules = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name="goal_id")
    private Set<SkillRule> skillRules = new HashSet<>();

    public Goal(String name, Long timeTaken, Long goalTypeId, Boolean active, Long userId, Boolean completed) {
        this.name = name;
        this.timeTaken = timeTaken;
        this.goalTypeId = goalTypeId;
        this.active = active;
        this.completed = completed;
        this.userId = userId;
    }

    public Goal(String name, Long timeTaken, Long goalTypeId, Boolean active, Long userId, Boolean completed, Long parentId) {
        this.name = name;
        this.timeTaken = timeTaken;
        this.goalTypeId = goalTypeId;
        this.active = active;
        this.completed = completed;
        this.userId = userId;
        this.parentId = parentId;
    }

}
