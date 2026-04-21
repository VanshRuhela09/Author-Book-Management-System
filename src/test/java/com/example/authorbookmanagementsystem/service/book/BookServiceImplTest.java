package com.example.authorbookmanagementsystem.service.book;

import com.example.authorbookmanagementsystem.dto.book.request.BookRequest;
import com.example.authorbookmanagementsystem.dto.book.response.BookResponse;
import com.example.authorbookmanagementsystem.entity.Author;
import com.example.authorbookmanagementsystem.entity.Book;
import com.example.authorbookmanagementsystem.exception.DuplicateResourceException;
import com.example.authorbookmanagementsystem.exception.ResourceNotFoundException;
import com.example.authorbookmanagementsystem.mapper.book.BookMapper;
import com.example.authorbookmanagementsystem.repository.author.AuthorRepository;
import com.example.authorbookmanagementsystem.repository.book.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookServiceImplTest {
    @Mock BookRepository bookRepository;
    @Mock AuthorRepository authorRepository;
    @Mock BookMapper bookMapper;
    @InjectMocks BookServiceImpl bookService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createBook_success() {
        BookRequest request = BookRequest.builder().title("Book").isbn("123").authorId(1L).build();
        Book book = new Book();
        Author author = new Author();
        BookResponse response = new BookResponse();
        when(bookRepository.findByIsbn(request.getIsbn())).thenReturn(Optional.empty());
        when(authorRepository.findById(request.getAuthorId())).thenReturn(Optional.of(author));
        when(bookMapper.toEntity(request, author)).thenReturn(book);
        when(bookRepository.save(book)).thenReturn(book);
        when(bookMapper.toResponse(book)).thenReturn(response);
        BookResponse result = bookService.createBook(request);
        assertNotNull(result);
        verify(bookRepository).save(book);
    }

    @Test
    void createBook_duplicateIsbn_throwsException() {
        BookRequest request = BookRequest.builder().title("Book").isbn("123").authorId(1L).build();
        when(bookRepository.findByIsbn(request.getIsbn())).thenReturn(Optional.of(new Book()));
        assertThrows(DuplicateResourceException.class, () -> bookService.createBook(request));
    }

    @Test
    void createBook_authorNotFound_throwsException() {
        BookRequest request = BookRequest.builder().title("Book").isbn("123").authorId(1L).build();
        when(bookRepository.findByIsbn(request.getIsbn())).thenReturn(Optional.empty());
        when(authorRepository.findById(request.getAuthorId())).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> bookService.createBook(request));
    }

    @Test
    void getAllBooks_success() {
        Book book = new Book();
        BookResponse response = new BookResponse();
        when(bookRepository.findAll()).thenReturn(Collections.singletonList(book));
        when(bookMapper.toResponse(book)).thenReturn(response);
        List<BookResponse> result = bookService.getAllBooks();
        assertEquals(1, result.size());
    }

    @Test
    void deleteBook_notFound_throwsException() {
        when(bookRepository.existsById(1L)).thenReturn(false);
        assertThrows(ResourceNotFoundException.class, () -> bookService.deleteBook(1L));
    }
}

