package com.bayesiansamaritan.lifeplanner.repository;

import com.bayesiansamaritan.lifeplanner.model.GoalType;
import com.bayesiansamaritan.lifeplanner.model.GoalType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Repository
public interface GoalTypeRepository extends JpaRepository<GoalType, Long> {

    GoalType findByNameAndUserId(String name,Long userId);
    Optional<GoalType> findById(Long Id);

    @Query(value = "Select h from GoalType h where userId=:userId order by h.count desc")
    List<GoalType> findByUserId(@Param("userId") Long userId);

    @Transactional
    @Modifying
    @Query("update GoalType set name = :name,updated_at=now() where id=:id")
    public void modifyName(@Param("id") Long id, @Param("name") String name);

    @Transactional
    @Modifying
    @Query("update GoalType h set h.count =:count where h.id=:id")
    public void updateCount(@Param("id") Long id, @Param("count") Long count);

}
