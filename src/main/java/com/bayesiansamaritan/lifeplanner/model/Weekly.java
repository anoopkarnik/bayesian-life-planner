package com.bayesiansamaritan.lifeplanner.model;


import com.bayesiansamaritan.lifeplanner.enums.DayOfWeek;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "weekly",schema = "scheduler")
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

    public long getId() {
        return id;
    }

    public Long getEvery() {
        return every;
    }

    public void setEvery(Long every) {
        this.every = every;
    }

    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }

    public List<DayOfWeek> getDaysOfWeek() {
        return daysOfWeek;
    }

    public void setDaysOfWeek(List<DayOfWeek> daysOfWeek) {
        this.daysOfWeek = daysOfWeek;
    }
}

