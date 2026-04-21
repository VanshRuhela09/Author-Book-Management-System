package com.example.authorbookmanagementsystem.service.bulk;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;
import java.util.Map;

public interface BulkService {
    Map<String, Object> uploadAuthorsCsv(MultipartFile file);
    Map<String, Object> uploadBooksCsv(MultipartFile file);
    Resource downloadAuthorsCsv();
    Resource downloadBooksCsv();
    Map<String, Object> bulkUpdateAuthorsCsv(MultipartFile file);
    Map<String, Object> bulkUpdateBooksCsv(MultipartFile file);
    Map<String, Object> bulkDeleteAuthors(java.util.List<Long> authorIds);
    Map<String, Object> bulkDeleteBooks(java.util.List<Long> bookIds);
}
