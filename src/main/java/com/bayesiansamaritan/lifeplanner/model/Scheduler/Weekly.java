package com.bayesiansamaritan.lifeplanner.model.Scheduler;


import com.bayesiansamaritan.lifeplanner.enums.DayOfWeek;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "weekly",schema = "scheduler")
@Getter
@Setter
public class Weekly {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "every")
    private Long every;

    @Column(name="reference_id")
    private String referenceId;

    @Column(name="days_of_week")
    @ElementCollection(targetClass=DayOfWeek.class)
    private List<DayOfWeek> daysOfWeek;

    public Weekly() {
    }

    public Weekly( Long every, String referenceId, List<DayOfWeek> daysOfWeek) {
        this.every = every;
        this.referenceId = referenceId;
        this.daysOfWeek = daysOfWeek;
    }

}

