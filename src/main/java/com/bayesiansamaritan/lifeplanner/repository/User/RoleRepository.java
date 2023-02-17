package com.bayesiansamaritan.lifeplanner.repository.User;

import com.bayesiansamaritan.lifeplanner.enums.RoleEnum;
import com.bayesiansamaritan.lifeplanner.model.User.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
  Optional<Role> findByName(RoleEnum name);

}
