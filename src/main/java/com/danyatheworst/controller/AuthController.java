package com.danyatheworst.controller;

import com.danyatheworst.dto.JwtRequestDto;
import com.danyatheworst.dto.JwtResponseDto;
import com.danyatheworst.dto.RegisterRequestDto;
import com.danyatheworst.service.AuthenticationService;
import com.danyatheworst.service.RegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationService authenticationService;
    private final RegistrationService registrationService;

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody RegisterRequestDto user) {
        registrationService.register(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/token")
    public ResponseEntity<JwtResponseDto> getToken(@RequestBody JwtRequestDto tokenDto) {
        JwtResponseDto jwt = authenticationService.authenticate(tokenDto);
        return ResponseEntity.status(HttpStatus.OK).body(jwt);
    }
}
