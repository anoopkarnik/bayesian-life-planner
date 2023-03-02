package com.bayesiansamaritan.lifeplanner.request.Rule;

import com.bayesiansamaritan.lifeplanner.enums.HabitEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RuleCreateRequest {

    private String ruleType;
    private String name;
    private Long id;
    private Long goalId;
    private Long value;
    private Float weightage;
    private String conditionType;
    private String ruleCategory;
}
