package com.samu.todoapi.dto;

import com.samu.todoapi.entity.Status;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class TaskUpdateDTO {
    @NotBlank
    private String title;
    @Nullable
    private String description;
    @NotNull
    private Status status;
}
