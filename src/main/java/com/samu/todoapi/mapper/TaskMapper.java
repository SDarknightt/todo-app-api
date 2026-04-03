package com.samu.todoapi.mapper;

import com.samu.todoapi.dto.TaskCreateRequestDTO;
import com.samu.todoapi.dto.TaskCreateResponseDTO;
import com.samu.todoapi.dto.TaskUpdateResponseDTO;
import com.samu.todoapi.entity.Task;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {

    public TaskCreateResponseDTO toCreateResponseDTO(Task task) {
        return TaskCreateResponseDTO.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .status(task.getStatus())
                .build();
    }

    public Task toEntity(TaskCreateRequestDTO taskDTO) {
        return Task.builder()
                .title(taskDTO.getTitle())
                .description(taskDTO.getDescription())
                .status(taskDTO.getStatus())
                .build();
    }

    public TaskUpdateResponseDTO toUpdateResponseDTO(Task task) {
        return TaskUpdateResponseDTO.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .status(task.getStatus())
                .build();
    }
}
