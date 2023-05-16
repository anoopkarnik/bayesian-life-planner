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
@Table(name = "rule_set",schema = "life_schema")
public class RuleSet extends Base2Model {

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name="rule_set_to_rule",
            joinColumns = @JoinColumn(name="rule_set_id"),
            inverseJoinColumns = @JoinColumn(name="rule_id"))
    private Set<Rule> rules = new HashSet<>();
}
