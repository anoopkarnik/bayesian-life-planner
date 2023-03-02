package com.bayesiansamaritan.lifeplanner.repository.Goal;

import com.bayesiansamaritan.lifeplanner.model.Goal.Goal;
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
public interface GoalRepository extends JpaRepository<Goal, Long> {


   @Query("Select t from Goal t where t.userId=:userId and t.active=:active and t.goalTypeId=:goalTypeId and t.parentId=0 order by t.updatedAt desc")
   List<Goal> findRootGoalsByUserIdAndActiveAndGoalTypeId(@Param("userId") Long userId, @Param("active") Boolean active,
                                                 @Param("goalTypeId") Long goalTypeId);

   @Query("Select t from Goal t where t.userId=:userId and t.active=:active and t.parentId=:parentGoalId order by t.updatedAt desc")
   Optional<List<Goal>> findGoalsByUserIdAndActiveAndParentGoalId(@Param("userId") Long userId, @Param("active") Boolean active,
                                                                     @Param("parentGoalId") Long parentGoalId);

   @Query("Select t from Goal t where t.userId=:userId and t.name=:name")
   Goal findByUserIdAndName(@Param("userId") Long userId, @Param("name") String name);

   @Transactional
   @Modifying
   @Query("update Goal set completedPercentage=:completedPercentage,updated_at=now() where id=:id")
   public void modifyCompletedPercentage(@Param("id") Long id, @Param("completedPercentage") Float completedPercentage);

   @Transactional
   @Modifying
   @Query("update Goal set childPercentage=:childPercentage,updated_at=now() where id=:id")
   public void modifyChildPercentage(@Param("id") Long id, @Param("childPercentage") Float childPercentage);

   @Transactional
   @Modifying
   @Query("update Goal set workPercentage=:workPercentage,updated_at=now() where id=:id")
   public void modifyWorkPercentage(@Param("id") Long id, @Param("workPercentage") Float workPercentage);
   @Transactional
   @Modifying
   @Query("update Goal set timeRemainingPercentage=:timeRemainingPercentage,updated_at=now() where id=:id")
   public void modifyTimeRemainingPercentage(@Param("id") Long id, @Param("timeRemainingPercentage") Float timeRemainingPercentage);

   void deleteById(Long id);
   @Transactional
   @Modifying
   @Query("update Goal set name=:name,startDate=:startDate,description=:description,active=:active,hidden=:hidden,completed=:completed" +
           ",dueDate=:dueDate,timeTaken=:timeTaken,updated_at=now()  where id=:id")
   public void modifyParams(@Param("id") Long id, @Param("name") String name, @Param("startDate") Date startDate, @Param("description") String description,
                            @Param("active") Boolean active, @Param("hidden") Boolean hidden, @Param("completed") Boolean completed,
                            @Param("dueDate") Date dueDate,@Param("timeTaken") Long timeTaken);

   @Transactional
   @Modifying
   @Query("update Goal set completed=:completed,updated_at=now() where id=:id")
   public void completeGoal(@Param("id") Long id, @Param("completed") Boolean completed);
}
