package com.bayesiansamaritan.lifeplanner.request.Financial;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SubAccountTypeRequest {
    private String accountName;
    private Long balance;
    private String name;
    private Boolean liquidity;
    private Boolean freeLiquidity;
}
