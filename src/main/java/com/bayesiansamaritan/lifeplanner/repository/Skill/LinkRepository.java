package com.bayesiansamaritan.lifeplanner.repository.Skill;

import com.bayesiansamaritan.lifeplanner.model.Topic.Link;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
public interface LinkRepository extends JpaRepository<Link, Long> {

    @Transactional
    @Modifying
    @Query("update Link set name=:name, url=:url, manualSummary=:manualSummary,updated_at=now() where id=:id")
    public void modifyParams(@Param("id") Long id, @Param("name") String name,@Param("url") String url, @Param("manualSummary") String manualSummary );
}

