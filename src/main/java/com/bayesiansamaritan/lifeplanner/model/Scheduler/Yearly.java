package com.bayesiansamaritan.lifeplanner.model.Scheduler;


import com.bayesiansamaritan.lifeplanner.model.BaseModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "yearly",schema = "scheduler")
@Getter
@Setter
public class Yearly extends BaseModel {

    @Column(name = "every")
    private Long every;

    @Column(name="reference_id")
    private String referenceId;

    public Yearly() {
    }

    public Yearly(Long every, String referenceId) {
        this.every = every;
        this.referenceId = referenceId;
    }
}

