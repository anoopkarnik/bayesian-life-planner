package com.bayesiansamaritan.lifeplanner.response;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import java.util.Date;

@Getter
@Setter
public class AccountResponse {

    private Long id;
    private Date createdAt;
    private Date updatedAt;
    private String name;
    private Date startDate;
    private String accountTypeName;
    private String description;
    private Long balance;
    private Boolean liquidity;
    private Boolean freeLiquidity;
    private Boolean active;
    private Boolean hidden;
    private Boolean completed;
    private Long userId;

    public AccountResponse(Long id, Date createdAt, Date updatedAt, String name, Date startDate,String accountTypeName,
                           Long balance,Boolean liquidity, Boolean freeLiquidity,
                           String description, Boolean active, Boolean hidden, Boolean completed, Long userId) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.name = name;
        this.startDate = startDate;
        this.accountTypeName = accountTypeName;
        this.balance = balance;
        this.liquidity = liquidity;
        this.freeLiquidity = freeLiquidity;
        this.description = description;
        this.active = active;
        this.hidden = hidden;
        this.completed = completed;
        this.userId = userId;
    }
}