package com.bayesiansamaritan.lifeplanner.model.Skill;

import com.bayesiansamaritan.lifeplanner.model.Base2Model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "skill",schema = "life_schema")
@JsonNaming(value= PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties({"hibernate_lazy_initializer","handler"})
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Skill extends Base2Model {

    @Column(name="time_taken")
    private Long timeTaken;
    @Column(name="skill_type_id")
    private Long skillTypeId;
    @Column(name="parent_id")
    private long parentId=0L;

    public Skill(){};

    public Skill(String name, Long timeTaken, Long skillTypeId, Boolean active, Long userId, Boolean completed) {
        this.name = name;
        this.timeTaken = timeTaken;
        this.skillTypeId = skillTypeId;
        this.active = active;
        this.userId = userId;
        this.completed = completed;
    }

    public Skill(String name, Long timeTaken, Long skillTypeId, Boolean active, Long userId, Boolean completed,long parentId) {
        this.name = name;
        this.timeTaken = timeTaken;
        this.skillTypeId = skillTypeId;
        this.active = active;
        this.userId = userId;
        this.completed = completed;
        this.parentId = parentId;
    }
}
