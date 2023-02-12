package com.bayesiansamaritan.lifeplanner.repository;

import com.bayesiansamaritan.lifeplanner.model.Stats;
import com.bayesiansamaritan.lifeplanner.model.Stats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
public interface StatsRepository extends JpaRepository<Stats, Long> {


   @Query("Select t from Stats t where t.userId=:userId and t.statsTypeId=:statsTypeId order by t.updatedAt asc")
   List<Stats> findByUserIdAndStatsTypeId(@Param("userId") Long userId,
                                                    @Param("statsTypeId") Long statsTypeId);
 
   @Transactional
   @Modifying
   @Query("update Stats set description=:description,updated_at=now() where id=:id")
   public void addDescription(@Param("id") Long id, @Param("description") String description);

   @Transactional
   @Modifying
   @Query("update Stats set value=:value,updated_at=now() where id=:id")
   public void addValue(@Param("id") Long id, @Param("value") Float value);

}
