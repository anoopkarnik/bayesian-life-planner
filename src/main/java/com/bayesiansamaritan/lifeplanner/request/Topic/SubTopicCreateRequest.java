package com.bayesiansamaritan.lifeplanner.request.Topic;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SubTopicCreateRequest {

    private Long id;
    private Long topicId;
    private String name;
    private String text;

}
