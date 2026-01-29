package com.samu.todoapi.controller;

import com.samu.todoapi.dto.TaskCreateDTO;
import com.samu.todoapi.entity.Task;
import com.samu.todoapi.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<List<Task>> create(@RequestBody @Valid TaskCreateDTO task) {
        List<Task> tasks = taskService.getTasks();
        return ResponseEntity.ok().body(tasks);
    }

    @GetMapping
    public ResponseEntity<List<Task>> getAll() {
        List<Task> tasks = taskService.getTasks();
        return ResponseEntity.ok().body(tasks);
    }


}
