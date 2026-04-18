package com.example.authorbookmanagementsystem.repository.book;

import com.example.authorbookmanagementsystem.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findByIsbn(String isbn);   // unique book check
}