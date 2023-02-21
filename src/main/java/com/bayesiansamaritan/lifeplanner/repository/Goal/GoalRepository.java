package com.bayesiansamaritan.lifeplanner.repository.Goal;

import com.bayesiansamaritan.lifeplanner.model.Goal.Goal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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

   void deleteById(Long id);

   Goal findByUserIdAndId(@Param("userId") Long userId, @Param("id") Long id);

   @Query("Select t from Goal t where t.userId=:userId and t.name=:name")
   Goal findByUserIdAndName(@Param("userId") Long userId, @Param("name") String name);

   @Transactional
   @Modifying
   @Query("update Goal set completed=true,updated_at=now() where id=:id")
   public void completeGoal(@Param("id") Long id);

   @Transactional
   @Modifying
   @Query("update Goal set description=:description,updated_at=now() where id=:id")
   public void addDescription(@Param("id") Long id, @Param("description") String description);

   @Transactional
   @Modifying
   @Query("update Goal set completedPercentage=:completedPercentage,updated_at=now() where id=:id")
   public void modifyCompletedPercentage(@Param("id") Long id, @Param("completedPercentage") Float completedPercentage);

   @Transactional
   @Modifying
   @Query("update Goal set workPercentage=:workPercentage,updated_at=now() where id=:id")
   public void modifyWorkPercentage(@Param("id") Long id, @Param("workPercentage") Float workPercentage);
}
