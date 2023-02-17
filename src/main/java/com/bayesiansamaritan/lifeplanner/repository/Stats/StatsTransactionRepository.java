package com.bayesiansamaritan.lifeplanner.repository.Stats;


import com.bayesiansamaritan.lifeplanner.model.Stats.StatsTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface StatsTransactionRepository extends JpaRepository<StatsTransaction, Long> {

}
