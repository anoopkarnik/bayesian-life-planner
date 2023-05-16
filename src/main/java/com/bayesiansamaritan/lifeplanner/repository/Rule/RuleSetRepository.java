package com.bayesiansamaritan.lifeplanner.repository.Rule;

import com.bayesiansamaritan.lifeplanner.model.Rule.Criteria;
import com.bayesiansamaritan.lifeplanner.model.Rule.RuleSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface RuleSetRepository extends JpaRepository<RuleSet, Long> {

    @Query("Select t from RuleSet t where t.userId=:userId")
    List<RuleSet> findRuleSetByUserId(@Param("userId") Long userId);

}