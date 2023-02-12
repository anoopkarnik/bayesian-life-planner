package com.bayesiansamaritan.lifeplanner.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "bad_habit",schema = "life_schema")
public class BadHabit {

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

    @Column(name="start_date")
    private Date startDate;

    @Column(name="habit_type_id")
    private Long habitTypeId;

    @Column(name="active")
    private Boolean active;

    @Column(name="user_id")
    private Long userId;

    @Column(name="total_times")
    private Long totalTimes;

    @Column(name="description",length = 10240)
    private String description;

    public BadHabit(){};

    public BadHabit(String name, Date startDate, Long habitTypeId, Boolean active, Long userId, Long totalTimes) {
        this.name = name;
        this.startDate = startDate;
        this.habitTypeId = habitTypeId;
        this.active = active;
        this.userId = userId;
        this.totalTimes = totalTimes;
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Long getHabitTypeId() {
        return habitTypeId;
    }

    public void setHabitTypeId(Long habitTypeId) {
        this.habitTypeId = habitTypeId;
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

    public Long getTotalTimes() {
        return totalTimes;
    }

    public void setTotalTimes(Long totalTimes) {
        this.totalTimes = totalTimes;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
