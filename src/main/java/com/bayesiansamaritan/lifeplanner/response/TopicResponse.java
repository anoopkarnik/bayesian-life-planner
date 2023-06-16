package com.bayesiansamaritan.lifeplanner.response;

import com.bayesiansamaritan.lifeplanner.enums.TopicTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class TopicResponse {

    private Long id;
    private Date createdAt;
    private Date updatedAt;
    private String name;
    private String skillTypeName;
    private String summary;
    private String description;
    private Set<LinkResponse> linkResponses;
    private Set<SubTopicResponse> subTopicResponses;

}