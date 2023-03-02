package com.bayesiansamaritan.lifeplanner.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class StatsResponse {

    private Long id;
    private Date createdAt;
    private Date updatedAt;
    private String name;
    private String statsTypeName;
    private Float value;
    private String description;
    private List<StatsResponse> statsResponses;
    private Boolean active;
    private Boolean hidden;
    private Boolean completed;

    public StatsResponse(Long id, Date createdAt, Date updatedAt, String name, String statsTypeName, Float value, String description,Boolean active, Boolean hidden, Boolean completed) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.name = name;
        this.statsTypeName = statsTypeName;
        this.value = value;
        this.description = description;
        this.active = active;
        this.hidden = hidden;
        this.completed = completed;
    }
}