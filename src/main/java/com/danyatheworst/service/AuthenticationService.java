package com.danyatheworst.service;

import com.danyatheworst.dto.JwtRequestDto;
import com.danyatheworst.dto.JwtResponseDto;
import com.danyatheworst.dto.JwtUserDetailsDto;
import com.danyatheworst.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public JwtResponseDto authenticate(JwtRequestDto payload) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(payload.getUsername(), payload.getPassword())
        );
        User user = (User) authentication.getPrincipal();
        JwtUserDetailsDto jwtUserDetailsDto = new JwtUserDetailsDto(
                user.getId(), user.getUsername(), user.getAuthorities()
        );
        String token = jwtService.generateToken(jwtUserDetailsDto);
        return new JwtResponseDto(token);
    }
}

