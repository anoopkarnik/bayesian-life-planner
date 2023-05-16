package com.bayesiansamaritan.lifeplanner.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

import com.bayesiansamaritan.lifeplanner.enums.CriteriaEnum;

@Getter
@Setter
@AllArgsConstructor
public class CriteriaResponse {

    private Long id;
    private Date createdAt;
    private Date updatedAt;
    private String name;
    private String condition;
    private String category;
    private CriteriaEnum criteriaType;
    private Boolean active;
    private Long value;
    private String categoryName;
    private Float weightage;

}