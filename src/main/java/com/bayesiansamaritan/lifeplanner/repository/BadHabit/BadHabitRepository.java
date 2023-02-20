package com.bayesiansamaritan.lifeplanner.repository.BadHabit;

import com.bayesiansamaritan.lifeplanner.model.BadHabit.BadHabit;
import com.bayesiansamaritan.lifeplanner.model.Skill.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Repository
public interface BadHabitRepository extends JpaRepository<BadHabit, Long> {


   @Query("Select t from BadHabit t where t.userId=:userId and t.active=:active and t.badHabitTypeId=:habitTypeId  and t.startDate<=now() and t.parentId=0 order by t.updatedAt asc")
   List<BadHabit> findRootBadHabitsByUserIdAndActiveAndHabitTypeId(@Param("userId") Long userId, @Param("active") Boolean active,
                                                 @Param("habitTypeId") Long habitTypeId);
   @Query("Select t from BadHabit t where t.userId=:userId and t.active=:active and t.badHabitTypeId=:habitTypeId  and t.startDate<=now() order by t.updatedAt asc")
   List<BadHabit> findAllBadHabitsByUserIdAndActiveAndHabitTypeId(@Param("userId") Long userId, @Param("active") Boolean active,
                                                                   @Param("habitTypeId") Long habitTypeId);

   @Query("Select t from BadHabit t where t.userId=:userId and t.active=:active and  t.startDate<=now() and t.parentId=:parentBadHabitId order by t.updatedAt asc")
   Optional<List<BadHabit>> findChildBadHabitsByUserIdAndActiveAndParentBadHabitId(@Param("userId") Long userId, @Param("active") Boolean active,
                                                                             @Param("parentBadHabitId") Long parentBadHabitId);

   void deleteById(Long id);

   public BadHabit findByUserIdAndId(@Param("userId") Long userId, @Param("id") Long id);
   @Query("Select t from BadHabit t where t.userId=:userId and t.name=:name")
   BadHabit findByUserIdAndName(@Param("userId") Long userId, @Param("name") String name);

   @Transactional
   @Modifying
   @Query("update BadHabit set totalTimes=totalTimes+1,updated_at=now() where id=:id")
   public void carriedOutBadHabit(@Param("id") Long id);


   @Transactional
   @Modifying
   @Query("update BadHabit set description=:description where id=:id")
   public void addDescription(@Param("id") Long id, @Param("description") String description);
}
