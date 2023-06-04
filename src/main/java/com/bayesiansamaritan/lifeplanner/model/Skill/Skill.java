package com.bayesiansamaritan.lifeplanner.model.Skill;

import com.bayesiansamaritan.lifeplanner.model.Base2Model;
import com.bayesiansamaritan.lifeplanner.model.Topic;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "skill",schema = "life_schema")
public class Skill extends Base2Model {

    @Column(name="time_taken")
    private Long timeTaken;
    @Column(name="skill_type_id")
    private Long skillTypeId;
    @Column(name="parent_id")
    private long parentId=0L;
    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.PERSIST)
    @JoinTable(name="skill_to_topic",joinColumns = @JoinColumn(name="skill_id"),
            inverseJoinColumns = @JoinColumn(name="topic_id"))
    private Set<Topic> topicSet = new HashSet<>();

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
