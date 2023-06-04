package com.bayesiansamaritan.lifeplanner.request.Skill;

import com.bayesiansamaritan.lifeplanner.enums.TopicTypeEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TopicCreateRequest {

    private Long id;
    private String name;
    private TopicTypeEnum topicTypeEnum;
    public List<String> items;
    public String paragraph;

}
