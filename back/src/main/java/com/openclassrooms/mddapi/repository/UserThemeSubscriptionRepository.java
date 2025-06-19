package com.openclassrooms.mddapi.repository;

import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.model.Theme;
import com.openclassrooms.mddapi.model.UserThemeSubscription;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserThemeSubscriptionRepository extends CrudRepository<UserThemeSubscription, Integer> {
    boolean existsByUserAndTheme(User user, Theme theme);
}
