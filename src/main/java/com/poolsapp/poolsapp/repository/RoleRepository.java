package com.poolsapp.poolsapp.repository;

import com.poolsapp.poolsapp.model.Role;
import com.poolsapp.poolsapp.model.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName (RoleName rolename);

}
