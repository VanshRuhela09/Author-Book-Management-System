package com.example.authorbookmanagementsystem.repository.role;

import com.example.authorbookmanagementsystem.entity.Role;
import com.example.authorbookmanagementsystem.entity.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(RoleName name);   // important for role mapping
}