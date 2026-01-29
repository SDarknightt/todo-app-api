package com.samu.todoapi.dto;

import com.samu.todoapi.entity.Status;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TaskDetailsDTO {
    @NotNull
    private Long id;

    @NotBlank
    private String title;

    @Nullable
    private String description;

    @NotNull
    private Status status;
}
