package com.bayesiansamaritan.lifeplanner.request.Rule;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RemoveCriteriaSetRequest {

    private Long criteriaSetId;
    private Long ruleId;

}
