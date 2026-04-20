package com.example.authorbookmanagementsystem.dto.author.response;

import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthorResponse {

    private Long id;
    private String name;
    private String email;
    private String bio;
    private List<BookInfo> books; // change from List<String> to List<BookInfo> for richer book info

    // Add BookInfo inner class for book name and ISBN
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class BookInfo {
        private String title;
        private String isbn;
    }
}