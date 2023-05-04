package com.bayesiansamaritan.lifeplanner.repository.Financial;

import com.bayesiansamaritan.lifeplanner.model.Financial.AccountType;
import com.bayesiansamaritan.lifeplanner.model.Task.TaskType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Repository
public interface AccountTypeRepository extends JpaRepository<AccountType, Long> {

    AccountType findByNameAndUserId(String name,Long userId);
    Optional<AccountType> findById(Long Id);

    @Transactional
    @Modifying
    @Query("update AccountType set name = :name,updated_at=now() where id=:id")
    public void modifyName(@Param("id") Long id, @Param("name") String name);

    @Query(value = "Select t from AccountType t where t.userId=:userId order by t.count desc")
    List<AccountType> findByUserId(@Param("userId") Long userId);

    @Transactional
    @Modifying
    @Query(value = "update AccountType t set t.count =:count where t.id=:id")
    public void updateCount(@Param("id") Long id, @Param("count") Long count);

    @Transactional
    @Modifying
    @Query("update AccountType set description=:description,updated_at=now() where id=:id")
    public void modifyParams(@Param("id") Long id, @Param("description") String description);


}
