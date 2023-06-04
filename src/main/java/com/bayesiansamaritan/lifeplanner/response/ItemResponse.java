package com.bayesiansamaritan.lifeplanner.response;

import com.bayesiansamaritan.lifeplanner.enums.TopicTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class ItemResponse {

    private Long id;
    private Date createdAt;
    private Date updatedAt;
    private String text;
}