package com.bayesiansamaritan.lifeplanner.model.Rule;

import com.bayesiansamaritan.lifeplanner.enums.CriteriaEnum;
import com.bayesiansamaritan.lifeplanner.model.Base2Model;
import com.bayesiansamaritan.lifeplanner.model.User.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "criteria_set",schema = "life_schema")
public class CriteriaSet extends Base2Model {

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name="criteria_set_to_criteria",
            joinColumns = @JoinColumn(name="criteria_set_id"),
            inverseJoinColumns = @JoinColumn(name="criteria_id"))
    private Set<Criteria> criteriaList = new HashSet<>();

    public CriteriaSet(String name, Set<Criteria> criteriaList) {
        this.name = name;
        this.criteriaList = criteriaList;
    }
}
