package com.bayesiansamaritan.lifeplanner.model;


import com.bayesiansamaritan.lifeplanner.enums.TopicTypeEnum;
import com.bayesiansamaritan.lifeplanner.model.Skill.Skill;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Setter
@Getter
@Table(name = "topic",schema = "topic_schema")
@NoArgsConstructor
@AllArgsConstructor
public class Topic extends BaseModel{

    @Enumerated(EnumType.STRING)
    @Column(name="type")
    public TopicTypeEnum topicTypeEnum;

    @ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
    @JoinTable(name="topic_to_item",joinColumns = @JoinColumn(name="topic_id"),
            inverseJoinColumns = @JoinColumn(name="item_id"))
    @Fetch(value= FetchMode.SELECT)
    @Column(name="item")
    public Set<Item> items = new HashSet<>();

    @ManyToMany(mappedBy = "topicSet", cascade = CascadeType.PERSIST)
    private Set<Skill> skills = new HashSet<>();

    @Column(name="paragraph",length = 10240)
    public String paragraph;

    public Topic(TopicTypeEnum topicTypeEnum, Set<Item> items, Long userId, String name) {
        this.topicTypeEnum = topicTypeEnum;
        this.items = items;
        this.userId = userId;
        this.name = name;
    }

    public Topic(TopicTypeEnum topicTypeEnum, String paragraph, Long userId, String name) {
        this.topicTypeEnum = topicTypeEnum;
        this.paragraph = paragraph;
        this.userId = userId;
        this.name = name;
    }

    void addItem(Item item){
        this.items.add(item);
    }

    void removeItem(Item item){
        this.items.remove(item);
    }
}
