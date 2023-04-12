package com.bayesiansamaritan.lifeplanner.request.Stats;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StatsCreateChildRequest {

    private String name;
    private String statsTypeName;
    private float value;
    private String description;
    private String parentStatsName;
    private Boolean active;
}
