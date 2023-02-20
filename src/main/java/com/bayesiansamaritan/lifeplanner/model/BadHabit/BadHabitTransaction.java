package com.bayesiansamaritan.lifeplanner.model.BadHabit;

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
public class BadHabitTransaction {

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

    @Column(name="start_date")
    private Date startDate;

    @Column(name="habit_id")
    private Long habitId;

    @Column(name="habit_type_id")
    private Long habitTypeId;

    @Column(name="user_id")
    private Long userId;

    @Column(name="total_times")
    private Long totalTimes;

    @Column(name="description",length = 10240)
    private String description;

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
