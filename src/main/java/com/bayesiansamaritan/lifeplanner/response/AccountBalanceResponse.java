package com.bayesiansamaritan.lifeplanner.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountBalanceResponse {

    private Long id;
    private String name;
    private Long balance;
}
