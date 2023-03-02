package com.bayesiansamaritan.lifeplanner.model.Stats;

import com.bayesiansamaritan.lifeplanner.model.Base2Model;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "stats",schema = "life_schema")
@Getter
@Setter
public class Stats extends Base2Model {

    @Column(name="stats_type_id")
    private Long statsTypeId;
    @Column(name="value")
    private float value;
    @Column(name="parent_id")
    private long parentId=0L;

    public Stats(){};

    public Stats(String name, Long statsTypeId, Long userId, float value, String description,Boolean active) {
        this.name = name;
        this.statsTypeId = statsTypeId;
        this.userId = userId;
        this.value = value;
        this.description = description;
        this.active = active;
    }

    public Stats(String name, Long statsTypeId, Long userId, float value, String description, Long parentId,Boolean active) {
        this.name = name;
        this.statsTypeId = statsTypeId;
        this.userId = userId;
        this.value = value;
        this.description = description;
        this.parentId = parentId;
        this.active = active;
    }
}
