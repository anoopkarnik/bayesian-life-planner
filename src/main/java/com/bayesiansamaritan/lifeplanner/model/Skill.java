package com.bayesiansamaritan.lifeplanner.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "skill",schema = "life_schema")
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", updatable = false)
    protected Date createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    protected Date updatedAt;

    @Column(name="name")
    private String name;

    @Column(name="time_taken")
    private Long timeTaken;
    @Column(name="skill_type_id")
    private Long skillTypeId;

    @Column(name="active")
    private Boolean active;
    @Column(name="user_id")
    private Long userId;
    @Column(name="description",length = 10240)
    private String description;

    @Column(name="sub_skill_type_id")
    private Long subSkillTypeId;

    @Column(name="completed")
    private Boolean completed;

    public Skill(){};

    public Skill(String name, Long timeTaken, Long skillTypeId, Boolean active, Long userId, Boolean completed) {
        this.name = name;
        this.timeTaken = timeTaken;
        this.skillTypeId = skillTypeId;
        this.active = active;
        this.userId = userId;
        this.completed = completed;
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

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getTimeTaken() {
        return timeTaken;
    }

    public void setTimeTaken(Long timeTaken) {
        this.timeTaken = timeTaken;
    }

    public Long getSkillTypeId() {
        return skillTypeId;
    }

    public void setSkillTypeId(Long skillTypeId) {
        this.skillTypeId = skillTypeId;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getSubSkillTypeId() {
        return subSkillTypeId;
    }

    public void setSubSkillTypeId(Long subSkillTypeId) {
        this.subSkillTypeId = subSkillTypeId;
    }
}
