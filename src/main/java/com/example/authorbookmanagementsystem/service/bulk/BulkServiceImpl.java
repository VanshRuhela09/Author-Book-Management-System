package com.example.authorbookmanagementsystem.service.bulk;

import com.example.authorbookmanagementsystem.entity.Author;
import com.example.authorbookmanagementsystem.entity.Book;
import com.example.authorbookmanagementsystem.repository.author.AuthorRepository;
import com.example.authorbookmanagementsystem.repository.book.BookRepository;
import com.example.authorbookmanagementsystem.file.CsvHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class BulkServiceImpl implements BulkService {
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final CsvHelper csvHelper;

    @Override
    public Map<String, Object> uploadAuthorsCsv(MultipartFile file) {
        List<Author> authors;
        try {
            authors = csvHelper.parseAuthors(file.getInputStream());
        } catch (IOException e) {
            return Map.of("success", false, "error", "Invalid CSV file");
        }
        List<String> duplicates = new ArrayList<>();
        List<Author> saved = new ArrayList<>();
        for (Author author : authors) {
            if (authorRepository.findByEmail(author.getEmail()).isPresent()) {
                duplicates.add(author.getEmail());
            } else {
                saved.add(authorRepository.save(author));
            }
        }
        return Map.of(
            "success", true,
            "inserted", saved.size(),
            "duplicates", duplicates
        );
    }

    @Override
    public Map<String, Object> uploadBooksCsv(MultipartFile file) {
        List<Book> books;
        try {
            books = csvHelper.parseBooks(file.getInputStream(), authorRepository);
        } catch (IOException e) {
            return Map.of("success", false, "error", "Invalid CSV file");
        }
        List<String> duplicates = new ArrayList<>();
        List<Book> saved = new ArrayList<>();
        for (Book book : books) {
            if (bookRepository.findByIsbn(book.getIsbn()).isPresent()) {
                duplicates.add(book.getIsbn());
            } else {
                saved.add(bookRepository.save(book));
            }
        }
        return Map.of(
            "success", true,
            "inserted", saved.size(),
            "duplicates", duplicates
        );
    }

    @Override
    public Resource downloadAuthorsCsv() {
        List<Author> authors = authorRepository.findAll();
        String csv = csvHelper.writeAuthors(authors);
        return new ByteArrayResource(csv.getBytes());
    }

    @Override
    public Resource downloadBooksCsv() {
        List<Book> books = bookRepository.findAll();
        String csv = csvHelper.writeBooks(books);
        return new ByteArrayResource(csv.getBytes());
    }
}

