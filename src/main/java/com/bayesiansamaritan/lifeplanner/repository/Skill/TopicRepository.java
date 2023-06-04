package com.bayesiansamaritan.lifeplanner.repository.Skill;

import com.bayesiansamaritan.lifeplanner.model.Item;
import com.bayesiansamaritan.lifeplanner.model.Skill.Skill;
import com.bayesiansamaritan.lifeplanner.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {

    @Query("Select t from Topic t where t.userId=:userId")
    List<Topic> findTopicByUserId(@Param("userId") Long userId);

    @Transactional
    @Modifying
    @Query("update Topic t set t.items=:items where t.userId=:userId and t.id=:topicId")
    public void updateItemByTopic(@Param("userId") Long userId,@Param("topicId") Long topicId, @Param("items") Set<Item> items);

    @Transactional
    @Modifying
    @Query("update Topic t set t.paragraph=:paragraph where t.userId=:userId and t.id=:topicId")
    public void updateParagraphByTopic(@Param("userId") Long userId,@Param("topicId") Long topicId, String paragraph);


}

