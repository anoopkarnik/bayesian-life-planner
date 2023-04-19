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
@Table(name = "funds",schema = "financial_schema")
public class Fund extends Base2Model {

    @Column(name="amount_allocated")
    private Long amountAllocated;

    @Column(name="amount_needed")
    private Long amountNeeded;

    public Fund() {
    }

    public Fund(String name, Long amountAllocated, Long amountNeeded, Long userId, Boolean active) {
        this.name = name;
        this.amountAllocated = amountAllocated;
        this.amountNeeded = amountNeeded;
        this.userId = userId;
        this.active = active;
    }
}
