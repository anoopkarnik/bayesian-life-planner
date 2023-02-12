package com.bayesiansamaritan.lifeplanner.repository;

import com.bayesiansamaritan.lifeplanner.model.Journal;
import com.bayesiansamaritan.lifeplanner.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
public interface JournalRepository extends JpaRepository<Journal, Long> {


   @Query("Select t from Journal t where t.userId=:userId and t.journalTypeId=:journalTypeId order by t.updatedAt asc")
   List<Journal> findByUserIdAndJournalTypeId(@Param("userId") Long userId,
                                                    @Param("journalTypeId") Long journalTypeId);

   @Transactional
   @Modifying
   @Query("update Journal set text=:text,updated_at=now() where id=:id")
   public void addText(@Param("id") Long id, @Param("text") String text);

}
