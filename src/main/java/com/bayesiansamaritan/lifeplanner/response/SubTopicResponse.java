package com.bayesiansamaritan.lifeplanner.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class SubTopicResponse {

    private Long id;
    private Date createdAt;
    private Date updatedAt;
    private String name;
    private String text;
}