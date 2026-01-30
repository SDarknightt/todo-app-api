package com.samu.todoapi.service;

import com.samu.todoapi.dto.UserCreateDTO;
import com.samu.todoapi.dto.UserDetailsDTO;
import com.samu.todoapi.entity.User;
import com.samu.todoapi.mapper.UserMapper;
import com.samu.todoapi.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public UserCreateDTO create(UserCreateDTO taskDTO) {
        User newUser = userRepository.save(userMapper.toEntity(taskDTO));
        return userMapper.toCreateDTO(newUser);
    }

    public UserDetailsDTO findById(Long id) {
        // Validate if logged user.id is the same as id
        return userRepository.findByIdAsDTO(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));
    }

    public List<UserDetailsDTO> findAll() { // Only ADMIN
        return userRepository.findAllUsersAsDTO();
    }
}
