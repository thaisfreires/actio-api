package com.actio.actio_api.service;

import com.actio.actio_api.model.ActioUser;
import com.actio.actio_api.repository.ActioUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Custom implementation of {@link UserDetailsService} that integrates the application's
 * {@link ActioUser} entity with Spring Security's authentication framework.
 *
 * This service retrieves user information from the {@link ActioUserRepository}
 * and maps it to a Spring Security {@link UserDetails} object used for authentication.
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final ActioUserRepository actioUserRepository;

    /**
     * Loads a user by their email address, which acts as the username for authentication.
     *
     * Fetches the corresponding {@link ActioUser} and builds a {@link UserDetails} object
     * including username, password, and assigned roles. If no user is found, a {@link UsernameNotFoundException} is thrown.
     *
     * @param email the email address of the user attempting to authenticate
     * @return the user details used by Spring Security for authentication
     * @throws UsernameNotFoundException if the user cannot be found
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        ActioUser user = actioUserRepository.findByEmail(email).orElseThrow();
        List<String> roles = new ArrayList<>();
        roles.add(user.getUserRole().getRoleDescription());
        return
                User.builder()
                        .username(user.getEmail())
                        .password(user.getPassword())
                        .roles(roles.toArray(new String[0]))
                        .build();
    }
}
