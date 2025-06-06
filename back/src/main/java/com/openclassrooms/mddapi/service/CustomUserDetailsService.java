package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.Optional;

@Service
public class CustomUserDetailsService  implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        //check email and then username for login
        Optional<com.openclassrooms.mddapi.model.User> userOpt = userRepository.findByEmail(login);

        if (!userOpt.isPresent()) {
            userOpt = userRepository.findByName(login); // try username
        }

        com.openclassrooms.mddapi.model.User user = userOpt.orElseThrow(() ->
                new UsernameNotFoundException("User not Found with login: " + login)
        );

        return new org.springframework.security.core.userdetails.User(
                user.getName(),
                user.getPassword(),
                Collections.emptyList()
        );
    }

}
