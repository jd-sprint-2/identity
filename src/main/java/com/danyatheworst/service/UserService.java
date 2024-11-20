package com.danyatheworst.service;

import com.danyatheworst.entity.User;
import com.danyatheworst.exceptions.EntityAlreadyExistsException;
import com.danyatheworst.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    public void save(User user) {
        try {
            userRepository.save(user);
        } catch (Exception e) {
            String message = String.format("username %s is already taken", user.getUsername());
            throw new EntityAlreadyExistsException(message);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        return this.userRepository
                .findByUsername(username)
                .orElseThrow(() -> new BadCredentialsException("Bad credentials"));
    }
}