package com.bayesiansamaritan.lifeplanner.request.Rule;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CriteriaSetRequest {

    private String name;
    private Set<Long> criteriaIds;
    private Long userId;

}
