package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.UserDto;
import com.openclassrooms.mddapi.dto.UserLoginDto;
import com.openclassrooms.mddapi.dto.UserRegistrationDto;
import com.openclassrooms.mddapi.dto.UserUpdateDto;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.UserRepository;
import com.openclassrooms.mddapi.security.JwtUtil;
import com.openclassrooms.mddapi.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private JwtUtil jwtUtils;
    @Autowired
    private AuthService authService;

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

    @GetMapping("/profile")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Récupérer les informations sur l'utilisateur actuel")
    public UserDto getCurrentUser() {
        User user = authService.getAuthenticatedUser();
        return new UserDto(user.getId(), user.getName(), user.getEmail(), user.getCreatedAt(), user.getUpdatedAt());
    }

    @PutMapping("/profile")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Mettre à jour le profil de l'utilisateur connecté")
    public ResponseEntity<?> updateProfile(@RequestBody UserUpdateDto updateDto) {
        User currentUser = authService.getAuthenticatedUser();

        boolean nameChanged = updateDto.getName() != null && !updateDto.getName().equals(currentUser.getName());
        boolean emailChanged = updateDto.getEmail() != null && !updateDto.getEmail().equals(currentUser.getEmail());
        boolean passwordChanged = updateDto.getPassword() != null && !updateDto.getPassword().isBlank();

        // Validate name uniqueness
        if (nameChanged && userRepository.existsByName(updateDto.getName())) {
            return ResponseEntity.badRequest().body("Username is already taken.");
        }

        // Validate email uniqueness
        if (emailChanged && userRepository.existsByEmail(updateDto.getEmail())) {
            return ResponseEntity.badRequest().body("Email is already in use.");
        }

        // Apply updates if not null
        if (updateDto.getName() != null) {
            currentUser.setName(updateDto.getName());
        }
        if (updateDto.getEmail() != null) {
            currentUser.setEmail(updateDto.getEmail());
        }
        if (passwordChanged) {
            currentUser.setPassword(encoder.encode(updateDto.getPassword()));
        }

        currentUser.setUpdatedAt(java.time.LocalDateTime.now());
        userRepository.save(currentUser);

        if (nameChanged || emailChanged || passwordChanged) {
            String newToken = jwtUtils.generateToken(currentUser.getName());
            return ResponseEntity.ok(Map.of("token", newToken));
        }

        return ResponseEntity.ok("Profile updated successfully.");
    }




}
