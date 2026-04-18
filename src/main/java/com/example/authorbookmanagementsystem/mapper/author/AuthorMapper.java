package com.example.authorbookmanagementsystem.mapper.author;

import com.example.authorbookmanagementsystem.dto.author.request.AuthorRequest;
import com.example.authorbookmanagementsystem.dto.author.response.AuthorResponse;
import com.example.authorbookmanagementsystem.entity.Author;
import org.springframework.stereotype.Component;

@Component
public class AuthorMapper {

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
                .build();
    }
}