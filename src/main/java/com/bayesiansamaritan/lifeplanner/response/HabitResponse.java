package com.bayesiansamaritan.lifeplanner.response;

import com.bayesiansamaritan.lifeplanner.enums.DayOfWeek;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class HabitResponse {

    private Long id;
    private Date createdAt;
    private Date updatedAt;
    private String name;
    private String scheduleType;
    private Long timeOfDay;
    private Long timeTaken;
    private Date startDate;
    private Date dueDate;
    private String habitTypeName;
    private Long streak;
    private Long totalTimes;
    private String description;
    private List<HabitResponse> habitResponses;
    private Boolean active;
    private Boolean hidden;
    private Boolean completed;
    private Long every;
    private List<String> daysOfWeek;

    public HabitResponse(Long id, Date createdAt, Date updatedAt, String name, String scheduleType, Long timeTaken,
                         Date startDate, Date dueDate, String habitTypeName, String description, Long streak,
                         Long totalTimes,Boolean active, Boolean hidden, Boolean completed,Long timeOfDay,Long every) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.name = name;
        this.scheduleType = scheduleType;
        this.timeTaken = timeTaken;
        this.startDate = startDate;
        this.dueDate = dueDate;
        this.habitTypeName = habitTypeName;
        this.description = description;
        this.streak = streak;
        this.totalTimes = totalTimes;
        this.active = active;
        this.hidden = hidden;
        this.completed = completed;
        this.timeOfDay=timeOfDay;
        this.every = every;
    }

    public HabitResponse(Long id, Date createdAt, Date updatedAt, String name, String scheduleType, Long timeTaken,
                         Date startDate, Date dueDate, String habitTypeName, String description, Long streak,
                         Long totalTimes,Boolean active, Boolean hidden, Boolean completed,Long timeOfDay,Long every,List<String> daysOfWeek) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.name = name;
        this.scheduleType = scheduleType;
        this.timeTaken = timeTaken;
        this.startDate = startDate;
        this.dueDate = dueDate;
        this.habitTypeName = habitTypeName;
        this.description = description;
        this.streak = streak;
        this.totalTimes = totalTimes;
        this.active = active;
        this.hidden = hidden;
        this.completed = completed;
        this.timeOfDay=timeOfDay;
        this.every = every;
        this.daysOfWeek = daysOfWeek;
    }
}