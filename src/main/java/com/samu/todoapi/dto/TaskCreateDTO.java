package com.samu.todoapi.dto;

import com.samu.todoapi.entity.Status;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class TaskCreateDTO {
    @NotBlank
    private String title;

    @Nullable
    private String description;

    @NotNull
    private Status status;
}
