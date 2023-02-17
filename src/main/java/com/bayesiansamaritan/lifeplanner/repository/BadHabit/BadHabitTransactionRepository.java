package com.bayesiansamaritan.lifeplanner.repository.BadHabit;


import com.bayesiansamaritan.lifeplanner.model.BadHabit.BadHabitTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BadHabitTransactionRepository extends JpaRepository<BadHabitTransaction, Long> {

}
