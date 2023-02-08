package com.bayesiansamaritan.lifeplanner.repository;


import com.bayesiansamaritan.lifeplanner.model.Daily;
import com.bayesiansamaritan.lifeplanner.model.Weekly;
import com.bayesiansamaritan.lifeplanner.model.Yearly;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface YearlyRepository extends JpaRepository<Yearly, Long> {

    Yearly findByReferenceId(String referenceId);

}
