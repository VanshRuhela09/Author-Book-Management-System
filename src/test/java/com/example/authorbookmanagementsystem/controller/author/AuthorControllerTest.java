package com.example.authorbookmanagementsystem.controller.author;

import com.example.authorbookmanagementsystem.dto.author.response.AuthorResponse;
import com.example.authorbookmanagementsystem.security.jwt.JwtFilter;
import com.example.authorbookmanagementsystem.security.jwt.JwtUtil;
import com.example.authorbookmanagementsystem.security.service.MyUserDetailsService;
import com.example.authorbookmanagementsystem.service.author.AuthorService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthorController.class)
class AuthorControllerTest {
    @Autowired MockMvc mockMvc;
    @MockitoBean AuthorService authorService;
    @MockitoBean JwtUtil jwtUtil;
    @MockitoBean JwtFilter jwtFilter;
    @MockitoBean MyUserDetailsService myUserDetailsService;

    @Test
    void createAuthor_returnsOk() throws Exception {
        AuthorResponse response = new AuthorResponse();
        Mockito.when(authorService.createAuthor(any())).thenReturn(response);
        mockMvc.perform(post("/authors")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Test\",\"email\":\"test@email.com\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void getAllAuthors_returnsOk() throws Exception {
        Mockito.when(authorService.getAllAuthors()).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/authors"))
                .andExpect(status().isOk());
    }
}
