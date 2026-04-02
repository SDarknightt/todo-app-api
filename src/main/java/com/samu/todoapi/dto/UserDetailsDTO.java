package com.samu.todoapi.dto;

import com.samu.todoapi.entity.Authority;

import java.util.UUID;

public record UserDetailsDTO(UUID id, String name, String email, Authority authority) {}
