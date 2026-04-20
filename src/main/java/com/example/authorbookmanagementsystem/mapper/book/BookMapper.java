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
        Book book = new Book();
        book.setTitle(request.getTitle());
        book.setIsbn(request.getIsbn());
        book.setPublishedDate(request.getPublishedDate());
        book.setPrice(request.getPrice());
        book.setAuthor(author);
        return book;
    }

    // Entity → DTO
    public BookResponse toResponse(Book book) {
        BookResponse response = new BookResponse();
        response.setId(book.getId());
        response.setTitle(book.getTitle());
        response.setIsbn(book.getIsbn());
        response.setPublishedDate(book.getPublishedDate());
        response.setPrice(book.getPrice());
        if (book.getAuthor() != null) {
            response.setAuthor(book.getAuthor().getName());
            response.setAuthorEmail(book.getAuthor().getEmail());
        }
        return response;
    }
}