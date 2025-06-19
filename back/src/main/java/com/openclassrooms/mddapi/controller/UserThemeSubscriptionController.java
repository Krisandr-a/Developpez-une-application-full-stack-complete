package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.service.UserThemeSubscriptionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/subscriptions")
public class UserThemeSubscriptionController {

    private final UserThemeSubscriptionService subscriptionService;

    public UserThemeSubscriptionController(UserThemeSubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @PostMapping("/subscribe")
    public ResponseEntity<Void> subscribeToTheme(@RequestParam Integer themeId) {
        subscriptionService.subscribeUserToTheme(themeId);
        return ResponseEntity.ok().build();
    }

}

