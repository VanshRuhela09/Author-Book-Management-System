package com.example.authorbookmanagementsystem.controller.bulk;

import com.example.authorbookmanagementsystem.service.bulk.BulkService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/bulk")
@RequiredArgsConstructor
public class BulkController {
    private final BulkService bulkService;

    @PostMapping("/authors")
    public ResponseEntity<Map<String, Object>> uploadAuthors(@RequestParam("file") MultipartFile file) {
        log.info("Uploading authors CSV");
        return ResponseEntity.ok(bulkService.uploadAuthorsCsv(file));
    }

    @PostMapping("/books")
    public ResponseEntity<Map<String, Object>> uploadBooks(@RequestParam("file") MultipartFile file) {
        log.info("Uploading books CSV");
        return ResponseEntity.ok(bulkService.uploadBooksCsv(file));
    }

    @GetMapping("/authors")
    public ResponseEntity<Resource> downloadAuthors() {
        log.info("Downloading authors CSV");
        Resource file = bulkService.downloadAuthorsCsv();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=authors.csv")
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(file);
    }

    @GetMapping("/books")
    public ResponseEntity<Resource> downloadBooks() {
        log.info("Downloading books CSV");
        Resource file = bulkService.downloadBooksCsv();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=books.csv")
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(file);
    }

    @PutMapping("/authors")
    public ResponseEntity<Map<String, Object>> bulkUpdateAuthors(@RequestParam("file") MultipartFile file) {
        log.info("Bulk update authors request received");
        return ResponseEntity.ok(bulkService.bulkUpdateAuthorsCsv(file));
    }

    @PutMapping("/books")
    public ResponseEntity<Map<String, Object>> bulkUpdateBooks(@RequestParam("file") MultipartFile file) {
        log.info("Bulk update books request received");
        return ResponseEntity.ok(bulkService.bulkUpdateBooksCsv(file));
    }

    @DeleteMapping("/authors")
    public ResponseEntity<Map<String, Object>> bulkDeleteAuthors(@RequestBody java.util.List<Long> authorIds) {
        log.info("Bulk delete authors request received: {}", authorIds);
        return ResponseEntity.ok(bulkService.bulkDeleteAuthors(authorIds));
    }

    @DeleteMapping("/books")
    public ResponseEntity<Map<String, Object>> bulkDeleteBooks(@RequestBody java.util.List<Long> bookIds) {
        log.info("Bulk delete books request received: {}", bookIds);
        return ResponseEntity.ok(bulkService.bulkDeleteBooks(bookIds));
    }
}
