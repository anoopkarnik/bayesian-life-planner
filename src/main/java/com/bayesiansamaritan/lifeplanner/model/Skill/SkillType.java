package com.bayesiansamaritan.lifeplanner.model.Skill;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "skill_type",schema = "commons_schema")
@Getter
@Setter
public class SkillType {
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

    @Column(name="user_id")
    private Long userId;

    @Column(name="count")
    private Long count=0L;

    public SkillType() {
    }

    public SkillType(String name, Long userId) {

        this.name = name;
        this.userId = userId;
    }
}
