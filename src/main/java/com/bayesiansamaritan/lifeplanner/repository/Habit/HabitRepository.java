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


   @Query("Select t from Habit t where t.userId=:userId and t.active=:active and t.habitTypeId=:habitTypeId  and t.startDate<=now() and :currentDate>=t.dueDate order by t.timeOfDay asc")
   List<Habit> findHabitByUserIdAndActiveAndHabitTypeId(@Param("userId") Long userId, @Param("active") Boolean active,
                                                 @Param("habitTypeId") Long habitTypeId, @Param("currentDate") Date currentDate);

   @Query("Select t from Habit t where t.userId=:userId and t.habitTypeId=:habitTypeId order by t.timeOfDay asc")
   List<Habit> findHabitByUserIdAndHabitTypeId(@Param("userId") Long userId, @Param("habitTypeId") Long habitTypeId);

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
   @Query("update Habit set scheduleType=:scheduleType,updated_at=now() where id=:id")
   public void modifyScheduleType(@Param("id") Long id, @Param("scheduleType") String scheduleType);

   void deleteById(Long id);
   @Transactional
   @Modifying
   @Query("update Habit set name=:name,startDate=:startDate,description=:description,active=:active,hidden=:hidden,completed=:completed" +
           ",dueDate=:dueDate,timeTaken=:timeTaken,streak=:streak,totalTimes=:totalTimes," +
           "totalTimeSpent=:totalTimeSpent,timeOfDay=:timeOfDay,updated_at=now() where id=:id")
   public void modifyParams(@Param("id") Long id, @Param("name") String name, @Param("startDate") Date startDate, @Param("description") String description,
                            @Param("active") Boolean active, @Param("hidden") Boolean hidden, @Param("completed") Boolean completed,
                            @Param("dueDate") Date dueDate,@Param("timeTaken") Long timeTaken,
                            @Param("streak") Long streak, @Param("totalTimes") Long totalTimes, @Param("totalTimeSpent") Long totalTimeSpent,
                            @Param("timeOfDay") Long timeOfDay);

}
