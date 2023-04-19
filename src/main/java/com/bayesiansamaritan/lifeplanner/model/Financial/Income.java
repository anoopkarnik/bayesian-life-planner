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
@Table(name = "income",schema = "financial_schema")
public class Income extends Base2Model {


    @Column(name="income")
    private Long income;

    public Income() {
    }
    public Income(String name, Long income, Boolean active, Long userId) {
        this.name = name;
        this.income = income;
        this.active = active;
        this.userId = userId;
    }
}
