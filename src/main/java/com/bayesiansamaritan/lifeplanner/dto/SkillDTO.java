package com.bayesiansamaritan.lifeplanner.dto;


import com.bayesiansamaritan.lifeplanner.model.Skill;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(value= PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonIgnoreProperties({"hibernate_lazy_initializer","handler"})
public class SkillDTO {

    private long id;
    private Date createdAt;
    private Date updatedAt;
    private String name;
    private Long timeTaken;
    private Long skillTypeId;
    private Boolean active;
    private Long userId;
    private String description;

    private Boolean completed;

    private Skill parent;

    private Set<Skill> children;

}
