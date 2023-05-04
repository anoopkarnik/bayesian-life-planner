package com.bayesiansamaritan.lifeplanner.request.Financial;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountRequest {
    private String accountName;
    private Long balance;
    private String name;
    private Boolean liquidity;
    private Boolean freeLiquidity;
}
