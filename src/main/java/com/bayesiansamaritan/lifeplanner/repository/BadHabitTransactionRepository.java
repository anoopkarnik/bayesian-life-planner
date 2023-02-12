package com.bayesiansamaritan.lifeplanner.repository;


import com.bayesiansamaritan.lifeplanner.model.BadHabitTransaction;
import com.bayesiansamaritan.lifeplanner.model.HabitTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BadHabitTransactionRepository extends JpaRepository<BadHabitTransaction, Long> {

}
