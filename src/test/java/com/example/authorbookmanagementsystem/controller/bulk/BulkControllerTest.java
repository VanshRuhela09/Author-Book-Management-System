package com.example.authorbookmanagementsystem.controller.bulk;

import com.example.authorbookmanagementsystem.security.jwt.JwtFilter;
import com.example.authorbookmanagementsystem.security.jwt.JwtUtil;
import com.example.authorbookmanagementsystem.security.service.MyUserDetailsService;
import com.example.authorbookmanagementsystem.service.bulk.BulkService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BulkController.class)
class BulkControllerTest {
    @Autowired MockMvc mockMvc;
    @MockitoBean BulkService bulkService;
    @MockitoBean JwtUtil jwtUtil;
    @MockitoBean JwtFilter jwtFilter;
    @MockitoBean MyUserDetailsService myUserDetailsService;

    @Test
    void uploadAuthors_returnsOk() throws Exception {
        Mockito.when(bulkService.uploadAuthorsCsv(any(MultipartFile.class))).thenReturn(Map.of("success", true));
        mockMvc.perform(multipart("/bulk/authors").file("file", "csv-data".getBytes()))
                .andExpect(status().isOk());
    }
}
