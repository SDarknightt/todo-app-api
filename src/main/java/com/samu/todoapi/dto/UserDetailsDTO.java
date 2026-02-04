package com.samu.todoapi.dto;

import com.samu.todoapi.entity.Authority;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserDetailsDTO {
    private Long id;
    private String name;
    private String email;
    private Authority authority;
}
