package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.UserDto;
import com.openclassrooms.mddapi.dto.UserLoginDto;
import com.openclassrooms.mddapi.dto.UserRegistrationDto;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.UserRepository;
import com.openclassrooms.mddapi.security.JwtUtil;
import com.openclassrooms.mddapi.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "S'inscrire et se connecter")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    JwtUtil jwtUtils;
    @Autowired
    AuthService authService;
    
    @PostMapping("/login")
    @Operation(summary = "Se connecter")
    public ResponseEntity<Map<String, String>> authenticateUser(@RequestBody UserLoginDto userLoginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userLoginDto.getLogin(),
                        userLoginDto.getPassword()
                )
        );
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = jwtUtils.generateToken(userDetails.getUsername());

        return ResponseEntity.ok(Map.of("token", token));
    }

    @PostMapping("/register")
    @Operation(summary = "S'inscrire")
    public String registerUser(@RequestBody UserRegistrationDto userRegistrationDto) {
        // the user can sign in with either their email or username. Both are unique.
        if (userRepository.existsByEmail(userRegistrationDto.getEmail())) {
            return "Error: this email has already been registered!";
        }

        if (userRepository.existsByName(userRegistrationDto.getName())) {
            return "Error: this username is already taken!";
        }

        // Create new user's account
        User newUser = new User(
                null,
                userRegistrationDto.getEmail(),
                userRegistrationDto.getName(),
                encoder.encode(userRegistrationDto.getPassword()),
                LocalDateTime.now(),
                LocalDateTime.now()
        );
        userRepository.save(newUser);
        return "User registered successfully!";
    }

    @GetMapping("/me")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Récupérer les informations sur l'utilisateur actuel")
    public UserDto getCurrentUser() {
        User user = authService.getAuthenticatedUser();
        return new UserDto(user.getId(), user.getName(), user.getEmail(), user.getCreatedAt(), user.getUpdatedAt());
    }
}
