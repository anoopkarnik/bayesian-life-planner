package com.bayesiansamaritan.lifeplanner.repository.Scheduler;


import com.bayesiansamaritan.lifeplanner.model.Scheduler.Daily;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DailyRepository extends JpaRepository<Daily, Long> {

    Daily findByReferenceId(String referenceId);

}
