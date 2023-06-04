package com.bayesiansamaritan.lifeplanner.response;

import com.bayesiansamaritan.lifeplanner.enums.TopicTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class TopicResponse {

    private Long id;
    private Date createdAt;
    private Date updatedAt;
    private String name;
    private TopicTypeEnum topicTypeEnum;
    private String paragraph;
    private Set<ItemResponse> itemResponses;

}