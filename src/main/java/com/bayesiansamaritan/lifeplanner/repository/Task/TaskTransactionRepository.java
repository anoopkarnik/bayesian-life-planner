package com.bayesiansamaritan.lifeplanner.repository.Task;


import com.bayesiansamaritan.lifeplanner.model.Habit.HabitTransaction;
import com.bayesiansamaritan.lifeplanner.model.Task.Task;
import com.bayesiansamaritan.lifeplanner.model.Task.TaskTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TaskTransactionRepository extends JpaRepository<TaskTransaction, Long> {

}
