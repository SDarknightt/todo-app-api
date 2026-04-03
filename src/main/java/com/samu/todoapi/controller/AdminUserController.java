package com.samu.todoapi.controller;

import com.samu.todoapi.dto.UserDetailsDTO;
import com.samu.todoapi.service.UserService;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/admin/users")
@PreAuthorize("hasRole('ADMIN')")
public class AdminUserController {
    private final UserService userService;

    public AdminUserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDetailsDTO> findById(@PathVariable("id") @NotNull UUID id) {
        UserDetailsDTO user = userService.findById(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserDetailsDTO>> findAll() {
        List<UserDetailsDTO> users = userService.findAll();
        return ResponseEntity.ok(users);
    }
}
