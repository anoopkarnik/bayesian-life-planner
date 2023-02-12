package com.bayesiansamaritan.lifeplanner.request;

import com.bayesiansamaritan.lifeplanner.enums.DayOfWeek;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JournalCreateRequest {

    private String name;
    private String journalTypeName;
    private Long userId;
    private String text;
    private Boolean hidden;
}
