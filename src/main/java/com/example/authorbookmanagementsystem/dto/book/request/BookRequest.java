package com.example.authorbookmanagementsystem.dto.book.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String isbn;

    private LocalDate publishedDate;

    private Double price;

    private Long authorId;   // important for mapping

    public String getTitle() {
        return title;
    }
    public String getIsbn() {
        return isbn;
    }
    public java.time.LocalDate getPublishedDate() {
        return publishedDate;
    }
    public Double getPrice() {
        return price;
    }
    public Long getAuthorId() {
        return authorId;
    }
}