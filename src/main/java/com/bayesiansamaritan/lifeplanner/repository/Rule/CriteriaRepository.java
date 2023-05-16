package com.bayesiansamaritan.lifeplanner.repository.Rule;

import com.bayesiansamaritan.lifeplanner.enums.CriteriaEnum;
import com.bayesiansamaritan.lifeplanner.model.Rule.Criteria;
import com.bayesiansamaritan.lifeplanner.model.Rule.TaskRule;
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


}