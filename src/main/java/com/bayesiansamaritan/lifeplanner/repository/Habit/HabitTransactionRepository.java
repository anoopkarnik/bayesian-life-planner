package com.bayesiansamaritan.lifeplanner.repository.Habit;


import com.bayesiansamaritan.lifeplanner.model.Habit.HabitTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface HabitTransactionRepository extends JpaRepository<HabitTransaction, Long> {

}
