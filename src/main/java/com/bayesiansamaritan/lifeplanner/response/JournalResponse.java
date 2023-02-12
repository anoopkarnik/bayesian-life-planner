package com.bayesiansamaritan.lifeplanner.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class JournalResponse {

    private Long id;
    private Date createdAt;
    private Date updatedAt;
    private String name;
    private String journalTypeName;
    private String text;

    public JournalResponse(Long id, Date createdAt, Date updatedAt, String name, String journalTypeName, String text) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.name = name;
        this.journalTypeName = journalTypeName;
        this.text = text;
    }
}