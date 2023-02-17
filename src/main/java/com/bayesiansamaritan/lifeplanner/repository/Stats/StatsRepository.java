package com.bayesiansamaritan.lifeplanner.repository.Stats;

import com.bayesiansamaritan.lifeplanner.model.Skill.Skill;
import com.bayesiansamaritan.lifeplanner.model.Stats.Stats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Repository
public interface StatsRepository extends JpaRepository<Stats, Long> {


   @Query("Select t from Stats t where t.userId=:userId and t.statsTypeId=:statsTypeId and t.parentId=0 order by t.updatedAt asc")
   List<Stats> findRootStatsByUserIdAndStatsTypeId(@Param("userId") Long userId,
                                                    @Param("statsTypeId") Long statsTypeId);

   @Query("Select t from Stats t where t.userId=:userId and t.parentId=:parentStatsId order by t.updatedAt asc")
   Optional<List<Stats>> findChildStatsByUserIdAndParentStatsId(@Param("userId") Long userId,
                                                                @Param("parentStatsId") Long parentStatsId);

   @Query("Select t from Stats t where t.userId=:userId and t.name=:name")
   Stats findByUserIdAndName(@Param("userId") Long userId, @Param("name") String name);
 
   @Transactional
   @Modifying
   @Query("update Stats set description=:description,updated_at=now() where id=:id")
   public void addDescription(@Param("id") Long id, @Param("description") String description);

   @Transactional
   @Modifying
   @Query("update Stats set value=:value,updated_at=now() where id=:id")
   public void addValue(@Param("id") Long id, @Param("value") Float value);

}
