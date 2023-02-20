package com.bayesiansamaritan.lifeplanner.repository.Stats;


import com.bayesiansamaritan.lifeplanner.model.Stats.Stats;
import com.bayesiansamaritan.lifeplanner.model.Stats.StatsTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface StatsTransactionRepository extends JpaRepository<StatsTransaction, Long> {

    @Query("Select t from StatsTransaction t where t.statId=:statId order by t.createdAt asc")
    List<StatsTransaction> findByStatId(@Param("statId") Long statId);

}
