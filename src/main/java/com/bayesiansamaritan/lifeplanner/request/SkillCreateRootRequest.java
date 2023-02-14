package com.bayesiansamaritan.lifeplanner.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SkillCreateRootRequest {
    private String name;
    private Long timeTaken;
    private String skillTypeName;
    private Long userId;
}
