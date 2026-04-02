package com.samu.todoapi.dto;

import com.samu.todoapi.entity.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class TaskCreateResponseDTO {
    @NotNull
    private UUID id;
    @NotBlank
    private String title;
    private String description;
    @NotNull
    private Status status;
}
