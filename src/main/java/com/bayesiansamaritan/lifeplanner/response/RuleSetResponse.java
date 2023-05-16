package com.bayesiansamaritan.lifeplanner.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class RuleSetResponse {

    private Long id;
    private Date createdAt;
    private Date updatedAt;
    private String name;
    private List<RulesResponse> ruleResponses;

}