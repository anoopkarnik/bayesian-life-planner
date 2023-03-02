package com.bayesiansamaritan.lifeplanner.request.Skill;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SkillCreateChildRequest {

    private String name;
    private Long timeTaken;
    private String skillTypeName;
    private String parentSkillName;
    private Boolean active;

}
