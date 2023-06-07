package com.bayesiansamaritan.lifeplanner.repository.Rule;

import com.bayesiansamaritan.lifeplanner.model.Rule.Criteria;
import com.bayesiansamaritan.lifeplanner.model.Rule.RuleSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
public interface RuleSetRepository extends JpaRepository<RuleSet, Long> {

    @Query("Select t from RuleSet t where t.userId=:userId")
    List<RuleSet> findRuleSetByUserId(@Param("userId") Long userId);

    @Transactional
    @Modifying
    @Query("update RuleSet set name=:name,updated_at=now() where id=:id")
    public void modifyParams(@Param("id") Long id, @Param("name") String name);

}