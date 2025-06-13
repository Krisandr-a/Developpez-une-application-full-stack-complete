package com.openclassrooms.mddapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Schema(description = "DTO for Article")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDto {

    @Schema(description = "Title", example = "Title of Article 1")
    private String title;

    @Schema(description = "Article content", example = "Here is the content of Article 1")
    private String content;

    @Schema(description = "Theme ID", example = "1")
    private Integer themeId;
}
