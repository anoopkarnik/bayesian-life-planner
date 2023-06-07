package com.bayesiansamaritan.lifeplanner.repository.Rule;

import com.bayesiansamaritan.lifeplanner.enums.CriteriaEnum;
import com.bayesiansamaritan.lifeplanner.model.Rule.Criteria;
import com.bayesiansamaritan.lifeplanner.model.Rule.CriteriaSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
public interface CriteriaSetRepository extends JpaRepository<CriteriaSet, Long> {

    @Query("Select t from CriteriaSet t where t.userId=:userId")
    List<CriteriaSet> findCriteriaSetByUserId(@Param("userId") Long userId);

    @Transactional
    @Modifying
    @Query("update CriteriaSet set name=:name,updated_at=now() where id=:id")
    public void modifyParams(@Param("id") Long id, @Param("name") String name);


}