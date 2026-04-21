package com.example.authorbookmanagementsystem.dto.author.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Schema(description = "Request body for creating or updating an author")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthorRequest {

    @Schema(description = "Full name of the author", example = "George Orwell")
    @NotBlank
    private String name;

    @Schema(description = "Email address of the author", example = "george@orwell.com")
    @Email
    @NotBlank
    private String email;

    @Schema(description = "Short biography of the author", example = "English novelist and essayist")
    private String bio;

}