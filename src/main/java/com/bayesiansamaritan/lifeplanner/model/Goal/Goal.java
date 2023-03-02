package com.bayesiansamaritan.lifeplanner.model.Goal;

import com.bayesiansamaritan.lifeplanner.model.Base2Model;
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
public class Goal extends Base2Model {


    @Column(name="time_taken")
    private Long timeTaken;
    @Column(name="due_date")
    private Date dueDate;
    @Column(name="goal_type_id")
    private Long goalTypeId;
    @Column(name="completed_percentage")
    private Float completedPercentage=0F;
    @Column(name="child_percentage")
    private Float childPercentage=0F;
    @Column(name="work_percentage")
    private Float workPercentage=0F;
    @Column(name="time_remaining_percentage")
    private Float timeRemainingPercentage=0F;
    @Column(name="parent_id")
    private long parentId=0L;

    public Goal(String name, Date dueDate, Long goalTypeId, Boolean active, Long userId, Boolean completed) {
        this.name = name;
        this.dueDate = dueDate;
        this.goalTypeId = goalTypeId;
        this.active = active;
        this.completed = completed;
        this.userId = userId;
    }

    public Goal(String name, Date dueDate, Long goalTypeId, Boolean active, Long userId, Boolean completed, Long parentId) {
        this.name = name;
        this.dueDate = dueDate;
        this.goalTypeId = goalTypeId;
        this.active = active;
        this.completed = completed;
        this.userId = userId;
        this.parentId = parentId;
    }

}
