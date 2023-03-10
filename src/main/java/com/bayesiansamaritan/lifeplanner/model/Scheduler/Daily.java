package com.bayesiansamaritan.lifeplanner.model.Scheduler;


import com.bayesiansamaritan.lifeplanner.model.BaseModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "daily",schema = "scheduler")
@Getter
@Setter
public class Daily extends BaseModel {

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
}

