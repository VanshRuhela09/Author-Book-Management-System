package com.example.authorbookmanagementsystem.controller.book;

import com.example.authorbookmanagementsystem.dto.book.request.BookRequest;
import com.example.authorbookmanagementsystem.dto.book.response.BookResponse;
import com.example.authorbookmanagementsystem.service.book.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping
    public ResponseEntity<BookResponse> createBook(@RequestBody BookRequest request) {
        log.info("Creating book: {}", request.getIsbn());
        return ResponseEntity.ok(bookService.createBook(request));
    }

    @GetMapping
    public ResponseEntity<List<BookResponse>> getAllBooks() {
        log.info("Fetching all books");
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponse> getBookById(@PathVariable Long id) {
        log.info("Fetching book by id: {}", id);
        return ResponseEntity.ok(bookService.getBookById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookResponse> updateBook(
            @PathVariable Long id,
            @RequestBody BookRequest request) {
        log.info("Updating book id: {}", id);
        return ResponseEntity.ok(bookService.updateBook(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable Long id) {
        log.info("Deleting book id: {}", id);
        bookService.deleteBook(id);
        return ResponseEntity.ok("Book deleted successfully");
    }

}