package com.bayesiansamaritan.lifeplanner.repository.Task;

import com.bayesiansamaritan.lifeplanner.model.Skill.Skill;
import com.bayesiansamaritan.lifeplanner.model.Task.Task;
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
public interface TaskRepository extends JpaRepository<Task, Long> {


   @Query("Select t from Task t where t.userId=:userId and t.active=:active and t.taskTypeId=:taskTypeId and t.startDate<=now() and t.parentId=0 order by t.dueDate asc")
   List<Task> findRootTasksByUserIdAndActiveAndTaskTypeId(@Param("userId") Long userId, @Param("active") Boolean active,
                                                 @Param("taskTypeId") Long taskTypeId);

   @Query("Select t from Task t where t.userId=:userId and t.active=:active and t.taskTypeId=:taskTypeId and t.startDate<=now() order by t.dueDate asc")
   List<Task> findAllTasksByUserIdAndActiveAndTaskTypeId(@Param("userId") Long userId, @Param("active") Boolean active,
                                                          @Param("taskTypeId") Long taskTypeId);

   @Query("Select t from Task t where t.userId=:userId and t.active=:active and t.parentId=:parentTaskId and t.startDate<=now()order by t.dueDate asc")
   Optional<List<Task>> findChildTasksByUserIdAndActiveAndParentTaskId(@Param("userId") Long userId, @Param("active") Boolean active,
                                                                      @Param("parentTaskId") Long parentTaskId);

   void deleteById(Long id);

   Task findByUserIdAndId(@Param("userId") Long userId, @Param("id") Long id);

   @Query("Select t from Task t where t.userId=:userId and t.name=:name")
   Task findByUserIdAndName(@Param("userId") Long userId, @Param("name") String name);

   @Transactional
   @Modifying
   @Query("update Task set active=:active,updated_at=now() where id=:id")
   public void completeTask(@Param("id") Long id, @Param("active") Boolean active);

   @Transactional
   @Modifying
   @Query("update Task set description=:description,updated_at=now() where id=:id")
   public void addDescription(@Param("id") Long id, @Param("description") String description);

   @Transactional
   @Modifying
   @Query("update Task set dueDate=:dueDate,updated_at=now() where id=:id")
   public void modifyDueDate(@Param("id") Long id, @Param("dueDate") Date dueDate);


}
