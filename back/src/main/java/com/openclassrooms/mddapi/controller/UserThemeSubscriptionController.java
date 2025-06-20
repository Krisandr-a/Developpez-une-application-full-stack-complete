package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.UserThemeSubscriptionDto;
import com.openclassrooms.mddapi.service.UserThemeSubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subscriptions")
public class UserThemeSubscriptionController {

    @Autowired
    private UserThemeSubscriptionService subscriptionService;

    @GetMapping
    public ResponseEntity<List<UserThemeSubscriptionDto>> getUserSubscriptions() {
        return ResponseEntity.ok(subscriptionService.getCurrentUserSubscriptions());
    }

    @PostMapping("/subscribe")
    public ResponseEntity<Void> subscribeToTheme(@RequestParam Integer themeId) {
        subscriptionService.subscribeUserToTheme(themeId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/unsubscribe")
    public ResponseEntity<Void> unsubscribeFromTheme(@RequestParam Integer themeId) {
        subscriptionService.unsubscribeUserFromTheme(themeId);
        return ResponseEntity.ok().build();
    }


}

