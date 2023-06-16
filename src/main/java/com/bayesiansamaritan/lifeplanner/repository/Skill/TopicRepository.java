package com.bayesiansamaritan.lifeplanner.repository.Skill;

import com.bayesiansamaritan.lifeplanner.model.Topic.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Set;


@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {

    @Query("Select t from Topic t where t.userId=:userId")
    List<Topic> findTopicByUserId(@Param("userId") Long userId);

    @Query("Select t from Topic t where t.userId=:userId and t.skillTypeId=:skillTypeId")
    List<Topic> findTopicByUserIdAndSkillTypeId(@Param("userId") Long userId,@Param("skillTypeId") Long skillTypeId);

    @Transactional
    @Modifying
    @Query("update Topic set name=:name, summary=:summary,description=:description,updated_at=now() where id=:id")
    public void modifyParams(@Param("id") Long id, @Param("name") String name, @Param("summary") String summary, @Param("description") String description);

}

