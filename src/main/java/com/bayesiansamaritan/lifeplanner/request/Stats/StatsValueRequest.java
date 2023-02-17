package com.bayesiansamaritan.lifeplanner.request.Stats;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StatsValueRequest {

    private Long id;
    private Float value;
}
