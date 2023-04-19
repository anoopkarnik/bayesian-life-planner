package com.bayesiansamaritan.lifeplanner.model.Financial;

import com.bayesiansamaritan.lifeplanner.model.Base2Model;
import com.bayesiansamaritan.lifeplanner.model.BaseModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;


@Entity
@Getter
@Setter
@Table(name = "account",schema = "financial_schema")
public class Account extends Base2Model {

    @Column(name="balance")
    private Long balance;

    @Column(name="account_type_id")
    private Long accountTypeId;


    @Column(name="liquidity")
    private Boolean liquidity;

    @Column(name="free_liquidity")
    private Boolean freeLiquidity;

    public Account() {
    }
    public Account(String name, Long balance, Long accountTypeId, Long userId, Boolean liquidity, Boolean freeLiquidity) {
        this.name = name;
        this.balance = balance;
        this.accountTypeId = accountTypeId;
        this.userId = userId;
        this.liquidity = liquidity;
        this.freeLiquidity = freeLiquidity;
    }
}
