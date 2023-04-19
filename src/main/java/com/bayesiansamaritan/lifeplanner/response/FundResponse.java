package com.bayesiansamaritan.lifeplanner.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class FundResponse {

    private Long id;
    private Date createdAt;
    private Date updatedAt;
    private String name;
    private Date startDate;
    private String description;
    private Boolean active;
    private Boolean hidden;
    private Boolean completed;
    private Long userId;
    private Long amountAllocated;
    private Long amountNeeded;

    public FundResponse(Long id, Date createdAt, Date updatedAt, String name, Date startDate, String description, Boolean active, Boolean hidden, Boolean completed, Long userId, Long amountAllocated, Long amountNeeded) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.name = name;
        this.startDate = startDate;
        this.description = description;
        this.active = active;
        this.hidden = hidden;
        this.completed = completed;
        this.userId = userId;
        this.amountAllocated = amountAllocated;
        this.amountNeeded = amountNeeded;
    }
}