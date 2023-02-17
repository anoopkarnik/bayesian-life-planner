package com.bayesiansamaritan.lifeplanner.model.BadHabit;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "bad_habit_type",schema = "commons_schema")
@Getter
@Setter
public class BadHabitType {
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

    @Column(name="user_id")
    private Long userId;

    @Column(name="count")
    private Long count=0L;

    public BadHabitType() {
    }

    public BadHabitType(String name, Long userId) {

        this.name = name;
        this.userId = userId;
    }

}
