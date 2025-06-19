package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.model.Theme;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.model.UserThemeSubscription;
import com.openclassrooms.mddapi.repository.ThemeRepository;
import com.openclassrooms.mddapi.repository.UserRepository;
import com.openclassrooms.mddapi.repository.UserThemeSubscriptionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;

@Slf4j
@Service
public class UserThemeSubscriptionService {

    private final UserRepository userRepository;
    private final ThemeRepository themeRepository;
    private final UserThemeSubscriptionRepository subscriptionRepository;

    public UserThemeSubscriptionService(UserRepository userRepository,
                                        ThemeRepository themeRepository,
                                        UserThemeSubscriptionRepository subscriptionRepository) {
        this.userRepository = userRepository;
        this.themeRepository = themeRepository;
        this.subscriptionRepository = subscriptionRepository;
    }

    public void subscribeUserToTheme(Integer themeId) {
        log.info("Attempting to subscribe to theme with ID: {}", themeId);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            log.error("Authentication object is null");
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not authenticated");
        }

        Object principal = authentication.getPrincipal();
        log.info("Authentication principal: {}", principal.getClass().getName());

        String username; // email is username in setup
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
            log.info("Extracted username from UserDetails: {}", username);
        } else if (principal instanceof String) {
            username = (String) principal; // sometimes principal can be String (username)
            log.info("Extracted username from String principal: {}", username);
        } else {
            log.error("Unknown principal type: {}", principal.getClass().getName());
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid user principal");
        }

        User user = userRepository.findByEmail(username)
                .or(() -> userRepository.findByName(username))
                .orElseThrow(() -> {
                    log.error("User not found with email or name: {}", username);
                    return new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found");
                });

        log.info("Found user: id={}, email={}, name={}", user.getId(), user.getEmail(), user.getName());

        Theme theme = themeRepository.findById(themeId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Theme not found"));

        log.info("Found theme: id={}, title={}", theme.getId(), theme.getTitle());

        if (subscriptionRepository.existsByUserAndTheme(user, theme)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User already subscribed to this theme");
        }

        UserThemeSubscription subscription = new UserThemeSubscription();
        subscription.setUser(user);
        subscription.setTheme(theme);
        subscription.setSubscribedAt(LocalDateTime.now());

        subscriptionRepository.save(subscription);
        log.info("Subscription created for user {} to theme {}", user.getId(), theme.getId());
    }
}
