package com.example.authorbookmanagementsystem.file;

import com.example.authorbookmanagementsystem.entity.Author;
import com.example.authorbookmanagementsystem.entity.Book;
import com.example.authorbookmanagementsystem.repository.author.AuthorRepository;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class CsvHelper {
    public List<Author> parseAuthors(InputStream is) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        List<Author> authors = new ArrayList<>();
        String line;
        boolean first = true;
        while ((line = reader.readLine()) != null) {
            if (first) { first = false; continue; } // skip header
            String[] fields = line.split(",");
            if (fields.length < 2) continue;
            Author author = Author.builder()
                    .name(fields[0].trim())
                    .email(fields[1].trim())
                    .bio(fields.length > 2 ? fields[2].trim() : null)
                    .build();
            authors.add(author);
        }
        return authors;
    }

    public List<Book> parseBooks(InputStream is, AuthorRepository authorRepository) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        List<Book> books = new ArrayList<>();
        String line;
        boolean first = true;
        while ((line = reader.readLine()) != null) {
            if (first) { first = false; continue; } // skip header
            String[] fields = line.split(",");
            if (fields.length < 4) continue;
            String authorEmail = fields[4].trim();
            Optional<Author> authorOpt = authorRepository.findByEmail(authorEmail);
            if (authorOpt.isEmpty()) continue;
            Book book = Book.builder()
                    .title(fields[0].trim())
                    .isbn(fields[1].trim())
                    .publishedDate(fields[2].trim().isEmpty() ? null : java.time.LocalDate.parse(fields[2].trim()))
                    .price(fields[3].trim().isEmpty() ? null : Double.valueOf(fields[3].trim()))
                    .author(authorOpt.get())
                    .build();
            books.add(book);
        }
        return books;
    }

    public String writeAuthors(List<Author> authors) {
        StringBuilder sb = new StringBuilder();
        sb.append("name,email,bio\n");
        for (Author a : authors) {
            sb.append(a.getName()).append(",")
              .append(a.getEmail()).append(",")
              .append(a.getBio() == null ? "" : a.getBio().replace(",", " ")).append("\n");
        }
        return sb.toString();
    }

    public String writeBooks(List<Book> books) {
        StringBuilder sb = new StringBuilder();
        sb.append("title,isbn,publishedDate,price,authorEmail\n");
        for (Book b : books) {
            sb.append(b.getTitle()).append(",")
              .append(b.getIsbn()).append(",")
              .append(b.getPublishedDate() == null ? "" : b.getPublishedDate()).append(",")
              .append(b.getPrice() == null ? "" : b.getPrice()).append(",")
              .append(b.getAuthor() != null ? b.getAuthor().getEmail() : "").append("\n");
        }
        return sb.toString();
    }
}

