package com.example.authorbookmanagementsystem.mapper.author;

import com.example.authorbookmanagementsystem.dto.author.request.AuthorRequest;
import com.example.authorbookmanagementsystem.dto.author.response.AuthorResponse;
import com.example.authorbookmanagementsystem.entity.Author;
import com.example.authorbookmanagementsystem.mapper.book.BookMapper;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class AuthorMapper {

    private final BookMapper bookMapper;

    public AuthorMapper(BookMapper bookMapper) {
        this.bookMapper = bookMapper;
    }

    // DTO → Entity
    public Author toEntity(AuthorRequest request) {
        return Author.builder()
                .name(request.getName())
                .email(request.getEmail())
                .bio(request.getBio())
                .build();
    }

    // Entity → DTO
    public AuthorResponse toResponse(Author author) {
        return AuthorResponse.builder()
                .id(author.getId())
                .name(author.getName())
                .email(author.getEmail())
                .bio(author.getBio())
                .books(author.getBooks() != null ? author.getBooks().stream().map(book -> book.getTitle()).collect(java.util.stream.Collectors.toList()) : null)
                .build();
    }
}