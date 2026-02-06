package com.samu.todoapi.service;

import com.samu.todoapi.dto.AuthRequest;
import com.samu.todoapi.dto.AuthResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthService(PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

//    public AuthResponse authenticate(AuthRequest authRequest) {
//
//    }
}
