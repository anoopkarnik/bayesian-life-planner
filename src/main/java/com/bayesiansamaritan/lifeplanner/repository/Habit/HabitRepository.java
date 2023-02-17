package com.bayesiansamaritan.lifeplanner.repository.Habit;

import com.bayesiansamaritan.lifeplanner.model.Habit.Habit;
import com.bayesiansamaritan.lifeplanner.model.Skill.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;


@Repository
public interface HabitRepository extends JpaRepository<Habit, Long> {


   @Query("Select t from Habit t where t.userId=:userId and t.active=:active and t.habitTypeId=:habitTypeId  and t.startDate<=now() and t.parentId=0 order by t.dueDate asc")
   List<Habit> findRootHabitByUserIdAndActiveAndHabitTypeId(@Param("userId") Long userId, @Param("active") Boolean active,
                                                 @Param("habitTypeId") Long habitTypeId);

   @Query("Select t from Habit t where t.userId=:userId and t.active=:active and t.startDate<=now() and t.parentId=:parentHabitId order by t.dueDate asc")
   Optional<List<Habit>> findChildHabitsByUserIdAndActiveAndParentHabitId(@Param("userId") Long userId, @Param("active") Boolean active,
                                                                   @Param("parentHabitId") Long parentHabitId);

   void deleteById(Long id);

   Habit findByUserIdAndId(@Param("userId") Long userId, @Param("id") Long id);

   @Query("Select t from Habit t where t.userId=:userId and t.name=:name")
   Habit findByUserIdAndName(@Param("userId") Long userId, @Param("name") String name);

   @Transactional
   @Modifying
   @Query("update Habit set streak=streak+1,totalTimes=totalTimes+1,totalTimeSpent=totalTimeSpent+timeTaken,updated_at=now() where id=:id")
   public void completeHabit(@Param("id") Long id);

   @Transactional
   @Modifying
   @Query("update Habit set streak=0 where id=:id")
   public void removeStreak(@Param("id") Long id);

   @Transactional
   @Modifying
   @Query("update Habit set dueDate=:dueDate,updated_at=now() where id=:id")
   public void modifyDueDate(@Param("id") Long id, @Param("dueDate") Date dueDate);

   @Transactional
   @Modifying
   @Query("update Habit set description=:description,updated_at=now() where id=:id")
   public void addDescription(@Param("id") Long id, @Param("description") String description);

}
