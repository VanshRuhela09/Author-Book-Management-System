package com.example.authorbookmanagementsystem.controller.book;

import com.example.authorbookmanagementsystem.dto.book.request.BookRequest;
import com.example.authorbookmanagementsystem.dto.book.response.BookResponse;
import com.example.authorbookmanagementsystem.security.jwt.JwtFilter;
import com.example.authorbookmanagementsystem.security.jwt.JwtUtil;
import com.example.authorbookmanagementsystem.security.service.MyUserDetailsService;
import com.example.authorbookmanagementsystem.service.book.BookService;
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

@WebMvcTest(BookController.class)
class BookControllerTest {
    @Autowired MockMvc mockMvc;
    @MockitoBean BookService bookService;
    @MockitoBean JwtUtil jwtUtil;
    @MockitoBean JwtFilter jwtFilter;
    @MockitoBean MyUserDetailsService myUserDetailsService;

    @Test
    void createBook_returnsOk() throws Exception {
        BookResponse response = new BookResponse();
        Mockito.when(bookService.createBook(any())).thenReturn(response);
        mockMvc.perform(post("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\":\"Book\",\"isbn\":\"123\",\"authorId\":1}"))
                .andExpect(status().isOk());
    }

    @Test
    void getAllBooks_returnsOk() throws Exception {
        Mockito.when(bookService.getAllBooks()).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/books"))
                .andExpect(status().isOk());
    }
}
