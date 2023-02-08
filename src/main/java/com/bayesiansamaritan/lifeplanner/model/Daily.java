package com.bayesiansamaritan.lifeplanner.model;


import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "daily",schema = "scheduler")
public class Daily {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "every")
    private Long every;

    @Column(name="reference_id")
    private String referenceId;

    public Daily() {
    }

    public Daily(Long every, String referenceId) {
        this.every = every;
        this.referenceId = referenceId;
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
}

