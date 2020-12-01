package com.msa.jrg.userservice.repository;

import com.msa.jrg.userservice.model.Role;
import com.msa.jrg.userservice.model.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(RoleName roleName);
}
