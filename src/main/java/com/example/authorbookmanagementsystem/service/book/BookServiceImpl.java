package com.example.authorbookmanagementsystem.service.book;

import com.example.authorbookmanagementsystem.dto.book.request.BookRequest;
import com.example.authorbookmanagementsystem.dto.book.response.BookResponse;
import com.example.authorbookmanagementsystem.entity.Author;
import com.example.authorbookmanagementsystem.entity.Book;
import com.example.authorbookmanagementsystem.mapper.book.BookMapper;
import com.example.authorbookmanagementsystem.repository.author.AuthorRepository;
import com.example.authorbookmanagementsystem.repository.book.BookRepository;
import com.example.authorbookmanagementsystem.exception.DuplicateResourceException;
import com.example.authorbookmanagementsystem.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final BookMapper bookMapper;

    @Override
    public BookResponse createBook(BookRequest request) {
        log.info("Creating book with ISBN: {}", request.getIsbn());
        // Check for duplicate book by ISBN
        if (bookRepository.findByIsbn(request.getIsbn()).isPresent()) {
            throw new DuplicateResourceException("Book already exists with ISBN: " + request.getIsbn());
        }
        Author author = authorRepository.findById(request.getAuthorId())
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + request.getAuthorId()));

        Book book = bookMapper.toEntity(request, author);
        return bookMapper.toResponse(bookRepository.save(book));
    }

    @Override
    public List<BookResponse> getAllBooks() {
        log.info("Fetching all books");
        return bookRepository.findAll()
                .stream()
                .map(bookMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public BookResponse updateBook(Long id, BookRequest request) {
        log.info("Updating book with id: {}", id);

        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
        // Check for duplicate ISBN if changed
        if (!book.getIsbn().equals(request.getIsbn()) && bookRepository.findByIsbn(request.getIsbn()).isPresent()) {
            throw new DuplicateResourceException("Book already exists with ISBN: " + request.getIsbn());
        }
        book.setTitle(request.getTitle());
        book.setIsbn(request.getIsbn());
        book.setPublishedDate(request.getPublishedDate());
        book.setPrice(request.getPrice());

        return bookMapper.toResponse(bookRepository.save(book));
    }

    @Override
    public void deleteBook(Long id) {
        log.info("Deleting book with id: {}", id);
        if (!bookRepository.existsById(id)) {
            throw new ResourceNotFoundException("Book not found with id: " + id);
        }
        bookRepository.deleteById(id);
    }

    @Override
    public BookResponse getBookById(Long id) {
        log.info("Fetching book by id: {}", id);
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
        return bookMapper.toResponse(book);
    }
}