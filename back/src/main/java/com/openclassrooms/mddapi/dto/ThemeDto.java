package com.openclassrooms.mddapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(description = "DTO for theme")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ThemeDto {

    @Schema(description = "Title", example = "Theme 1")
    private String title;

    @Schema(description = "Description", example = "Here is a description...")
    private String description;
}
