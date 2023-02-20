package com.bayesiansamaritan.lifeplanner.repository.Goal;

import com.bayesiansamaritan.lifeplanner.model.Goal.Goal;
import com.bayesiansamaritan.lifeplanner.model.Goal.HabitRule;
import com.bayesiansamaritan.lifeplanner.model.Goal.SkillRule;
import com.bayesiansamaritan.lifeplanner.model.Goal.TaskRule;
import com.bayesiansamaritan.lifeplanner.model.Task.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TaskRuleRepository extends JpaRepository<TaskRule, Long> {

    @Query("Select t from TaskRule t where t.userId=:userId and t.goalId=:goalId and t.active=:active and t.ruleCategory='CompletedRules'")
    List<TaskRule> findCompletedRulesByUserIdAndGoalIdAndActive(@Param("userId") Long userId, @Param("goalId") Long goalId,
                                                                 @Param("active") Boolean active);

    @Query("Select t from TaskRule t where t.userId=:userId and t.goalId=:goalId and t.active=:active and t.ruleCategory='WorkRules'")
    List<TaskRule> findWorkRulesByUserIdAndGoalIdAndActive(@Param("userId") Long userId, @Param("goalId") Long goalId,
                                                            @Param("active") Boolean active);

}
