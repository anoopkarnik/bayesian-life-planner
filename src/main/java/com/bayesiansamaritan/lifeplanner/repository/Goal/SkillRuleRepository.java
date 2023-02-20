package com.bayesiansamaritan.lifeplanner.repository.Goal;

import com.bayesiansamaritan.lifeplanner.model.BadHabit.BadHabit;
import com.bayesiansamaritan.lifeplanner.model.Goal.HabitRule;
import com.bayesiansamaritan.lifeplanner.model.Goal.SkillRule;
import com.bayesiansamaritan.lifeplanner.model.Skill.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface SkillRuleRepository extends JpaRepository<SkillRule, Long> {

    @Query("Select t from SkillRule t where t.userId=:userId and t.goalId=:goalId and t.active=:active and t.ruleCategory='CompletedRules'")
    List<SkillRule> findCompletedRulesByUserIdAndGoalIdAndActive(@Param("userId") Long userId, @Param("goalId") Long goalId,
                                                                 @Param("active") Boolean active);

    @Query("Select t from SkillRule t where t.userId=:userId and t.goalId=:goalId and t.active=:active and t.ruleCategory='WorkRules'")
    List<SkillRule> findWorkRulesByUserIdAndGoalIdAndActive(@Param("userId") Long userId, @Param("goalId") Long goalId,
                                                            @Param("active") Boolean active);

}
