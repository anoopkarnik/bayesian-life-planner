package com.bayesiansamaritan.lifeplanner.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StatsCreateRequest {

    private String name;
    private String statsTypeName;
    private Long userId;
    private Float value;
    private String description;
}
