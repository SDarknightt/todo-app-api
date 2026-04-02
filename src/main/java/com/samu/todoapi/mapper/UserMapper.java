package com.samu.todoapi.mapper;

import com.samu.todoapi.dto.UserCreateRequestDTO;
import com.samu.todoapi.dto.UserCreateResponseDTO;
import com.samu.todoapi.entity.User;
import org.springframework.stereotype.Component;

// Bean para fazer as conversões entre entidade e DTOs
@Component
public class UserMapper {

    public UserCreateResponseDTO toCreateResponseDTO(User user) {
        return UserCreateResponseDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }

    public User toEntity(UserCreateRequestDTO userDTO) {
        return User.builder()
                   .name(userDTO.getName())
                   .email(userDTO.getEmail())
                   .password(userDTO.getPassword())
                   .build();
    }
}
