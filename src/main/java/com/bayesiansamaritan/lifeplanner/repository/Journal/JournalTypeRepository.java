package com.bayesiansamaritan.lifeplanner.repository.Journal;

import com.bayesiansamaritan.lifeplanner.model.Journal.JournalType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Repository
public interface JournalTypeRepository extends JpaRepository<JournalType, Long> {

    JournalType findByNameAndUserId(String name,Long userId);
    Optional<JournalType> findById(Long Id);

    @Query(value = "Select h from JournalType h where userId=:userId order by h.count desc")
    List<JournalType> findByUserId(@Param("userId") Long userId);

    @Transactional
    @Modifying
    @Query("update JournalType set name = :name,updated_at=now() where id=:id")
    public void modifyName(@Param("id") Long id, @Param("name") String name);

    @Transactional
    @Modifying
    @Query("update JournalType h set h.count =:count where h.id=:id")
    public void updateCount(@Param("id") Long id, @Param("count") Long count);

}
