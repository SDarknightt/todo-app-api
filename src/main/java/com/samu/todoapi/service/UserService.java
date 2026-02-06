package com.samu.todoapi.service;

import com.samu.todoapi.dto.AuthRequest;
import com.samu.todoapi.dto.AuthResponse;
import com.samu.todoapi.dto.UserCreateDTO;
import com.samu.todoapi.dto.UserDetailsDTO;
import com.samu.todoapi.entity.User;
import com.samu.todoapi.mapper.UserMapper;
import com.samu.todoapi.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public UserService(UserRepository userRepository,
                       UserMapper userMapper,
                       PasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager,
                       JwtService jwtService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
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

    public AuthResponse login(AuthRequest authRequest) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(authRequest.username(),
                                                                                                authRequest.password());
        Authentication authentication = authenticationManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        final User loggedUser = userRepository.findByEmail(authRequest.username())
                .map()
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));

        final Map<String, Object> claims = Map.of("name", loggedUser.getName(),
                                                  "authorities", authentication.getAuthorities());

        // TODO: Set user uuid on subject
        return new AuthResponse(jwtService.createToken(claims, authRequest.username()));
    }
}
