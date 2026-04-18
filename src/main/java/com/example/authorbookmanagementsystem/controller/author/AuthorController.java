package com.example.authorbookmanagementsystem.controller.author;

import com.example.authorbookmanagementsystem.dto.author.request.AuthorRequest;
import com.example.authorbookmanagementsystem.dto.author.response.AuthorResponse;
import com.example.authorbookmanagementsystem.service.author.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/authors")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @PostMapping
    public ResponseEntity<AuthorResponse> createAuthor(@RequestBody AuthorRequest request) {
        return ResponseEntity.ok(authorService.createAuthor(request));
    }

    @GetMapping
    public ResponseEntity<List<AuthorResponse>> getAllAuthors() {
        return ResponseEntity.ok(authorService.getAllAuthors());
    }
}