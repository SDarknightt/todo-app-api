package com.samu.todoapi.dto;

import com.samu.todoapi.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class UserDetailsDTO {
    private Long id;
    private String name;
    private List<Role> roles;
}
