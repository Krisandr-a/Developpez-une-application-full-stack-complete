package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.UserThemeSubscriptionDto;
import com.openclassrooms.mddapi.model.Theme;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.model.UserThemeSubscription;
import com.openclassrooms.mddapi.repository.ThemeRepository;
import com.openclassrooms.mddapi.repository.UserRepository;
import com.openclassrooms.mddapi.repository.UserThemeSubscriptionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserThemeSubscriptionService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ThemeRepository themeRepository;
    @Autowired
    private UserThemeSubscriptionRepository subscriptionRepository;

    private User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not authenticated");
        }

        Object principal = authentication.getPrincipal();
        String username;

        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else if (principal instanceof String) {
            username = (String) principal;
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid user principal");
        }

        return userRepository.findByEmail(username)
                .or(() -> userRepository.findByName(username))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));
    }

    public void subscribeUserToTheme(Integer themeId) {
        log.info("Attempting to subscribe to theme with ID: {}", themeId);

        User user = getAuthenticatedUser();
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

    public List<UserThemeSubscriptionDto> getCurrentUserSubscriptions() {
        User user = getAuthenticatedUser();

        return subscriptionRepository.findAllByUser(user).stream()
                .map(subscription -> new UserThemeSubscriptionDto(
                        subscription.getTheme().getId(),
                        subscription.getTheme().getTitle(),
                        subscription.getTheme().getDescription(),
                        subscription.getSubscribedAt()
                ))
                .collect(Collectors.toList());
    }

    public void unsubscribeUserFromTheme(Integer themeId) {
        User user = getAuthenticatedUser();

        Theme theme = themeRepository.findById(themeId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Theme not found"));

        UserThemeSubscription themeToDelete = subscriptionRepository.findByUserAndTheme(user, theme)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Subscription not found"));

        subscriptionRepository.delete(themeToDelete);
        log.info("Unsubscribed user id {} from theme id {}", user.getId(), theme.getId());
    }



}
