package com.example.authorbookmanagementsystem.service.author;

import com.example.authorbookmanagementsystem.dto.author.request.AuthorRequest;
import com.example.authorbookmanagementsystem.dto.author.response.AuthorResponse;
import com.example.authorbookmanagementsystem.entity.Author;
import com.example.authorbookmanagementsystem.mapper.author.AuthorMapper;
import com.example.authorbookmanagementsystem.repository.author.AuthorRepository;
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
}