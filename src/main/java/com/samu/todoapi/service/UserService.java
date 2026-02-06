package com.samu.todoapi.service;

import com.samu.todoapi.dto.UserCreateDTO;
import com.samu.todoapi.dto.UserDetailsDTO;
import com.samu.todoapi.entity.User;
import com.samu.todoapi.mapper.UserMapper;
import com.samu.todoapi.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public UserCreateDTO create(UserCreateDTO userDTO) {
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        User newUser = userRepository.save(userMapper.toEntity(userDTO));
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
