package com.bayesiansamaritan.lifeplanner.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class SkillResponse {

    private Long id;
    private Date createdAt;
    private Date updatedAt;
    private String name;
    private Long timeTaken;
    private String skillTypeName;

    private Boolean completed;
    private String description;


    public SkillResponse(Long id, Date createdAt, Date updatedAt, String name, Long timeTaken, String skillTypeName, Boolean completed, String description) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.name = name;
        this.timeTaken = timeTaken;
        this.skillTypeName = skillTypeName;
        this.completed = completed;
        this.description = description;
    }
}