package com.samu.todoapi.controller;

import com.samu.todoapi.dto.*;
import com.samu.todoapi.service.TaskService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<TaskCreateResponseDTO> create(@RequestBody @Valid TaskCreateRequestDTO taskDTO) {
        TaskCreateResponseDTO newTask = taskService.create(taskDTO);
        URI uri = URI.create("/tasks/"+newTask.getId());
        return ResponseEntity.created(uri).body(newTask);
    }

    @PutMapping("{id}")
    public ResponseEntity<TaskUpdateResponseDTO> update(@PathVariable @NotNull UUID id, @RequestBody @Valid TaskUpdateRequestDTO taskDTO) {
        TaskUpdateResponseDTO newTask = taskService.update(id, taskDTO);
        return ResponseEntity.ok().body(newTask);
    }

    @GetMapping("{id}")
    public ResponseEntity<TaskDetailsDTO> findById(@PathVariable @NotNull UUID id) {
        TaskDetailsDTO tasks = taskService.findById(id);
        return ResponseEntity.ok().body(tasks);
    }

    @GetMapping
    public ResponseEntity<List<TaskDetailsDTO>> findAll() {
        List<TaskDetailsDTO> tasks = taskService.findAll();
        return ResponseEntity.ok().body(tasks);
    }
}
