package com.bayesiansamaritan.lifeplanner.repository.Goal;

import com.bayesiansamaritan.lifeplanner.model.Goal.Goal;
import com.bayesiansamaritan.lifeplanner.model.Goal.HabitRule;
import com.bayesiansamaritan.lifeplanner.model.Habit.Habit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface HabitRuleRepository extends JpaRepository<HabitRule, Long> {


    @Query("Select t from HabitRule t where t.userId=:userId and t.goalId=:goalId and t.active=:active and t.ruleCategory='CompletedRules'")
    List<HabitRule> findCompletedRulesByUserIdAndGoalIdAndActive(@Param("userId") Long userId, @Param("goalId") Long goalId,
                                                        @Param("active") Boolean active);

    @Query("Select t from HabitRule t where t.userId=:userId and t.goalId=:goalId and t.active=:active and t.ruleCategory='WorkRules'")
    List<HabitRule> findWorkRulesByUserIdAndGoalIdAndActive(@Param("userId") Long userId, @Param("goalId") Long goalId,
                                                                 @Param("active") Boolean active);

}
