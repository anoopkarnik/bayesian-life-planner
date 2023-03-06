package com.bayesiansamaritan.lifeplanner.repository.User;

import com.bayesiansamaritan.lifeplanner.model.User.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
  Optional<UserProfile> findByName(String name);

  Boolean existsByName(String name);
  Boolean existsByEmail(String email);

  @Transactional
  @Modifying
  @Query("update UserProfile set password=:password,updated_at=now() where id=:id")
  public void modifyPassword(@Param("id") Long id, @Param("password") String password);

}
