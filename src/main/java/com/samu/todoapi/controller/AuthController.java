package com.samu.todoapi.controller;

import com.samu.todoapi.dto.AuthRequest;
import com.samu.todoapi.dto.AuthResponse;
import com.samu.todoapi.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid AuthRequest authRequest) {
        AuthResponse authResponse = userService.login(authRequest);
        return ResponseEntity.ok(authResponse);
    }
}
