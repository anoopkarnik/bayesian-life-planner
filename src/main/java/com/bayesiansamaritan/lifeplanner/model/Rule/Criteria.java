package com.bayesiansamaritan.lifeplanner.model.Rule;

import com.bayesiansamaritan.lifeplanner.enums.BadHabitEnum;
import com.bayesiansamaritan.lifeplanner.enums.CriteriaEnum;
import com.bayesiansamaritan.lifeplanner.model.Base2Model;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "criteria",schema = "life_schema")
public class Criteria extends Base2Model {

    @Enumerated(EnumType.STRING)
    @Column(name="criteria_type")
    private CriteriaEnum criteriaType;
    @Column(name="condition")
    private String condition;
    @Column(name="category")
    private String category;
    @Column(name="weightage")
    private Float weightage;
    @Column(name="value")
    private Long value;
    @Column(name="category_name")
    private String categoryName;

    public Criteria(Long userId, CriteriaEnum criteriaType, String condition, String category, String name, Float weightage, Long value, String categoryName) {
        this.userId = userId;
        this.criteriaType = criteriaType;
        this.condition = condition;
        this.category = category;
        this.name = name;
        this.weightage = weightage;
        this.value = value;
        this.categoryName = categoryName;
    }
}
