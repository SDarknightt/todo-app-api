package com.samu.todoapi.controller;

import com.samu.todoapi.dto.UserCreateDTO;
import com.samu.todoapi.dto.UserDetailsDTO;
import com.samu.todoapi.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserCreateDTO> create(@RequestBody @Valid UserCreateDTO taskDTO) {
        UserCreateDTO newUser = userService.create(taskDTO);
        URI uri = URI.create("/users/"+newUser.getId());
        return ResponseEntity.created(uri).body(newUser);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDetailsDTO> findById(@PathVariable("id") @NotBlank Long id) {
        UserDetailsDTO user = userService.findById(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping
    public ResponseEntity<List<UserDetailsDTO>> findAll() { // Only ADMIN
        List<UserDetailsDTO> users = userService.findAll();
        return ResponseEntity.ok(users);
    }
}
