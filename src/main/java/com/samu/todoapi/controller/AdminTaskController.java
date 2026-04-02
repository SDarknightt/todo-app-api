package com.samu.todoapi.controller;

import com.samu.todoapi.dto.*;
import com.samu.todoapi.service.TaskService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/admin/tasks")
@PreAuthorize("hasRole('ADMIN')")
public class AdminTaskController {
    private final TaskService taskService;

    public AdminTaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("{userId}")
    public ResponseEntity<TaskCreateResponseDTO> create(@PathVariable UUID userId, @RequestBody @Valid TaskCreateRequestDTO taskDTO) {
        TaskCreateResponseDTO newTask = taskService.createForUser(taskDTO, userId);
        URI uri = URI.create("/tasks/"+newTask.getId());
        return ResponseEntity.created(uri).body(newTask);
    }

    @PutMapping("{id}")
    public ResponseEntity<TaskUpdateResponseDTO> update(@PathVariable @NotNull UUID id, @RequestBody @Valid TaskUpdateRequestDTO taskDTO) {
        TaskUpdateResponseDTO newTask = taskService.updateForUser(id, taskDTO);
        return ResponseEntity.ok().body(newTask);
    }

    @GetMapping("{id}")
    public ResponseEntity<TaskDetailsDTO> findById(@PathVariable @NotNull UUID id) {
        TaskDetailsDTO tasks = taskService.findById(id);
        return ResponseEntity.ok().body(tasks);
    }

    @GetMapping("{userId}")
    public ResponseEntity<List<TaskDetailsDTO>> findAllByUserId(@PathVariable @NotNull UUID userId) {
        List<TaskDetailsDTO> tasks = taskService.findAllByUserId(userId);
        return ResponseEntity.ok().body(tasks);
    }
}
