package com.bayesiansamaritan.lifeplanner.request.User;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ModifyPasswordRequest {


    private String name;
    private String oldPassword;
    private String newPassword;
}
