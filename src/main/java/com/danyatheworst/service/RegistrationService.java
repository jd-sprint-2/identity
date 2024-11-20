package com.danyatheworst.service;

import com.danyatheworst.dto.RegisterRequestDto;
import com.danyatheworst.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RegistrationService {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public void register(RegisterRequestDto payload) {
        String encodedPassword = passwordEncoder.encode(payload.getPassword());
        User user = new User(payload.getUsername(), encodedPassword);
        userService.save(user);
    }
}