package com.bayesiansamaritan.lifeplanner.repository;


import com.bayesiansamaritan.lifeplanner.model.Monthly;
import com.bayesiansamaritan.lifeplanner.model.Weekly;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MonthlyRepository extends JpaRepository<Monthly, Long> {

    Monthly findByReferenceId(String referenceId);

}
