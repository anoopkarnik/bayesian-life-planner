package com.bayesiansamaritan.lifeplanner.repository;

import com.bayesiansamaritan.lifeplanner.model.Task;
import com.bayesiansamaritan.lifeplanner.model.TaskType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;


@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {


   @Query("Select t from Task t where t.userId=:userId and t.active=:active and t.taskTypeId=:taskTypeId and t.startDate<=now() order by t.dueDate asc")
   List<Task> findByUserIdAndActiveAndTaskTypeId(@Param("userId") Long userId, @Param("active") Boolean active,
                                                 @Param("taskTypeId") Long taskTypeId);

   void deleteById(Long id);

   Task findByUserIdAndId(@Param("userId") Long userId, @Param("id") Long id);

   @Transactional
   @Modifying
   @Query("update Task set active=:active,updated_at=now() where id=:id")
   public void completeTask(@Param("id") Long id, @Param("active") Boolean active);
}
