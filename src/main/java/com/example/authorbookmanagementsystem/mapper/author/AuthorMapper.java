package com.example.authorbookmanagementsystem.mapper.author;

import com.example.authorbookmanagementsystem.dto.author.request.AuthorRequest;
import com.example.authorbookmanagementsystem.dto.author.response.AuthorResponse;
import com.example.authorbookmanagementsystem.entity.Author;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AuthorMapper {

    // DTO → Entity
    public Author toEntity(AuthorRequest request) {
        Author author = new Author();
        author.setName(request.getName());
        author.setEmail(request.getEmail());
        author.setBio(request.getBio());
        return author;
    }

    // Entity → DTO
    public AuthorResponse toResponse(Author author) {
        AuthorResponse response = new AuthorResponse();
        response.setId(author.getId());
        response.setName(author.getName());
        response.setEmail(author.getEmail());
        response.setBio(author.getBio());
        if (author.getBooks() != null) {
            List<AuthorResponse.BookInfo> bookInfos = new java.util.ArrayList<>();
            for (var book : author.getBooks()) {
                AuthorResponse.BookInfo info = new AuthorResponse.BookInfo();
                info.setTitle(book.getTitle());
                info.setIsbn(book.getIsbn());
                bookInfos.add(info);
            }
            response.setBooks(bookInfos);
        }
        return response;
    }
}