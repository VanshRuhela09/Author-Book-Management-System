package com.example.authorbookmanagementsystem.service.bulk;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;
import java.util.Map;

public interface BulkService {
    Map<String, Object> uploadAuthorsCsv(MultipartFile file);
    Map<String, Object> uploadBooksCsv(MultipartFile file);
    Resource downloadAuthorsCsv();
    Resource downloadBooksCsv();
}

