package com.bayesiansamaritan.lifeplanner.request.Skill;

import com.bayesiansamaritan.lifeplanner.enums.TopicTypeEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SkillAddTopicRequest {

    private Long skillId;
    private Long topicId;

}
