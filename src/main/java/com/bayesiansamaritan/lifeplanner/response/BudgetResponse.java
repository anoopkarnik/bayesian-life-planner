package com.bayesiansamaritan.lifeplanner.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class BudgetResponse {

    private Long id;
    private Date createdAt;
    private Date updatedAt;
    private String name;
    private Date startDate;
    private String categoryName;
    private String expenseName;
    private String subCategoryName;
    private String description;
    private Long cost;
    private Boolean active;
    private Boolean hidden;
    private Boolean completed;
    private Long userId;
    private Long budgetAmount;
    private Long amountSpent;

    public BudgetResponse(Long id, Date createdAt, Date updatedAt, String name, Date startDate, String categoryName,
                          String expenseName, String subCategoryName,
                          String description, Boolean active, Boolean hidden, Boolean completed, Long cost, Long userId,
                          Long budgetAmount,Long amountSpent) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.name = name;
        this.startDate = startDate;
        this.categoryName = categoryName;
        this.expenseName = expenseName;
        this.subCategoryName = subCategoryName;
        this.description = description;
        this.active = active;
        this.hidden = hidden;
        this.completed = completed;
        this.cost =cost;
        this.userId = userId;
        this.budgetAmount = budgetAmount;
        this.amountSpent = amountSpent;
    }
}