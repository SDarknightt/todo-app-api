package com.samu.todoapi.controller;

import com.samu.todoapi.dto.TaskCreateDTO;
import com.samu.todoapi.dto.TaskDetailsDTO;
import com.samu.todoapi.dto.TaskUpdateDTO;
import com.samu.todoapi.entity.Task;
import com.samu.todoapi.service.TaskService;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.websocket.server.PathParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<TaskCreateDTO> create(@RequestBody @Valid TaskCreateDTO taskDTO) {
        TaskCreateDTO newTask = taskService.create(taskDTO);
        URI uri = URI.create("/tasks/"+newTask.getId());
        return ResponseEntity.created(uri).body(newTask);
    }

    @PutMapping
    public ResponseEntity<TaskUpdateDTO> update(@RequestBody @Valid TaskUpdateDTO taskDTO) {
        TaskUpdateDTO newTask = taskService.update(taskDTO);
        return ResponseEntity.ok().body(newTask);
    }

    @GetMapping("{id}")
    public ResponseEntity<TaskDetailsDTO> findById(@PathVariable @NotBlank Long id) {
        TaskDetailsDTO tasks = taskService.findById(id);
        return ResponseEntity.ok().body(tasks);
    }

    @GetMapping
    public ResponseEntity<List<TaskDetailsDTO>> findAll(@Nullable @RequestParam Long userId) {
        List<TaskDetailsDTO> tasks = taskService.findAll(Optional.ofNullable(userId));
        return ResponseEntity.ok().body(tasks);
    }


}
