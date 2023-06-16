package com.bayesiansamaritan.lifeplanner.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class LinkResponse {

    private Long id;
    private Date createdAt;
    private Date updatedAt;
    private String name;
    private String url;
    private String manualSummary;
    private String aiSummary;
    private String transcript;

}