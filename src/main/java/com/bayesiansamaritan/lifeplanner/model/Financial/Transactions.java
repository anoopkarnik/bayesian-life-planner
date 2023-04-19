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
@Table(name = "transactions",schema = "financial_schema")
public class Transactions extends Base2Model {

    @Column(name="cost")
    private Long cost;

    @Column(name="expense_type_id")
    private Long expenseTypeId;

    @Column(name="account_type_id")
    private Long accountTypeId;

    @Column(name="category_id")
    private Long categoryId;

    @Column(name="account_id")
    private Long accountId;

    @Column(name="sub_category_id")
    private Long subCategoryId;

    public Transactions() {
    }

    public Transactions(String name, Long cost, Long expenseTypeId, Long userId, Long accountTypeId, Long categoryId, Long accountId, Long subCategoryId, Boolean active) {
        this.name = name;
        this.cost = cost;
        this.expenseTypeId = expenseTypeId;
        this.userId = userId;
        this.accountTypeId = accountTypeId;
        this.categoryId = categoryId;
        this.accountId = accountId;
        this.subCategoryId = subCategoryId;
        this.active = active;
    }

}
