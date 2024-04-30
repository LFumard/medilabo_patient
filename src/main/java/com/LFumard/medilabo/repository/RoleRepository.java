package com.LFumard.medilabo.repository;

import com.LFumard.medilabo.model.ERole;
import com.LFumard.medilabo.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
