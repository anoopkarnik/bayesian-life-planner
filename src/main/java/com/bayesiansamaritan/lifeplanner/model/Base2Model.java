package com.bayesiansamaritan.lifeplanner.model;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Setter
@Getter
@MappedSuperclass
public abstract class Base2Model extends BaseModel {

    @Column(name="start_date")
    public Date startDate;
    @Column(name="active")
    public Boolean active;
    @Column(name="hidden")
    public Boolean hidden;
    @Column(name="completed")
    public Boolean completed;
    @Column(name="description",length = 10240)
    public String description;
}
