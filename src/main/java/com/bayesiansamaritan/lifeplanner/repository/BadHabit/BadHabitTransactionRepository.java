package com.bayesiansamaritan.lifeplanner.repository.BadHabit;


import com.bayesiansamaritan.lifeplanner.model.BadHabit.BadHabitTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;


@Repository
public interface BadHabitTransactionRepository extends JpaRepository<BadHabitTransaction, Long> {

    @Query("Select t from BadHabitTransaction t where updatedAt>=:updatedAt and habitId=:habitId")
    List<BadHabitTransaction> getBadHabitTransactionsByHabitIdAndUpdatedAt( @Param("habitId") Long habitId,@Param("updatedAt") Date updatedAt);

}
