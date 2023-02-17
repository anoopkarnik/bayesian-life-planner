package com.bayesiansamaritan.lifeplanner.repository.Scheduler;


import com.bayesiansamaritan.lifeplanner.model.Scheduler.Yearly;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface YearlyRepository extends JpaRepository<Yearly, Long> {

    Yearly findByReferenceId(String referenceId);

}
