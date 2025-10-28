package com.digitalbank.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final PasswordEncoder encoder;

    public CustomUserDetailsService(PasswordEncoder encoder) {
        this.encoder = encoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Temporary hardcoded user
        if (username.equals("testuser")) {
            return new User("testuser", encoder.encode("password"), Collections.emptyList());
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }
}
