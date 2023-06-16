package com.bayesiansamaritan.lifeplanner.model.Topic;


import com.bayesiansamaritan.lifeplanner.model.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

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
@Table(name = "subtopic",schema = "topic_schema")
public class SubTopic extends BaseModel {

    @Column(name="text")
    public String text;

    @ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinTable(name="subtopic_to_flashcard",joinColumns = @JoinColumn(name="subtopic_id"),
            inverseJoinColumns = @JoinColumn(name="flashcard_id"))
    @Fetch(value= FetchMode.SELECT)
    @Column(name="flashcards")
    public Set<FlashCard> flashCards = new HashSet<>();

    @ManyToMany(mappedBy = "subTopics", cascade = CascadeType.PERSIST)
    private List<Topic> topics = new ArrayList<>();

    public SubTopic(Long userId,String name, String text) {
        this.userId = userId;
        this.name = name;
        this.text = text;
    }
}
