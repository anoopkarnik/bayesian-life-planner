package com.bayesiansamaritan.lifeplanner.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "stats_transcription",schema = "life_schema")
public class StatsTranscription {

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

    @Column(name="user_id")
    private Long userId;

    @Column(name="value")
    private float value;

    @Column(name="description",length = 10240)
    private String description;

    public StatsTranscription(){};

    public StatsTranscription(String name, Long statsTypeId, Long userId, float value, String description) {
        this.name = name;
        this.statsTypeId = statsTypeId;
        this.userId = userId;
        this.value = value;
        this.description = description;
    }

    public long getId() {
        return id;
    }


    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getStatsTypeId() {
        return statsTypeId;
    }

    public void setStatsTypeId(Long statsTypeId) {
        this.statsTypeId = statsTypeId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
