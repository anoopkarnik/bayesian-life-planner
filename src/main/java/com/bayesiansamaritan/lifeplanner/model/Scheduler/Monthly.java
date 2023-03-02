package com.bayesiansamaritan.lifeplanner.model.Scheduler;


import com.bayesiansamaritan.lifeplanner.model.BaseModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "monthly",schema = "scheduler")
@Getter
@Setter
public class Monthly extends BaseModel {
    @Column(name = "every")
    private Long every;

    @Column(name="reference_id")
    private String referenceId;

    public Monthly() {
    }

    public Monthly(Long every, String referenceId) {
        this.every = every;
        this.referenceId = referenceId;
    }

}

