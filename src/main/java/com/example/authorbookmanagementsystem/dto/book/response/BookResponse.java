package com.example.authorbookmanagementsystem.dto.book.response;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookResponse {

    private Long id;
    private String title;
    private String isbn;
    private LocalDate publishedDate;
    private Double price;

    private String author; // clean API response
    private String authorEmail; // add author email for richer response
}