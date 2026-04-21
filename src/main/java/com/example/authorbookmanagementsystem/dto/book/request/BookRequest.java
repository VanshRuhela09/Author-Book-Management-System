package com.example.authorbookmanagementsystem.dto.book.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;

@Schema(description = "Request body for creating or updating a book")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookRequest {

    @Schema(description = "Title of the book", example = "1984")
    @NotBlank
    private String title;

    @Schema(description = "ISBN of the book", example = "978-0451524935")
    @NotBlank
    private String isbn;

    @Schema(description = "Published date of the book", example = "1949-06-08")
    private LocalDate publishedDate;

    @Schema(description = "Price of the book", example = "9.99")
    private Double price;

    @Schema(description = "ID of the author who wrote this book", example = "1")
    private Long authorId;   // important for mapping

}