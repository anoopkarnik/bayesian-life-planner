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
@Table(name = "link",schema = "topic_schema")
public class Link extends BaseModel {

    @Column(name="url")
    public String url;

    @Column(name="manual_summary",length = 10240)
    public String manualSummary;

    @Column(name="ai_summary",length = 10240)
    public String aiSummary;

    @Column(name="transcript",length = 50240)
    public String transcript;

    @ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinTable(name="url_to_flashcard",joinColumns = @JoinColumn(name="url_id"),
            inverseJoinColumns = @JoinColumn(name="flashcard_id"))
    @Fetch(value= FetchMode.SELECT)
    @Column(name="flashcards")
    public Set<FlashCard> flashCards = new HashSet<>();

    @ManyToMany(mappedBy = "links", cascade = CascadeType.PERSIST)
    private List<Topic> topics = new ArrayList<>();

    public Link(Long userId,String name,String url, String manualSummary, String aiSummary, String transcript) {
        this.userId = userId;
        this.name = name;
        this.url = url;
        this.manualSummary = manualSummary;
        this.aiSummary = aiSummary;
        this.transcript = transcript;
    }
}
