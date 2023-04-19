package com.bayesiansamaritan.lifeplanner.repository.Financial;

import com.bayesiansamaritan.lifeplanner.model.Financial.BudgetPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface BudgetPlanRepository extends JpaRepository<BudgetPlan, Long> {
  List<BudgetPlan> findByUserId(Long userId);

  BudgetPlan findByUserIdAndExpenseTypeId(Long userId, Long ExpenseTypeId);

  @Transactional
  @Modifying
  @Query("update BudgetPlan set plan_percentage = :plan_percentage,updated_at=now() where id=:id")
  public void changePlanPercentage(@Param("id") Long id, @Param("plan_percentage") Long plan_percentage);

}
