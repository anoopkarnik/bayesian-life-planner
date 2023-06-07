package com.bayesiansamaritan.lifeplanner.repository.Rule;

import com.bayesiansamaritan.lifeplanner.model.Rule.Criteria;
import com.bayesiansamaritan.lifeplanner.model.Rule.Rule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
public interface RuleRepository extends JpaRepository<Rule, Long> {

    @Query("Select t from Rule t where t.userId=:userId")
    List<Rule> findRuleByUserId(@Param("userId") Long userId);

    @Transactional
    @Modifying
    @Query("update Rule set name=:name,updated_at=now() where id=:id")
    public void modifyParams(@Param("id") Long id, @Param("name") String name);


}