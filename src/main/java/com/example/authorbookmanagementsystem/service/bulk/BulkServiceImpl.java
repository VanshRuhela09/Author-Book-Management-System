package com.example.authorbookmanagementsystem.service.bulk;

import com.example.authorbookmanagementsystem.entity.Author;
import com.example.authorbookmanagementsystem.entity.Book;
import com.example.authorbookmanagementsystem.repository.author.AuthorRepository;
import com.example.authorbookmanagementsystem.repository.book.BookRepository;
import com.example.authorbookmanagementsystem.file.CsvHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class BulkServiceImpl implements BulkService {
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final CsvHelper csvHelper;

    @Override
    public Map<String, Object> uploadAuthorsCsv(MultipartFile file) {
        log.info("Uploading authors CSV");
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
        log.info("Uploaded authors CSV: {} inserted, {} duplicates", saved.size(), duplicates.size());
        return Map.of(
            "success", true,
            "inserted", saved.size(),
            "duplicates", duplicates
        );
    }

    @Override
    public Map<String, Object> uploadBooksCsv(MultipartFile file) {
        log.info("Uploading books CSV");
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
        log.info("Uploaded books CSV: {} inserted, {} duplicates", saved.size(), duplicates.size());
        return Map.of(
            "success", true,
            "inserted", saved.size(),
            "duplicates", duplicates
        );
    }

    @Override
    public Resource downloadAuthorsCsv() {
        log.info("Downloading authors CSV");
        List<Author> authors = authorRepository.findAll();
        String csv = csvHelper.writeAuthors(authors);
        return new ByteArrayResource(csv.getBytes());
    }

    @Override
    public Resource downloadBooksCsv() {
        log.info("Downloading books CSV");
        List<Book> books = bookRepository.findAll();
        String csv = csvHelper.writeBooks(books);
        return new ByteArrayResource(csv.getBytes());
    }

    @Override
    public Map<String, Object> bulkUpdateAuthorsCsv(MultipartFile file) {
        log.info("Bulk updating authors from CSV");
        List<Author> authors;
        try {
            authors = csvHelper.parseAuthors(file.getInputStream());
        } catch (IOException e) {
            return Map.of("success", false, "error", "Invalid CSV file");
        }
        int updated = 0, notFound = 0;
        List<String> notFoundEmails = new ArrayList<>();
        for (Author author : authors) {
            var existing = authorRepository.findByEmail(author.getEmail());
            if (existing.isPresent()) {
                Author toUpdate = existing.get();
                toUpdate.setName(author.getName());
                toUpdate.setBio(author.getBio());
                authorRepository.save(toUpdate);
                updated++;
            } else {
                notFound++;
                notFoundEmails.add(author.getEmail());
            }
        }
        return Map.of("success", true, "updated", updated, "notFound", notFound, "notFoundEmails", notFoundEmails);
    }

    @Override
    public Map<String, Object> bulkUpdateBooksCsv(MultipartFile file) {
        log.info("Bulk updating books from CSV");
        List<Book> books;
        try {
            books = csvHelper.parseBooks(file.getInputStream(), authorRepository);
        } catch (IOException e) {
            return Map.of("success", false, "error", "Invalid CSV file");
        }
        int updated = 0, notFound = 0;
        List<String> notFoundIsbns = new ArrayList<>();
        for (Book book : books) {
            var existing = bookRepository.findByIsbn(book.getIsbn());
            if (existing.isPresent()) {
                Book toUpdate = existing.get();
                toUpdate.setTitle(book.getTitle());
                toUpdate.setPublishedDate(book.getPublishedDate());
                toUpdate.setPrice(book.getPrice());
                toUpdate.setAuthor(book.getAuthor());
                bookRepository.save(toUpdate);
                updated++;
            } else {
                notFound++;
                notFoundIsbns.add(book.getIsbn());
            }
        }
        return Map.of("success", true, "updated", updated, "notFound", notFound, "notFoundIsbns", notFoundIsbns);
    }

    @Override
    public Map<String, Object> bulkDeleteAuthors(List<Long> authorIds) {
        log.info("Bulk deleting authors: {}", authorIds);
        int deleted = 0, notFound = 0;
        List<Long> notFoundIds = new ArrayList<>();
        for (Long id : authorIds) {
            if (authorRepository.existsById(id)) {
                authorRepository.deleteById(id);
                deleted++;
            } else {
                notFound++;
                notFoundIds.add(id);
            }
        }
        return Map.of("success", true, "deleted", deleted, "notFound", notFound, "notFoundIds", notFoundIds);
    }

    @Override
    public Map<String, Object> bulkDeleteBooks(List<Long> bookIds) {
        log.info("Bulk deleting books: {}", bookIds);
        int deleted = 0, notFound = 0;
        List<Long> notFoundIds = new ArrayList<>();
        for (Long id : bookIds) {
            if (bookRepository.existsById(id)) {
                bookRepository.deleteById(id);
                deleted++;
            } else {
                notFound++;
                notFoundIds.add(id);
            }
        }
        return Map.of("success", true, "deleted", deleted, "notFound", notFound, "notFoundIds", notFoundIds);
    }
}
