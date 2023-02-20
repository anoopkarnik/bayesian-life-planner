package com.bayesiansamaritan.lifeplanner.model.Stats;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "stats_transaction",schema = "life_schema")
@Getter
@Setter
public class StatsTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", updatable = false)
    protected Date createdAt;

    @Column(name="name")
    private String name;

    @Column(name="stats_type_id")
    private Long statsTypeId;

    @Column(name="stat_id")
    private Long statId;

    @Column(name="user_id")
    private Long userId;

    @Column(name="value")
    private float value;

    @Column(name="parent_id")
    private long parentId=0L;

    @Column(name="description",length = 10240)
    private String description;

    public StatsTransaction(){};

    public StatsTransaction(String name, Long statsTypeId, Long userId, float value, String description,Long statId) {
        this.name = name;
        this.statsTypeId = statsTypeId;
        this.userId = userId;
        this.value = value;
        this.description = description;
        this.statId = statId;
    }


    public StatsTransaction(String name, Long statsTypeId, Long userId, float value, String description,Long statId, Long parentId) {
        this.name = name;
        this.statsTypeId = statsTypeId;
        this.userId = userId;
        this.value = value;
        this.description = description;
        this.statId = statId;
        this.parentId = parentId;
    }

}
