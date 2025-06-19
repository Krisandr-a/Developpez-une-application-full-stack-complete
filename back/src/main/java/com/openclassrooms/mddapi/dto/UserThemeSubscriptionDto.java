package com.openclassrooms.mddapi.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserThemeSubscriptionDto {

    @Schema(description = "Theme ID", example = "1")
    private Integer themeId;

    @Schema(description = "Theme title", example = "Theme 1")
    private String themeTitle;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Schema(description = "Date of subscription")
    private LocalDateTime subscribedAt;
}
