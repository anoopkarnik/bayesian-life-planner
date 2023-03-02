package com.bayesiansamaritan.lifeplanner.model.Task;

import com.bayesiansamaritan.lifeplanner.model.BaseModel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;


@Entity
@Table(name = "task_type",schema = "commons_schema")
@Getter
@Setter
public class TaskType  extends BaseModel {

    @Column(name="count")
    private Long count=0L;

    public TaskType() {
    }

    public TaskType(String name, Long userId) {
        this.name = name;
        this.userId = userId;
    }
}
