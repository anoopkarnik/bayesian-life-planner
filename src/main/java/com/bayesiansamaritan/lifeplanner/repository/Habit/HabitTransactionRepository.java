package com.bayesiansamaritan.lifeplanner.repository.Habit;


import com.bayesiansamaritan.lifeplanner.model.Habit.HabitTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;


@Repository
public interface HabitTransactionRepository extends JpaRepository<HabitTransaction, Long> {

    @Query("Select t from HabitTransaction t where t.habitId=:habitId and t.createdAt>=:createdAt")
    List<HabitTransaction> findByHabitIdAndCreatedAt(Long habitId, Date createdAt);

}
