package com.bayesiansamaritan.lifeplanner.model.Financial;

import com.bayesiansamaritan.lifeplanner.model.Base2Model;
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
@Table(name = "sub_category_type",schema = "commons_schema")
public class SubCategoryType extends BaseModel {
    @Column(name="count")
    private Long count=0L;

    public SubCategoryType() {
    }

    public SubCategoryType(String name, Long userId) {

        this.name = name;
        this.userId = userId;
    }

}
