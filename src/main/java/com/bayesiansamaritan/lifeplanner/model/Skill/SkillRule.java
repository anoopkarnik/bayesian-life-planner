package com.bayesiansamaritan.lifeplanner.model.Skill;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;


@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "skill_rule",schema = "life_schema")
public class SkillRule {

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

    @Column(name="skill_id")
    private Long skillId;

    @Column(name="active")
    private Boolean active;

    @Column(name="user_id")
    private Long userId;

    @Column(name="value")
    private Long value;
    @Column(name="description",length = 10240)
    private String description;

    public SkillRule(String name, Long skillId, Boolean active, Long userId, Long value) {
        this.name = name;
        this.skillId = skillId;
        this.active = active;
        this.userId = userId;
        this.value = value;
    }
}
