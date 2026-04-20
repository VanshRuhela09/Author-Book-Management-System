package com.example.authorbookmanagementsystem.mapper.book;

import com.example.authorbookmanagementsystem.dto.book.request.BookRequest;
import com.example.authorbookmanagementsystem.dto.book.response.BookResponse;
import com.example.authorbookmanagementsystem.entity.Author;
import com.example.authorbookmanagementsystem.entity.Book;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {

    // DTO → Entity
    public Book toEntity(BookRequest request, Author author) {
        return Book.builder()
                .title(request.getTitle())
                .isbn(request.getIsbn())
                .publishedDate(request.getPublishedDate())
                .price(request.getPrice())
                .author(author) // relation mapping
                .build();
    }

    // Entity → DTO
    public BookResponse toResponse(Book book) {
        String authorName = book.getAuthor() != null ? book.getAuthor().getName() : null;
        return BookResponse.builder()
                .id(book.getId())
                .title(book.getTitle())
                .isbn(book.getIsbn())
                .publishedDate(book.getPublishedDate())
                .price(book.getPrice())
                .author(authorName)
                .build();
    }
}