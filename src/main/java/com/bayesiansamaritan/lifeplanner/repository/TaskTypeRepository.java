package com.bayesiansamaritan.lifeplanner.repository;

import com.bayesiansamaritan.lifeplanner.model.TaskType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Repository
public interface TaskTypeRepository extends JpaRepository<TaskType, Long> {

    TaskType findByName(String name);
    Optional<TaskType> findById(Long Id);

    @Transactional
    @Modifying
    @Query("update TaskType set name = :name,updated_at=now() where id=:id")
    public void modifyName(@Param("id") Long id, @Param("name") String name);

}
