package com.bayesiansamaritan.lifeplanner.request.Rule;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RulesModifyRequest {

    private Long id;
    private String name;
    private Set<Long> criteriaSetIds;
    private Long userId;

}
