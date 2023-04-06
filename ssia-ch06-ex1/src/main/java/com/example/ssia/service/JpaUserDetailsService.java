package com.example.ssia.service;

import com.example.ssia.entity.User;
import com.example.ssia.model.CustomUserDetails;
import com.example.ssia.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JpaUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public JpaUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User u = userRepository.findUserByUsername(username).orElseThrow();
        return new CustomUserDetails(u);
    }
}
