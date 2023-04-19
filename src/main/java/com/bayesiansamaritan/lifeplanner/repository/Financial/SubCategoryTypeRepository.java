package com.bayesiansamaritan.lifeplanner.repository.Financial;

import com.bayesiansamaritan.lifeplanner.model.Financial.SubCategoryType;
import com.bayesiansamaritan.lifeplanner.model.Financial.SubCategoryType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubCategoryTypeRepository extends JpaRepository<SubCategoryType, Long> {
  SubCategoryType findByNameAndUserId(String name, Long userId);
  Optional<SubCategoryType> findById(Long Id);

  @Transactional
  @Modifying
  @Query("update SubCategoryType set name = :name,updated_at=now() where id=:id")
  public void modifyName(@Param("id") Long id, @Param("name") String name);

  @Query(value = "Select t from SubCategoryType t where t.userId=:userId order by t.count desc")
  List<SubCategoryType> findByUserId(@Param("userId") Long userId);

  @Transactional
  @Modifying
  @Query(value = "update SubCategoryType t set t.count =:count where t.id=:id")
  public void updateCount(@Param("id") Long id, @Param("count") Long count);

}
