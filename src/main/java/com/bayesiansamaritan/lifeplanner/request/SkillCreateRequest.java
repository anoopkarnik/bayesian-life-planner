package com.bayesiansamaritan.lifeplanner.request;

import com.bayesiansamaritan.lifeplanner.enums.DayOfWeek;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SkillCreateRequest {

    private String name;
    private Long timeTaken;
    private String skillTypeName;
    private Long userId;
}
