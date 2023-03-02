package com.bayesiansamaritan.lifeplanner.repository.Rule;

import com.bayesiansamaritan.lifeplanner.model.Rule.TaskRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
public interface TaskRuleRepository extends JpaRepository<TaskRule, Long> {

    @Query("Select t from TaskRule t where t.userId=:userId and t.goalId=:goalId and t.active=:active and t.ruleCategory='CompletedRules'")
    List<TaskRule> findCompletedRulesByUserIdAndGoalIdAndActive(@Param("userId") Long userId, @Param("goalId") Long goalId,
                                                                 @Param("active") Boolean active);

    @Query("Select t from TaskRule t where t.userId=:userId and t.goalId=:goalId and t.active=:active and t.ruleCategory='WorkRules'")
    List<TaskRule> findWorkRulesByUserIdAndGoalIdAndActive(@Param("userId") Long userId, @Param("goalId") Long goalId,
                                                            @Param("active") Boolean active);

    @Transactional
    @Modifying
    @Query("update BadHabit set active=:active where id=:id")
    public void modifyActiveStatus(@Param("id") Long id, @Param("active") Boolean active);

}
