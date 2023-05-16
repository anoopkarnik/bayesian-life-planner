package com.bayesiansamaritan.lifeplanner.request.Rule;

import com.bayesiansamaritan.lifeplanner.enums.CriteriaEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CriteriaRequest {

    private String name;
    private Long id;
    private CriteriaEnum criteriaType;
    private String condition;
    private String category;
    private Float weightage;
    private Long value;
    private String categoryName;
    private Long userId;
}
