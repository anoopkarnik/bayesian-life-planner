package com.bayesiansamaritan.lifeplanner.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class RuleResponse {

    private Long id;
    private Date createdAt;
    private Date updatedAt;
    private String name;
    private Long conditionId;
    private Long goalId;
    private Boolean active;
    private Long value;
    private String conditionType;
    private String description;
    private String ruleType;


}