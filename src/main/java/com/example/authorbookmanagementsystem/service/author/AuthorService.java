package com.example.authorbookmanagementsystem.service.author;

import com.example.authorbookmanagementsystem.dto.author.request.AuthorRequest;
import com.example.authorbookmanagementsystem.dto.author.response.AuthorResponse;

import java.util.List;

public interface AuthorService {

    AuthorResponse createAuthor(AuthorRequest request);

    List<AuthorResponse> getAllAuthors();
}