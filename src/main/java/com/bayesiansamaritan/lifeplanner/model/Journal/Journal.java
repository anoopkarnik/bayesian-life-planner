package com.bayesiansamaritan.lifeplanner.model.Journal;

import com.bayesiansamaritan.lifeplanner.model.Base2Model;
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
public class Journal extends Base2Model {


    @Column(name="journal_type_id")
    private Long journalTypeId;
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
