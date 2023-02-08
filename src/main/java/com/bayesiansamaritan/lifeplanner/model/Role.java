package com.bayesiansamaritan.lifeplanner.model;


import com.bayesiansamaritan.lifeplanner.enums.RoleEnum;

import javax.persistence.*;


@Entity
@Table(name = "role",schema = "user_schema")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Enumerated(EnumType.STRING)
    @Column(name="name")
    private RoleEnum name;

    public Role() {
    }

    public Role(RoleEnum name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public RoleEnum getName() {
        return name;
    }

    public void setName(RoleEnum name) {
        this.name = name;
    }
}
