package com.bayesiansamaritan.lifeplanner.repository.Task;

import com.bayesiansamaritan.lifeplanner.model.Task.TaskType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Repository
public interface TaskTypeRepository extends JpaRepository<TaskType, Long> {

    TaskType findByNameAndUserId(String name,Long userId);
    Optional<TaskType> findById(Long Id);

    @Query(value = "Select t from TaskType t where t.userId=:userId order by t.name")
    List<TaskType> findByUserId(@Param("userId") Long userId);

    @Transactional
    @Modifying
    @Query("update TaskType set name = :name,updated_at=now() where id=:id")
    public void modifyName(@Param("id") Long id, @Param("name") String name);

    @Transactional
    @Modifying
    @Query(value = "update TaskType t set t.count =:count where t.id=:id")
    public void updateCount(@Param("id") Long id, @Param("count") Long count);

}
