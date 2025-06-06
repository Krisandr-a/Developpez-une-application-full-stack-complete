package com.openclassrooms.mddapi.repository;

import com.openclassrooms.mddapi.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository  extends CrudRepository<User, Integer> {
    // For login
    Optional<User> findByEmail(String email);
    Optional<User> findByName(String name);

    // for registration
    boolean existsByEmail(String email);
    boolean existsByName(String name);


}
