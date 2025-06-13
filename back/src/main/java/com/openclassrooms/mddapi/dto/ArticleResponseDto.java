package com.openclassrooms.mddapi.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ArticleResponseDto {
    private Integer id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private AuthorDto author;
    private ThemeDto theme;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AuthorDto {
        private String name;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ThemeDto {
        private Integer id;
        private String title;
        private String description;
    }
}
