package com.lgnasolutions.backend_challenge.presentation.controllers;

import com.lgnasolutions.backend_challenge.application.services.AuthService;
import com.lgnasolutions.backend_challenge.domain.dto.AuthRequestDTO;
import com.lgnasolutions.backend_challenge.domain.dto.AuthResponseDTO;
import com.lgnasolutions.backend_challenge.domain.dto.UserDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public AuthResponseDTO login(@Valid @RequestBody AuthRequestDTO request) {
        return authService.login(request);
    }

    @PostMapping("/register")
    public AuthResponseDTO register(@Valid @RequestBody UserDTO request) {
        return authService.register(request);
    }
}
