package com.bayesiansamaritan.lifeplanner.model.Stats;

import com.bayesiansamaritan.lifeplanner.model.BaseModel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "stats_type",schema = "commons_schema")
@Getter
@Setter
public class StatsType  extends BaseModel {

    @Column(name="count")
    private Long count=0L;

    public StatsType() {
    }

    public StatsType(String name, Long userId) {
        this.name = name;
        this.userId = userId;
    }
}
