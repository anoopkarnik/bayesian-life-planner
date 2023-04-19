package com.bayesiansamaritan.lifeplanner.repository.Financial;
import com.bayesiansamaritan.lifeplanner.model.Financial.MonthlyBudget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Repository
public interface MonthlyBudgetRepository extends JpaRepository<MonthlyBudget, Long> {
  List<MonthlyBudget> findByUserIdAndExpenseTypeId(Long userId,Long ExpenseTypeId);

  void deleteById(Long id);

  @Transactional
  @Modifying
  @Query("update MonthlyBudget set cost = :cost,updated_at=now() where id=:id")
  public void changeCost(@Param("id") Long id, @Param("cost") Long cost);

  @Transactional
  @Modifying
  @Query("update MonthlyBudget set name=:name,startDate=:startDate,description=:description,active=:active,hidden=:hidden,completed=:completed" +
          ",expenseTypeId=:expenseTypeId,categoryTypeId=:categoryTypeId," +
          "subCategoryTypeId=:subCategoryTypeId,cost=:cost,updated_at=now() where id=:id")
  void modifyParams(@Param("id") Long id, @Param("name") String name, @Param("startDate") Date startDate,
                    @Param("description") String description, @Param("active") Boolean active, @Param("hidden") Boolean hidden,
                    @Param("completed") Boolean completed,
                    @Param("expenseTypeId") Long expenseTypeId,@Param("categoryTypeId") Long categoryId,
                    @Param("subCategoryTypeId") Long subCategoryId,@Param("cost") Long cost);



}
