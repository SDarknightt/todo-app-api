package com.samu.todoapi.service;

import com.samu.todoapi.entity.Task;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    public List<Task> getTasks() {
        return List.of(
            Task.builder()
                .title("Título")
                .description("Description")
                .build()
        );
    }
}
