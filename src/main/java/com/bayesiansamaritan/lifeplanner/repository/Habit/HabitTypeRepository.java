package com.bayesiansamaritan.lifeplanner.repository.Habit;

import com.bayesiansamaritan.lifeplanner.model.Habit.HabitType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Repository
public interface HabitTypeRepository extends JpaRepository<HabitType, Long> {

    HabitType findByNameAndUserId(String name,Long userId);
    Optional<HabitType> findById(Long Id);

    @Query(value = "Select h from HabitType h where userId=:userId order by h.count desc")
    List<HabitType> findByUserId(@Param("userId") Long userId);

    @Transactional
    @Modifying
    @Query("update HabitType set name = :name,updated_at=now() where id=:id")
    public void modifyName(@Param("id") Long id, @Param("name") String name);

    @Transactional
    @Modifying
    @Query("update HabitType h set h.count =:count where h.id=:id")
    public void updateCount(@Param("id") Long id, @Param("count") Long count);

}
