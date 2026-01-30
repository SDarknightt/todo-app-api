package com.samu.todoapi.mapper;

import com.samu.todoapi.dto.TaskCreateDTO;
import com.samu.todoapi.entity.Task;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {

    public TaskCreateDTO toCreateDTO(Task task) {
        return TaskCreateDTO.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .status(task.getStatus())
                .build();
    }

    public Task toEntity(TaskCreateDTO taskDTO) {
        return Task.builder()
                .title(taskDTO.getTitle())
                .description(taskDTO.getDescription())
                .status(taskDTO.getStatus())
                .build();
    }
}
