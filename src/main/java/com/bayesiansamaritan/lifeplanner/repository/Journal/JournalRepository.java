package com.bayesiansamaritan.lifeplanner.repository.Journal;

import com.bayesiansamaritan.lifeplanner.model.Journal.Journal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
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

   void deleteById(Long id);
   @Transactional
   @Modifying
   @Query("update Journal set name=:name,startDate=:startDate,description=:description,active=:active,hidden=:hidden,completed=:completed" +
           ",text=:text,updated_at=now() where id=:id")
   public void modifyParams(@Param("id") Long id, @Param("name") String name, @Param("startDate") Date startDate, @Param("description") String description,
                            @Param("active") Boolean active, @Param("hidden") Boolean hidden, @Param("completed") Boolean completed,
                            @Param("text") String text);



}
