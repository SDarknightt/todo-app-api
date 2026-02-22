package com.samu.todoapi.service;

import com.samu.todoapi.dto.*;
import com.samu.todoapi.entity.Authority;
import com.samu.todoapi.entity.User;
import com.samu.todoapi.exception.NotFoundException;
import com.samu.todoapi.mapper.UserMapper;
import com.samu.todoapi.repository.UserRepository;
import com.samu.todoapi.security.JwtService;
import com.samu.todoapi.security.UserPrincipal;
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

    public UserListDTO findById(Long id) {
        User loggedUser = this.getLoggedUser();

        if (loggedUser.getAuthority() != Authority.ADMIN) {
            id = loggedUser.getId();
        }

        // Validate if logged user.id is the same as id
        return userRepository.findByIdAsDTO(id)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado!"));
    }

    public List<UserListDTO> findAll() { // Only ADMIN
        return userRepository.findAllUsersAsDTO();
    }

    public AuthResponse login(AuthRequest authRequest) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(authRequest.username(),
                                                                                                authRequest.password());
        Authentication authentication = authenticationManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        final UserPrincipal loggedUser = userRepository.findByEmailIgnoreCase(authRequest.username())
                .map(UserPrincipal::new)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado!"));

        final Map<String, Object> claims = Map.of("name", loggedUser.getUser().getName(),
                                                  "authorities", authentication.getAuthorities());

        // TODO: Set user uuid on subject
        return new AuthResponse(jwtService.createToken(claims, authRequest.username()));
    }

    public User getLoggedUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserPrincipal) {
            return ((UserPrincipal) principal).getUser();
        }
        String username = principal.toString();
        return this.userRepository.findByEmailIgnoreCase(username)
                                    .orElseThrow(() -> new NotFoundException("Usuário não encontrado!"));
    }
}
