package com.bayesiansamaritan.lifeplanner.repository;

import com.bayesiansamaritan.lifeplanner.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
  Optional<UserProfile> findByName(String name);

  Boolean existsByName(String name);
  Boolean existsByEmail(String email);

}