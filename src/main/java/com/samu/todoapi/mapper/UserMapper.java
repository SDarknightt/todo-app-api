package com.samu.todoapi.mapper;

import com.samu.todoapi.dto.UserCreateDTO;
import com.samu.todoapi.entity.User;
import org.springframework.stereotype.Component;

// Bean para fazer as conversões entre entidade e DTOs
@Component
public class UserMapper {

    public UserCreateDTO toCreateDTO(User user) {
        return UserCreateDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }

    public User toEntity(UserCreateDTO userDTO) {
        return User.builder()
                   .name(userDTO.getName())
                   .email(userDTO.getEmail())
                   .password(userDTO.getPassword())
                   .build();
    }
}
