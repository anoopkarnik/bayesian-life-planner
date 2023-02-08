package com.bayesiansamaritan.lifeplanner.repository;

import com.bayesiansamaritan.lifeplanner.model.Habit;
import com.bayesiansamaritan.lifeplanner.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


@Repository
public interface HabitRepository extends JpaRepository<Habit, Long> {


   @Query("Select t from Habit t where t.userId=:userId and t.active=:active and t.habitTypeId=:habitTypeId  and t.startDate<=now() order by t.dueDate asc")
   List<Habit> findByUserIdAndActiveAndHabitTypeId(@Param("userId") Long userId, @Param("active") Boolean active,
                                                 @Param("habitTypeId") Long taskTypeId);

   void deleteById(Long id);

   Habit findByUserIdAndId(@Param("userId") Long userId, @Param("id") Long id);

   @Transactional
   @Modifying
   @Query("update Habit set streak=streak+1,totalTimes=totalTimes+1,updated_at=now() where id=:id")
   public void completeHabit(@Param("id") Long id);

   @Transactional
   @Modifying
   @Query("update Habit set streak=0 where id=:id")
   public void removeStreak(@Param("id") Long id);

   @Transactional
   @Modifying
   @Query("update Habit set dueDate=:dueDate,updated_at=now() where id=:id")
   public void modifyDueDate(@Param("id") Long id, @Param("dueDate") Date dueDate);
}
