package com.openclassrooms.mddapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "DTO for registering a new user")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationDto {

    @Schema(description = "User's email address", example = "user@example.com")
    private String email;

    @Schema(description = "Username")
    private String name;

    @Schema(description = "User's password", example = "password123")
    private String password;
}
