package com.example.authorbookmanagementsystem.controller.bulk;

import com.example.authorbookmanagementsystem.service.bulk.BulkService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Tag(name = "Bulk Operations", description = "APIs for bulk upload, download, update and delete of authors and books via CSV")
@SecurityRequirement(name = "BearerAuth")
@RestController
@RequestMapping("/bulk")
@RequiredArgsConstructor
public class BulkController {

    private final BulkService bulkService;

    @Operation(summary = "Upload authors from CSV", description = "Inserts new authors from a CSV file, skips duplicates")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "CSV processed successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid CSV file")
    })
    @PostMapping("/authors")
    public ResponseEntity<Map<String, Object>> uploadAuthors(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(bulkService.uploadAuthorsCsv(file));
    }

    @Operation(summary = "Upload books from CSV", description = "Inserts new books from a CSV file, skips duplicates")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "CSV processed successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid CSV file")
    })
    @PostMapping("/books")
    public ResponseEntity<Map<String, Object>> uploadBooks(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(bulkService.uploadBooksCsv(file));
    }

    @Operation(summary = "Download all authors as CSV")
    @ApiResponse(responseCode = "200", description = "Authors CSV file returned")
    @GetMapping("/authors")
    public ResponseEntity<Resource> downloadAuthors() {
        Resource file = bulkService.downloadAuthorsCsv();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=authors.csv")
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(file);
    }

    @Operation(summary = "Download all books as CSV")
    @ApiResponse(responseCode = "200", description = "Books CSV file returned")
    @GetMapping("/books")
    public ResponseEntity<Resource> downloadBooks() {
        Resource file = bulkService.downloadBooksCsv();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=books.csv")
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(file);
    }

    @Operation(summary = "Bulk update authors from CSV", description = "Updates existing authors using email as key")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Bulk update completed"),
        @ApiResponse(responseCode = "400", description = "Invalid CSV file")
    })
    @PutMapping("/authors")
    public ResponseEntity<Map<String, Object>> bulkUpdateAuthors(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(bulkService.bulkUpdateAuthorsCsv(file));
    }

    @Operation(summary = "Bulk update books from CSV", description = "Updates existing books using ISBN as key")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Bulk update completed"),
        @ApiResponse(responseCode = "400", description = "Invalid CSV file")
    })
    @PutMapping("/books")
    public ResponseEntity<Map<String, Object>> bulkUpdateBooks(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(bulkService.bulkUpdateBooksCsv(file));
    }

    @Operation(summary = "Bulk delete authors by IDs")
    @ApiResponse(responseCode = "200", description = "Bulk delete completed")
    @DeleteMapping("/authors")
    public ResponseEntity<Map<String, Object>> bulkDeleteAuthors(@RequestBody List<Long> authorIds) {
        return ResponseEntity.ok(bulkService.bulkDeleteAuthors(authorIds));
    }

    @Operation(summary = "Bulk delete books by IDs")
    @ApiResponse(responseCode = "200", description = "Bulk delete completed")
    @DeleteMapping("/books")
    public ResponseEntity<Map<String, Object>> bulkDeleteBooks(@RequestBody List<Long> bookIds) {
        return ResponseEntity.ok(bulkService.bulkDeleteBooks(bookIds));
    }
}
