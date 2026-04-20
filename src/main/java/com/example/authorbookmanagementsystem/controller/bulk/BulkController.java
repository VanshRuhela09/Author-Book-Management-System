package com.example.authorbookmanagementsystem.controller.bulk;

import com.example.authorbookmanagementsystem.service.bulk.BulkService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/bulk")
@RequiredArgsConstructor
public class BulkController {
    private final BulkService bulkService;

    @PostMapping("/authors/upload")
    public ResponseEntity<Map<String, Object>> uploadAuthors(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(bulkService.uploadAuthorsCsv(file));
    }

    @PostMapping("/books/upload")
    public ResponseEntity<Map<String, Object>> uploadBooks(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(bulkService.uploadBooksCsv(file));
    }

    @GetMapping("/authors/download")
    public ResponseEntity<Resource> downloadAuthors() {
        Resource file = bulkService.downloadAuthorsCsv();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=authors.csv")
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(file);
    }

    @GetMapping("/books/download")
    public ResponseEntity<Resource> downloadBooks() {
        Resource file = bulkService.downloadBooksCsv();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=books.csv")
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(file);
    }
}

