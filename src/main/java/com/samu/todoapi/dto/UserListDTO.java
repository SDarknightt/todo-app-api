package com.samu.todoapi.dto;

import com.samu.todoapi.entity.Authority;

public record UserListDTO(Long id, String name, String email, Authority authority) {}
