package com.bayesiansamaritan.lifeplanner.model.Rule;

import com.bayesiansamaritan.lifeplanner.model.Base2Model;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "rule",schema = "life_schema")
public class Rule extends Base2Model {

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name="rule_to_criteria_set",
            joinColumns = @JoinColumn(name="rule_id"),
            inverseJoinColumns = @JoinColumn(name="criteria_set_id"))
    private Set<CriteriaSet> criteriaSetList = new HashSet<>();
}
