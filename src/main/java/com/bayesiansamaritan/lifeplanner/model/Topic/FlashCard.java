package com.bayesiansamaritan.lifeplanner.model.Topic;


import com.bayesiansamaritan.lifeplanner.model.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "flash_card",schema = "topic_schema")
public class FlashCard extends BaseModel {

    @Column(name="question")
    public String question;

    @Column(name="answer",length = 10240)
    public String answer;

    @ManyToMany(mappedBy = "flashCards", cascade = CascadeType.PERSIST)
    private List<SubTopic> subTopics = new ArrayList<>();

    @ManyToMany(mappedBy = "flashCards", cascade = CascadeType.PERSIST)
    private List<Link> links = new ArrayList<>();

}
