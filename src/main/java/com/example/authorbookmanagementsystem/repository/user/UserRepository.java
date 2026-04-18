package com.example.authorbookmanagementsystem.repository.user;

import com.example.authorbookmanagementsystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);   // used in security
}