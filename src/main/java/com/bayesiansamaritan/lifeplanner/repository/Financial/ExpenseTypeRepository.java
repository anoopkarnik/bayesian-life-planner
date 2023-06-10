package com.bayesiansamaritan.lifeplanner.repository.Financial;

import com.bayesiansamaritan.lifeplanner.model.Financial.ExpenseType;
import com.bayesiansamaritan.lifeplanner.model.Financial.ExpenseType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Repository
public interface ExpenseTypeRepository extends JpaRepository<ExpenseType, Long> {
  ExpenseType findByNameAndUserId(String name, Long userId);
  Optional<ExpenseType> findById(Long Id);

  @Transactional
  @Modifying
  @Query("update ExpenseType set name = :name,updated_at=now() where id=:id")
  public void modifyName(@Param("id") Long id, @Param("name") String name);

  @Query(value = "Select t from ExpenseType t where t.userId=:userId order by t.name")
  List<ExpenseType> findByUserId(@Param("userId") Long userId);

  @Transactional
  @Modifying
  @Query(value = "update ExpenseType t set t.count =:count where t.id=:id")
  public void updateCount(@Param("id") Long id, @Param("count") Long count);

}
