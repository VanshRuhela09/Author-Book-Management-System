package com.example.authorbookmanagementsystem.service.author;

import com.example.authorbookmanagementsystem.dto.author.request.AuthorRequest;
import com.example.authorbookmanagementsystem.dto.author.response.AuthorResponse;
import com.example.authorbookmanagementsystem.entity.Author;
import com.example.authorbookmanagementsystem.mapper.author.AuthorMapper;
import com.example.authorbookmanagementsystem.repository.author.AuthorRepository;
import com.example.authorbookmanagementsystem.exception.DuplicateResourceException;
import com.example.authorbookmanagementsystem.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    @Override
    public AuthorResponse createAuthor(AuthorRequest request) {
        // Check for duplicate author by email
        if (authorRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new DuplicateResourceException("Author already exists with email: " + request.getEmail());
        }
        Author author = authorMapper.toEntity(request);
        return authorMapper.toResponse(authorRepository.save(author));
    }

    @Override
    public List<AuthorResponse> getAllAuthors() {
        return authorRepository.findAll()
                .stream()
                .map(authorMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public AuthorResponse updateAuthor(Long id, AuthorRequest request) {

        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + id));
        // Check for duplicate email if changed
        if (!author.getEmail().equals(request.getEmail()) && authorRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new DuplicateResourceException("Author already exists with email: " + request.getEmail());
        }
        author.setName(request.getName());
        author.setEmail(request.getEmail());
        author.setBio(request.getBio());

        return authorMapper.toResponse(authorRepository.save(author));
    }

    @Override
    public void deleteAuthor(Long id) {
        if (!authorRepository.existsById(id)) {
            throw new ResourceNotFoundException("Author not found with id: " + id);
        }
        authorRepository.deleteById(id);
    }

    @Override
    public AuthorResponse getAuthorById(Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + id));
        return authorMapper.toResponse(author);
    }
}