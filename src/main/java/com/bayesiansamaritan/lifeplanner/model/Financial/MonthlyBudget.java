package com.bayesiansamaritan.lifeplanner.model.Financial;

import com.bayesiansamaritan.lifeplanner.model.Base2Model;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;


@Entity
@Getter
@Setter
@Table(name = "monthly_budget",schema = "financial_schema")
public class MonthlyBudget extends Base2Model {

    @Column(name="cost")
    private Long cost;

    @Column(name="expense_type_id")
    private Long expenseTypeId;

    @Column(name="category_type_id")
    private Long categoryTypeId;

    @Column(name="sub_category_type_id")
    private Long subCategoryTypeId;

    public MonthlyBudget() {
    }

    public MonthlyBudget(Long cost, Long expenseTypeId, Boolean active, Long categoryTypeId, Long subCategoryTypeId, Long userId) {
        this.cost = cost;
        this.expenseTypeId = expenseTypeId;
        this.active = active;
        this.categoryTypeId = categoryTypeId;
        this.subCategoryTypeId = subCategoryTypeId;
        this.userId = userId;
    }
}
