package com.samu.todoapi.controller;

import com.samu.todoapi.dto.UserCreateDTO;
import com.samu.todoapi.dto.UserListDTO;
import com.samu.todoapi.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
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
    public ResponseEntity<UserListDTO> findById(@PathVariable("id") @NotNull Long id) {
        UserListDTO user = userService.findById(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping
    public ResponseEntity<List<UserListDTO>> findAll() { // Only ADMIN
        List<UserListDTO> users = userService.findAll();
        return ResponseEntity.ok(users);
    }
}
