package com.bayesiansamaritan.lifeplanner.request.Journal;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JournalDescriptionRequest {

    private Long id;
    private String text;
}
