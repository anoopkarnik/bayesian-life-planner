package com.bayesiansamaritan.lifeplanner.request.BadHabit;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BadHabitModifyRequest {

    private Long id;
    private String name;
    private Date startDate;
    private String description;
    private Boolean active;
    private Boolean hidden;
    private Boolean completed;
    private Long badHabitTypeName;
    private Long totalTimes;
}
