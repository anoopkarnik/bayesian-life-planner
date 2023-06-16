package com.bayesiansamaritan.lifeplanner.request.Topic;

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

    private String name;
    private String skillTypeName;
    public String description;

}
