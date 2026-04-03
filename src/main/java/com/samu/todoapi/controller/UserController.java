package com.samu.todoapi.controller;

import com.samu.todoapi.dto.UserCreateRequestDTO;
import com.samu.todoapi.dto.UserCreateResponseDTO;
import com.samu.todoapi.dto.UserDetailsDTO;
import com.samu.todoapi.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping()
    public ResponseEntity<UserCreateResponseDTO> create(@RequestBody @Valid UserCreateRequestDTO userDTO) {
        UserCreateResponseDTO newUser = userService.create(userDTO);
        URI uri = URI.create("/users/info");
        return ResponseEntity.created(uri).body(newUser);
    }

    @GetMapping("/users/info")
    public ResponseEntity<UserDetailsDTO> getUserInfo() {
        UserDetailsDTO user = userService.getUserInfo();
        return ResponseEntity.ok(user);
    }
}
