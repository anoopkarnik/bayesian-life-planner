package com.bayesiansamaritan.lifeplanner.model;


import com.bayesiansamaritan.lifeplanner.model.Skill.Skill;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "item",schema = "topic_schema")
public class Item extends  BaseModel{

    @Column(name="text")
    public String text;

    @ManyToMany(mappedBy = "items", cascade = CascadeType.PERSIST)
    private List<Topic> topicSet = new ArrayList<>();

    public Item(String text) {
        this.text=text;
    }


}
