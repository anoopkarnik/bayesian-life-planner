package com.bayesiansamaritan.lifeplanner.repository.Goal;

import com.bayesiansamaritan.lifeplanner.model.BadHabit.BadHabit;
import com.bayesiansamaritan.lifeplanner.model.Goal.BadHabitRule;
import com.bayesiansamaritan.lifeplanner.model.Goal.Goal;
import com.bayesiansamaritan.lifeplanner.model.Goal.HabitRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Repository
public interface BadHabitRuleRepository extends JpaRepository<BadHabitRule, Long> {

    @Query("Select t from BadHabitRule t where t.userId=:userId and t.goalId=:goalId and t.active=:active and t.ruleCategory='CompletedRules'")
    List<BadHabitRule> findCompletedRulesByUserIdAndGoalIdAndActive(@Param("userId") Long userId, @Param("goalId") Long goalId,
                                                                 @Param("active") Boolean active);

    @Query("Select t from BadHabitRule t where t.userId=:userId and t.goalId=:goalId and t.active=:active and t.ruleCategory='WorkRules'")
    List<BadHabitRule> findWorkRulesByUserIdAndGoalIdAndActive(@Param("userId") Long userId, @Param("goalId") Long goalId,
                                                            @Param("active") Boolean active);


}
