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
@Table(name = "budget_plan",schema = "financial_schema")
public class BudgetPlan extends Base2Model {

    @Column(name="plan_percentage")
    private Long planPercentage;

    @Column(name="expense_type_id")
    private Long expenseTypeId;


    public BudgetPlan() {
    }

    public BudgetPlan(Long planPercentage, Long expenseTypeId, Long userId) {
        this.planPercentage = planPercentage;
        this.expenseTypeId = expenseTypeId;
        this.userId = userId;
    }
}
