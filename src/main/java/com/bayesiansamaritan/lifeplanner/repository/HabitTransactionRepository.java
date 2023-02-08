package com.bayesiansamaritan.lifeplanner.repository;


import com.bayesiansamaritan.lifeplanner.model.HabitTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface HabitTransactionRepository extends JpaRepository<HabitTransaction, Long> {

}
