package com.example.authorbookmanagementsystem.service.author;

import com.example.authorbookmanagementsystem.dto.author.request.AuthorRequest;
import com.example.authorbookmanagementsystem.dto.author.response.AuthorResponse;
import com.example.authorbookmanagementsystem.entity.Author;
import com.example.authorbookmanagementsystem.exception.DuplicateResourceException;
import com.example.authorbookmanagementsystem.exception.ResourceNotFoundException;
import com.example.authorbookmanagementsystem.mapper.author.AuthorMapper;
import com.example.authorbookmanagementsystem.repository.author.AuthorRepository;
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

class AuthorServiceImplTest {
    @Mock AuthorRepository authorRepository;
    @Mock AuthorMapper authorMapper;
    @InjectMocks AuthorServiceImpl authorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createAuthor_success() {
        AuthorRequest request = AuthorRequest.builder().name("Test").email("test@email.com").bio("bio").build();
        Author author = new Author();
        AuthorResponse response = new AuthorResponse();
        when(authorRepository.findByEmail(request.getEmail())).thenReturn(Optional.empty());
        when(authorMapper.toEntity(request)).thenReturn(author);
        when(authorRepository.save(author)).thenReturn(author);
        when(authorMapper.toResponse(author)).thenReturn(response);
        AuthorResponse result = authorService.createAuthor(request);
        assertNotNull(result);
        verify(authorRepository).save(author);
    }

    @Test
    void createAuthor_duplicateEmail_throwsException() {
        AuthorRequest request = AuthorRequest.builder().name("Test").email("test@email.com").bio("bio").build();
        when(authorRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(new Author()));
        assertThrows(DuplicateResourceException.class, () -> authorService.createAuthor(request));
    }

    @Test
    void getAllAuthors_success() {
        Author author = new Author();
        AuthorResponse response = new AuthorResponse();
        when(authorRepository.findAll()).thenReturn(Collections.singletonList(author));
        when(authorMapper.toResponse(author)).thenReturn(response);
        List<AuthorResponse> result = authorService.getAllAuthors();
        assertEquals(1, result.size());
    }

    @Test
    void updateAuthor_notFound_throwsException() {
        when(authorRepository.findById(1L)).thenReturn(Optional.empty());
        AuthorRequest request = AuthorRequest.builder().name("Test").email("test@email.com").bio("bio").build();
        assertThrows(ResourceNotFoundException.class, () -> authorService.updateAuthor(1L, request));
    }

    @Test
    void deleteAuthor_notFound_throwsException() {
        when(authorRepository.existsById(1L)).thenReturn(false);
        assertThrows(ResourceNotFoundException.class, () -> authorService.deleteAuthor(1L));
    }
}

