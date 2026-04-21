package com.example.authorbookmanagementsystem.service.bulk;

import com.example.authorbookmanagementsystem.entity.Author;
import com.example.authorbookmanagementsystem.entity.Book;
import com.example.authorbookmanagementsystem.file.CsvHelper;
import com.example.authorbookmanagementsystem.repository.author.AuthorRepository;
import com.example.authorbookmanagementsystem.repository.book.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BulkServiceImplTest {
    @Mock AuthorRepository authorRepository;
    @Mock BookRepository bookRepository;
    @Mock CsvHelper csvHelper;
    @InjectMocks BulkServiceImpl bulkService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void uploadAuthorsCsv_invalidFile_returnsError() throws IOException {
        MultipartFile file = mock(MultipartFile.class);
        when(file.getInputStream()).thenThrow(new IOException());
        Map<String, Object> result = bulkService.uploadAuthorsCsv(file);
        assertFalse((Boolean) result.get("success"));
        assertEquals("Invalid CSV file", result.get("error"));
    }

    @Test
    void downloadAuthorsCsv_success() {
        List<Author> authors = Collections.singletonList(new Author());
        when(authorRepository.findAll()).thenReturn(authors);
        when(csvHelper.writeAuthors(authors)).thenReturn("csv");
        ByteArrayResource resource = (ByteArrayResource) bulkService.downloadAuthorsCsv();
        assertNotNull(resource);
        assertArrayEquals("csv".getBytes(), resource.getByteArray());
    }
}

