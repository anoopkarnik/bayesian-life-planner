package com.bayesiansamaritan.lifeplanner.repository;

import com.bayesiansamaritan.lifeplanner.model.BadHabit;
import com.bayesiansamaritan.lifeplanner.model.Habit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


@Repository
public interface BadHabitRepository extends JpaRepository<BadHabit, Long> {


   @Query("Select t from BadHabit t where t.userId=:userId and t.active=:active and t.habitTypeId=:habitTypeId  and t.startDate<=now() order by t.updatedAt asc")
   List<BadHabit> findByUserIdAndActiveAndHabitTypeId(@Param("userId") Long userId, @Param("active") Boolean active,
                                                 @Param("habitTypeId") Long habitTypeId);

   void deleteById(Long id);

   public BadHabit findByUserIdAndId(@Param("userId") Long userId, @Param("id") Long id);

   @Transactional
   @Modifying
   @Query("update BadHabit set totalTimes=totalTimes+1,updated_at=now() where id=:id")
   public void carriedOutBadHabit(@Param("id") Long id);


   @Transactional
   @Modifying
   @Query("update BadHabit set description=:description where id=:id")
   public void addDescription(@Param("id") Long id, @Param("description") String description);
}
