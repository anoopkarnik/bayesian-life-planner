package com.bayesiansamaritan.lifeplanner.repository.Skill;

import com.bayesiansamaritan.lifeplanner.model.Skill.Skill;
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
public interface SkillRepository extends JpaRepository<Skill, Long> {


   @Query("Select t from Skill t where t.userId=:userId and t.active=:active and t.skillTypeId=:skillTypeId and t.parentId=0")
   List<Skill> findRootSkillsByUserIdAndActiveAndSkillTypeId(@Param("userId") Long userId, @Param("active") Boolean active,
                                                             @Param("skillTypeId") Long skillTypeId);

   @Query("Select t from Skill t where t.userId=:userId and t.active=:active and t.skillTypeId=:skillTypeId")
   List<Skill> findAllSkillsByUserIdAndActiveAndSkillTypeId(@Param("userId") Long userId, @Param("active") Boolean active,
                                                            @Param("skillTypeId") Long skillTypeId);

   @Query("Select t from Skill t where t.userId=:userId and t.active=:active and t.parentId=:parentSkillId")
   Optional<List<Skill>> findSkillsByUserIdAndActiveAndParentSkillId(@Param("userId") Long userId, @Param("active") Boolean active,
                                                                     @Param("parentSkillId") Long parentSkillId);

   Skill findByUserIdAndId(@Param("userId") Long userId, @Param("id") Long id);

   @Query("Select t from Skill t where t.userId=:userId and t.name=:name")
   Skill findByUserIdAndName(@Param("userId") Long userId, @Param("name") String name);

   @Transactional
   @Modifying
   @Query("update Skill set completed=:completed,updated_at=now() where id=:id")
   public void completeSkill(@Param("id") Long id, @Param("completed") Boolean completed);

   void deleteById(Long id);

   @Transactional
   @Modifying
   @Query("update Skill set name=:name,startDate=:startDate,description=:description,active=:active,hidden=:hidden,completed=:completed" +
           ",timeTaken=:timeTaken,updated_at=now() where id=:id")
   public void modifyParams(@Param("id") Long id, @Param("name") String name, @Param("startDate") Date startDate, @Param("description") String description,
                            @Param("active") Boolean active, @Param("hidden") Boolean hidden, @Param("completed") Boolean completed,
                            @Param("timeTaken") Long timeTaken);
}
