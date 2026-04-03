package com.samu.todoapi.dto;

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
public class UserCreateResponseDTO {
    @NotNull
    private UUID id;
    @NotBlank
    private String name;
    @NotBlank
    private String email;
}
