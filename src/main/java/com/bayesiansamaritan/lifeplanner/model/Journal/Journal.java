package com.bayesiansamaritan.lifeplanner.model.Journal;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "journal",schema = "life_schema")
@Getter
@Setter
public class Journal {

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

    @Column(name="journal_type_id")
    private Long journalTypeId;
    @Column(name="hidden")
    private Boolean hidden;

    @Column(name="user_id")
    private Long userId;

    @Column(name="text",length = 10240)
    private String text;

    public Journal(){};

    public Journal(String name, Long journalTypeId, Boolean hidden, Long userId, String text) {
        this.name = name;
        this.journalTypeId = journalTypeId;
        this.hidden = hidden;
        this.userId = userId;
        this.text = text;
    }
}
