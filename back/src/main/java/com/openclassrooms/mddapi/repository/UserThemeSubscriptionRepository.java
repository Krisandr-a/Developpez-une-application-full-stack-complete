package com.openclassrooms.mddapi.repository;

import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.model.Theme;
import com.openclassrooms.mddapi.model.UserThemeSubscription;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserThemeSubscriptionRepository extends CrudRepository<UserThemeSubscription, Integer> {

    boolean existsByUserAndTheme(User user, Theme theme);

    Optional<UserThemeSubscription> findByUserAndTheme(User user, Theme theme);

    List<UserThemeSubscription> findAllByUser(User user);

}
