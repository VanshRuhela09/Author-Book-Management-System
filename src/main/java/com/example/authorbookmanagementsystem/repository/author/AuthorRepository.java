package com.example.authorbookmanagementsystem.repository.author;

import com.example.authorbookmanagementsystem.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    Optional<Author> findByEmail(String email);   // for duplicate check
}