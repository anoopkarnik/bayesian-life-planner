package com.bayesiansamaritan.lifeplanner.model.Financial;

import com.bayesiansamaritan.lifeplanner.model.BaseModel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;


@Entity
@Getter
@Setter
@Table(name = "account_type",schema = "commons_schema")
public class AccountType extends BaseModel {

    @Column(name="count")
    private Long count=0L;

    @Column(name="description",length = 10240)
    public String description;

    public AccountType() {
    }

    public AccountType(String name, Long userId) {

        this.name = name;
        this.userId = userId;
    }


}
