package com.bayesiansamaritan.lifeplanner.repository.BadHabit;

import com.bayesiansamaritan.lifeplanner.model.BadHabit.BadHabitType;
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
public interface BadHabitTypeRepository extends JpaRepository<BadHabitType, Long> {

    BadHabitType findByNameAndUserId(String name,Long userId);
    Optional<BadHabitType> findById(Long Id);

    @Query(value = "Select h from BadHabitType h where userId=:userId order by h.name")
    List<BadHabitType> findByUserId(@Param("userId") Long userId);

    @Transactional
    @Modifying
    @Query("update BadHabitType set name = :name,updated_at=now() where id=:id")
    public void modifyName(@Param("id") Long id, @Param("name") String name);

    @Transactional
    @Modifying
    @Query("update BadHabitType h set h.count =:count where h.id=:id")
    public void updateCount(@Param("id") Long id, @Param("count") Long count);

}
