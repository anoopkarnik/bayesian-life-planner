package com.bayesiansamaritan.lifeplanner.model.Topic;


import com.bayesiansamaritan.lifeplanner.model.Base2Model;
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
public class Topic extends Base2Model {

    @Column(name="skill_type_id")
    private Long skillTypeId;

    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(name="topic_to_subtopic",joinColumns = @JoinColumn(name="topic_id"),
            inverseJoinColumns = @JoinColumn(name="subtopic_id"))
    @Fetch(value= FetchMode.SELECT)
    @Column(name="subtopics")
    public Set<SubTopic> subTopics = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(name="topic_to_link",joinColumns = @JoinColumn(name="topic_id"),
            inverseJoinColumns = @JoinColumn(name="link_id"))
    @Fetch(value= FetchMode.SELECT)
    @Column(name="links")
    public Set<Link> links = new HashSet<>();

    @Column(name="summary",length = 10240)
    public String summary;


    public Topic(Long skillTypeId, Long userId, String name, String description) {
        this.skillTypeId = skillTypeId;
        this.userId = userId;
        this.name = name;
        this.description = description;
    }

    public Topic(String summary,Long skillTypeId, Long userId, String name) {
        this.summary = summary;
        this.skillTypeId = skillTypeId;
        this.userId = userId;
        this.name = name;
    }

    void addKeyPoint(SubTopic subTopic){
        this.subTopics.add(subTopic);
    }

    void removeItem(SubTopic subTopic){
        this.subTopics.remove(subTopic);
    }
}
