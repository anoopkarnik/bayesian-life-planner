package com.bayesiansamaritan.lifeplanner.repository;


import com.bayesiansamaritan.lifeplanner.model.Daily;
import com.bayesiansamaritan.lifeplanner.model.Weekly;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
public interface WeeklyRepository extends JpaRepository<Weekly, Long> {

    Weekly findByReferenceId(String referenceId);

    @Transactional
    @Modifying
    @Query("update Weekly set referenceId=:referenceId where id=:id")
    public void modifyReferenceId(@Param("id") Long id, @Param("referenceId") String referenceId);

}
