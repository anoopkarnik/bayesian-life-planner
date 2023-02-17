package com.bayesiansamaritan.lifeplanner.model.BadHabit;

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
public class BadHabit {

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

    @Column(name="bad_habit_type_id")
    private Long badHabitTypeId;

    @Column(name="active")
    private Boolean active;

    @Column(name="user_id")
    private Long userId;

    @Column(name="total_times")
    private Long totalTimes;

    @Column(name="description",length = 10240)
    private String description;

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
