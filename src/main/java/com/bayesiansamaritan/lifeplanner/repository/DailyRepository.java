package com.bayesiansamaritan.lifeplanner.repository;


import com.bayesiansamaritan.lifeplanner.model.Daily;
import com.bayesiansamaritan.lifeplanner.model.HabitTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DailyRepository extends JpaRepository<Daily, Long> {

    Daily findByReferenceId(String referenceId);

}
