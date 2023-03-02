package com.bayesiansamaritan.lifeplanner.model.BadHabit;

import com.bayesiansamaritan.lifeplanner.model.BaseModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "bad_habit_type",schema = "commons_schema")
@Getter
@Setter
public class BadHabitType extends BaseModel {

    @Column(name="count")
    private Long count=0L;

    public BadHabitType() {
    }

    public BadHabitType(String name, Long userId) {

        this.name = name;
        this.userId = userId;
    }

}
