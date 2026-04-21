package com.example.authorbookmanagementsystem.dto.author.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthorRequest {

    @NotBlank
    private String name;

    @Email
    @NotBlank
    private String email;

    private String bio;

}