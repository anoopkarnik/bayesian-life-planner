package com.bayesiansamaritan.lifeplanner.repository.Rule;

import com.bayesiansamaritan.lifeplanner.enums.CriteriaEnum;
import com.bayesiansamaritan.lifeplanner.model.Rule.Criteria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
public interface CriteriaRepository extends JpaRepository<Criteria, Long> {

    @Query("Select t from Criteria t where t.userId=:userId and t.criteriaType=:criteriaType")
    List<Criteria> findCriteriaByUserIdAndCriteriaType(@Param("userId") Long userId, @Param("criteriaType") CriteriaEnum criteriaType);

    @Transactional
    @Modifying
    @Query("update Criteria set name=:name, criteriaType=:criteriaType,condition=:condition,category=:category,weightage=:weightage," +
            "value=:value,categoryName=:categoryName,updated_at=now() where id=:id")
    public void modifyParams(@Param("id") Long id, @Param("name") String name, @Param("criteriaType") CriteriaEnum criteriaType,
                             @Param("condition") String condition, @Param("category") String category, @Param("weightage") Float weightage,
                             @Param("value") Long value, @Param("categoryName") String categoryName);


}