package com.bayesiansamaritan.lifeplanner.repository.Financial;
import com.bayesiansamaritan.lifeplanner.model.BadHabit.BadHabit;
import com.bayesiansamaritan.lifeplanner.model.Financial.Fund;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Repository
public interface FundRepository extends JpaRepository<Fund, Long> {

    List<Fund> findByUserId(Long userId);

    @Query("Select t from Fund t where t.userId=:userId and t.name=:name")
    Fund findByUserIdAndName(@Param("userId") Long userId, @Param("name") String name);

    @Transactional
    @Modifying
    @Query("update Fund set amount_needed = :amountNeeded,updated_at=now() where id=:id")
    public void updateAmountNeeded(@Param("id") Long id, @Param("amountNeeded") Long amountNeeded);

    @Transactional
    @Modifying
    @Query("update Fund set amount_allocated = :amountAllocated,updated_at=now() where id=:id")
    public void updateAmountAllocated(@Param("id") Long id,@Param("amountAllocated") Long amountAllocated);

    @Transactional
    @Modifying
    @Query("update Fund set name=:name,startDate=:startDate,description=:description,active=:active,hidden=:hidden,completed=:completed" +
            ",amountAllocated=:amountAllocated,amountNeeded=:amountNeeded,updated_at=now() where id=:id")
    void modifyParams(@Param("id") Long id, @Param("name") String name, @Param("startDate") Date startDate,
                      @Param("description") String description, @Param("active") Boolean active, @Param("hidden") Boolean hidden,
                      @Param("completed") Boolean completed,@Param("amountAllocated") Long amountAllocated,
                      @Param("amountNeeded") Long amountNeeded);

}
