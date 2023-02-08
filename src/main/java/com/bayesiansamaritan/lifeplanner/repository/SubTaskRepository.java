package com.bayesiansamaritan.lifeplanner.repository;

import com.bayesiansamaritan.lifeplanner.model.SubTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
public interface SubTaskRepository extends JpaRepository<SubTask, Long> {


   @Query("Select t from SubTask t where t.userId=:userId and t.active=:active and t.taskId=:taskId  and t.startDate<=now() order by t.dueDate asc")
   List<SubTask> findByUserIdAndActiveAndTaskId(@Param("userId") Long userId, @Param("active") Boolean active,
                                                 @Param("taskId") Long taskId);

   void deleteById(Long id);

   SubTask findByUserIdAndId(@Param("userId") Long userId, @Param("id") Long id);

   @Transactional
   @Modifying
   @Query("update SubTask set active=:active,updated_at=now() where id=:id")
   public void completeSubTask(@Param("id") Long id, @Param("active") Boolean active);
}
