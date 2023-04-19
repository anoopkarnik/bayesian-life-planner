package com.bayesiansamaritan.lifeplanner.repository.Financial;
import com.bayesiansamaritan.lifeplanner.model.Financial.Income;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IncomeRepository extends JpaRepository<Income, Long> {

    List<Income> findByUserId(Long userId);

    void deleteById(Long id);

}
